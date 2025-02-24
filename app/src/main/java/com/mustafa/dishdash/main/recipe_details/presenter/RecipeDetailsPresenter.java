package com.mustafa.dishdash.main.recipe_details.presenter;

import android.annotation.SuppressLint;

import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.recipe_details.view.RecipeDetailsView;

public class RecipeDetailsPresenter {
    private RecipeDetailsView view;
    private MealsRepository mealsRepository;
    private FavoriteMealsRepository favoriteMealsRepository;
    private AuthRepository authRepository;

    public RecipeDetailsPresenter(RecipeDetailsView view,
                                  MealsRepository mealsRepository,
                                  FavoriteMealsRepository favoriteMealsRepository,
                                  AuthRepository authRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.favoriteMealsRepository = favoriteMealsRepository;
        this.authRepository = authRepository;
    }

    @SuppressLint("CheckResult")
    public void getMealById(String id) {

        mealsRepository.getMealById(id)
                .subscribe(
                        mealsItemBooleanPair ->
                                view.onGetMealDetailsSuccess(mealsItemBooleanPair.first, mealsItemBooleanPair.second),
                        error -> view.onGetMealDetailsFail(error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void addMealToFavorites(MealsItem meal) {
        String userEmail = authRepository.getCurrentAuthenticatedUserEmail();
        if (userEmail != null) {
            favoriteMealsRepository.insertFavoriteMeal(meal)
                    .subscribe(() -> view.onAddedToFavoritesSuccess(),
                            error -> view.onAddedToFavoritesFail());
        } else {
            view.userNotLoggedIn();
        }
    }

    @SuppressLint("CheckResult")
    public void removeFavoriteMeal(MealsItem meal) {
        String userEmail = authRepository.getCurrentAuthenticatedUserEmail();
        if (userEmail != null) {
            favoriteMealsRepository
                    .removeFavoriteMeal(meal)
                    .subscribe(() -> view.onRemoveFavoriteSuccess(),
                            error -> view.onRemoveFavoriteFail(error.getMessage()));
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
}
