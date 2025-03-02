package com.mustafa.dishdash.main.recipe_details.presenter;

import android.annotation.SuppressLint;

import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.UploadRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.entities.FuturePlaneEntity;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.UploadFuturePlanesCallBack;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.recipe_details.view.RecipeDetailsView;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class RecipeDetailsPresenter implements UploadRemoteFavoriteMealsCallBack, UploadFuturePlanesCallBack {
    private RecipeDetailsView view;
    private MealsRepository repository;
    private AuthRepository authRepository;
    private CompositeDisposable compositeDisposable;

    public RecipeDetailsPresenter(RecipeDetailsView view,
                                  MealsRepository mealsRepository,
                                  AuthRepository authRepository) {
        this.view = view;
        this.repository = mealsRepository;
        this.authRepository = authRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    public void getMealById(String id) {
        compositeDisposable.add(
                repository.getMealById(id)
                        .subscribe(
                                mealsItemBooleanPair ->
                                        view.onGetMealDetailsSuccess(mealsItemBooleanPair.first, mealsItemBooleanPair.second),
                                error -> view.onGetMealDetailsFail(error.getMessage())));
    }

    @SuppressLint("CheckResult")
    public void addMealToFavorites(MealsItem meal) {
        if (authRepository.isAuthenticated()) {
            compositeDisposable.add(
                    repository.insertFavoriteMeal(meal)
                            .subscribe(() -> {
                                        view.onAddedToFavoritesSuccess();
                                        syncFavorites();
                                    },
                                    error -> view.onAddedToFavoritesFail()));
        } else {
            view.userNotLoggedIn();
        }
    }

    private void syncFavorites() {
        compositeDisposable.add(
                repository
                        .getFavoriteMeals()
                        .flatMap(mealsItems ->
                                Flowable.fromIterable(mealsItems)
                                        .map(mealsItem -> mealsItem.getIdMeal())
                                        .collect(Collectors.toList()).toFlowable())
                        .subscribe(mealsId -> {
                            repository
                                    .uploadFavoriteMeals(RecipeDetailsPresenter.this, mealsId);
                        }));
    }

    @SuppressLint("CheckResult")
    public void removeFavoriteMeal(MealsItem meal) {
        String userEmail = authRepository.getCurrentAuthenticatedUserEmail();
        if (userEmail != null) {
            compositeDisposable.add(
                    repository
                            .removeFavoriteMeal(meal)
                            .subscribe(() -> {
                                        view.onRemoveFavoriteSuccess();
                                        syncFavorites();
                                    },
                                    error -> view.onRemoveFavoriteFail(error.getMessage())));
        } else {
            view.userNotLoggedIn();
        }
    }

    public void addMealToFuturePlane(MealsItem meal, int day, int month, int year) {
        if (authRepository.isAuthenticated()) {
            FuturePlane futurePlane = new FuturePlane(meal, day, month, year);
            compositeDisposable.add(
                    repository
                            .insertFuturePlane(futurePlane)
                            .subscribe(() -> {
                                        view.onAddedToFuturePlanesSuccess(meal, day, month-1, year);
                                        syncFutureData();
                                    },
                                    error -> view.onAddedToFuturePlanesFail()));
        } else {
            view.userNotLoggedIn();
        }
    }

    private void syncFutureData() {
        compositeDisposable.add(
                repository
                        .getAllFuturePlanes()
                        .flatMap(futurePlanes ->
                                Flowable.fromIterable(futurePlanes)
                                        .map(futurePlane ->
                                                new FuturePlaneEntity(futurePlane.getIdMeal(),
                                                        futurePlane.getDay(),
                                                        futurePlane.getMonth(),
                                                        futurePlane.getYear()))
                                        .collect(Collectors.toList()).toFlowable())
                        .subscribe(futurePlaneEntities -> {
                            repository
                                    .uploadFuturePlanes(RecipeDetailsPresenter.this, futurePlaneEntities);
                        }));
    }

    public void close() {
        compositeDisposable.clear();
    }

    public String getYoutubeVideo(String strYoutube) {
        if (strYoutube.indexOf('&') != -1) {
            return strYoutube.substring(strYoutube.indexOf('=') + 1, strYoutube.indexOf('&'));
        } else {
            return strYoutube.substring(strYoutube.indexOf('=') + 1);
        }
    }


    @Override
    public void onUploadFavoriteMealsRemoteOnSuccess() {
        view.onSyncSuccess();
    }

    @Override
    public void onUploadFavoriteMealsRemoteOnFail(String errorMsg) {
        view.onSyncDataFailed();
    }

    @Override
    public void onUploadFuturePlanesRemoteOnSuccess() {
        view.onSyncSuccess();
    }

    @Override
    public void onUploadFuturePlanesRemoteOnFail(String userNotLoggedIn) {
        view.onSyncDataFailed();
    }
}
