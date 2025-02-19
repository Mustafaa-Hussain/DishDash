package com.mustafa.dishdash.main.recipe_details.view;

import com.mustafa.dishdash.main.data_layer.network.pojo.random_meal.MealsItem;

public interface RecipeDetailsView {
    void recipeDetailsResponseSuccess(MealsItem mealsItem);
    void recipeDetailsResponseFail(String errorMessage);
}
