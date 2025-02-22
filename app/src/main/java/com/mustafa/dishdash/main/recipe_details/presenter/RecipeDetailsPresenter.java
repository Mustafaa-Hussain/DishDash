package com.mustafa.dishdash.main.recipe_details.presenter;

import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.db.entities.FavoriteMeal;
import com.mustafa.dishdash.main.data_layer.network.GetMealByIdNetworkCallBack;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.recipe_details.view.RecipeDetailsView;

public class RecipeDetailsPresenter implements GetMealByIdNetworkCallBack {
    private RecipeDetailsView view;
    private MealsRepository mealsRepository;
    private AuthRepository authRepository;

    public RecipeDetailsPresenter(RecipeDetailsView view,
                                  MealsRepository mealsRepository,
                                  AuthRepository authRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.authRepository = authRepository;
    }

    public void getMealById(String id) {
        mealsRepository.getMealById(this, id);
    }


    @Override
    public void onGetMealByIdCallSuccess(MealsItem mealItem) {
        view.recipeDetailsResponseSuccess(mealItem);
    }

    @Override
    public void onGetMealByIdCallFail(String errorMessage) {
        view.recipeDetailsResponseFail(errorMessage);
    }

    public void addMealToFavorites(MealsItem meal) {
        String userEmail = authRepository.getCurrentAuthenticatedUserEmail();
        if (userEmail != null) {
            mealsRepository.insertFavoriteMeal(new FavoriteMeal(userEmail, meal));
            view.addedToFavorites();
        } else {
            view.userNotLoggedIn();
        }
    }

    public void removeFavoriteMeal(MealsItem meal) {
        String userEmail = authRepository.getCurrentAuthenticatedUserEmail();
        if (userEmail != null) {
            mealsRepository.removeFavoriteMeal(new FavoriteMeal(userEmail, meal));
        } else {
            view.userNotLoggedIn();
        }
    }

    public void getFavoriteMealById(String mealId) {
        String userEmail = authRepository.getCurrentAuthenticatedUserEmail();
        if (userEmail != null) {
            view.favoriteMeal(mealsRepository.getFavoriteMealById(userEmail, mealId));
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
