package com.mustafa.dishdash.main.recipe_details.view;

import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.mustafa.dishdash.main.data_layer.db.entities.FavoriteMeal;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

import java.util.List;

public interface RecipeDetailsView {
    void recipeDetailsResponseSuccess(MealsItem mealsItem);
    void recipeDetailsResponseFail(String errorMessage);
    void addedToFavorites();
    void userNotLoggedIn();

    void favoriteMeal(LiveData<FavoriteMeal> favoriteMealById);
}
