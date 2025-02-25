package com.mustafa.dishdash.main.recipe_details.presenter;

import android.annotation.SuppressLint;

import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.UploadRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.recipe_details.view.RecipeDetailsView;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class RecipeDetailsPresenter implements UploadRemoteFavoriteMealsCallBack {
    private RecipeDetailsView view;
    private MealsRepository mealsRepository;
    private FavoriteMealsRepository favoriteMealsRepository;
    private AuthRepository authRepository;
    private CompositeDisposable compositeDisposable;

    public RecipeDetailsPresenter(RecipeDetailsView view,
                                  MealsRepository mealsRepository,
                                  FavoriteMealsRepository favoriteMealsRepository,
                                  AuthRepository authRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.favoriteMealsRepository = favoriteMealsRepository;
        this.authRepository = authRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    public void getMealById(String id) {
        compositeDisposable.add(
                mealsRepository.getMealById(id)
                        .subscribe(
                                mealsItemBooleanPair ->
                                        view.onGetMealDetailsSuccess(mealsItemBooleanPair.first, mealsItemBooleanPair.second),
                                error -> view.onGetMealDetailsFail(error.getMessage())));
    }

    @SuppressLint("CheckResult")
    public void addMealToFavorites(MealsItem meal) {
        String userEmail = authRepository.getCurrentAuthenticatedUserEmail();
        if (userEmail != null) {
            compositeDisposable.add(
                    favoriteMealsRepository.insertFavoriteMeal(meal)
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
                favoriteMealsRepository
                        .getFavoriteMeals()
                        .flatMap(mealsItems ->
                                Flowable.fromIterable(mealsItems)
                                        .map(mealsItem -> mealsItem.getIdMeal())
                                        .collect(Collectors.toList()).toFlowable())
                        .subscribe(mealsId -> {
                            favoriteMealsRepository
                                    .uploadFavoriteMeals(RecipeDetailsPresenter.this, mealsId);
                        }));
    }

    @SuppressLint("CheckResult")
    public void removeFavoriteMeal(MealsItem meal) {
        String userEmail = authRepository.getCurrentAuthenticatedUserEmail();
        if (userEmail != null) {
            compositeDisposable.add(
                    favoriteMealsRepository
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

    public String getYoutubeVideo(String strYoutube) {
        if (strYoutube.indexOf('&') != -1) {
            return strYoutube.substring(strYoutube.indexOf('=') + 1, strYoutube.indexOf('&'));
        } else {
            return strYoutube.substring(strYoutube.indexOf('=') + 1);
        }
    }

    public void close() {
        compositeDisposable.clear();
    }

    @Override
    public void onUploadFavoriteMealsRemoteOnSuccess() {
        view.onSyncSuccess();
    }

    @Override
    public void onUploadFavoriteMealsRemoteOnFail(String errorMsg) {
        view.onSyncDataFailed();
    }
}
