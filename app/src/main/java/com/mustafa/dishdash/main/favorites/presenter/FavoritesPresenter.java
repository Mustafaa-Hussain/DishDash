package com.mustafa.dishdash.main.favorites.presenter;

import android.annotation.SuppressLint;

import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.favorites.view.FavoritesView;

import io.reactivex.rxjava3.core.Observer;

public class FavoritesPresenter {
    private FavoritesView view;
    private FavoriteMealsRepository favoriteMealsRepository;

    public FavoritesPresenter(FavoritesView view,
                              FavoriteMealsRepository favoriteMealsRepository) {
        this.view = view;
        this.favoriteMealsRepository = favoriteMealsRepository;
    }

    @SuppressLint("CheckResult")
    public void getAllFavoriteMeals() {
        favoriteMealsRepository
                .getFavoriteMeals()
                .subscribe(mealsItems -> {
                    view.allFavoriteMeals(mealsItems);
                    if (mealsItems.isEmpty()) {
                        view.noFavoriteMeals();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void removeFavoriteMeal(MealsItem meal) {
        favoriteMealsRepository
                .removeFavoriteMeal(meal).subscribe(() -> view.onRemovedSuccess(),
                        error -> view.onRemovedFail());
    }
}
