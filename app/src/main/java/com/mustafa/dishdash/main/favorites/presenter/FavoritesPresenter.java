package com.mustafa.dishdash.main.favorites.presenter;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseAuth;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.UploadRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.favorites.view.FavoritesView;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class FavoritesPresenter implements UploadRemoteFavoriteMealsCallBack {
    private FavoritesView view;
    private FavoriteMealsRepository favoriteMealsRepository;
    private CompositeDisposable compositeDisposable;

    public FavoritesPresenter(FavoritesView view,
                              FavoriteMealsRepository favoriteMealsRepository) {
        this.view = view;
        this.favoriteMealsRepository = favoriteMealsRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    public void getAllFavoriteMeals() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            compositeDisposable.add(
                    favoriteMealsRepository
                            .getFavoriteMeals()
                            .subscribe(mealsItems -> {
                                view.allFavoriteMeals(mealsItems);
                                if (mealsItems.isEmpty()) {
                                    view.noFavoriteMeals();
                                }
                            }));
        } else {
            view.userNotLoggedIn();
        }
    }

    @SuppressLint("CheckResult")
    public void removeFavoriteMeal(MealsItem meal) {
        compositeDisposable.add(
                favoriteMealsRepository
                        .removeFavoriteMeal(meal).subscribe(() -> {
                                    view.onRemovedSuccess();
                                    syncFavorites();
                                },
                                error -> view.onRemovedFail()));
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
                                    .uploadFavoriteMeals(FavoritesPresenter.this, mealsId);
                        }));
    }


    public void close() {
        compositeDisposable.clear();
    }

    @Override
    public void onUploadFavoriteMealsRemoteOnSuccess() {
        view.onSyncDataSuccess();
    }

    @Override
    public void onUploadFavoriteMealsRemoteOnFail(String errorMsg) {
        view.onSyncDataFailed();
    }
}
