package com.mustafa.dishdash.main.favorites.view;

import androidx.lifecycle.LiveData;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

import java.util.List;

public interface FavoritesView {
    void allFavoriteMeals(List<MealsItem> mealsList);
    void noFavoriteMeals();
    void onRemovedSuccess();
    void onRemovedFail();
    void userNotLoggedIn();
}
