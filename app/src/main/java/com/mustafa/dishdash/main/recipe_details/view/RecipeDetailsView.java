package com.mustafa.dishdash.main.recipe_details.view;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

public interface RecipeDetailsView {
    void onGetMealDetailsSuccess(MealsItem mealsItem, Boolean isFavorite);

    void onGetMealDetailsFail(String errorMessage);

    void userNotLoggedIn();


    void onAddedToFavoritesSuccess();

    void onAddedToFavoritesFail();

    void onRemoveFavoriteSuccess();

    void onRemoveFavoriteFail(String errorMsg);

    void onSyncSuccess();

    void onSyncDataFailed();
}
