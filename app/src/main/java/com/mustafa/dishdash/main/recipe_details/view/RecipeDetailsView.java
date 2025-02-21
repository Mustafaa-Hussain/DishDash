package com.mustafa.dishdash.main.recipe_details.view;

import android.util.Pair;

import com.mustafa.dishdash.main.data_layer.network.pojo.random_meal.MealsItem;

import java.util.List;

public interface RecipeDetailsView {
    void recipeDetailsResponseSuccess(MealsItem mealsItem, List<Pair<String, String>> ingredients);
    void recipeDetailsResponseFail(String errorMessage);
}
