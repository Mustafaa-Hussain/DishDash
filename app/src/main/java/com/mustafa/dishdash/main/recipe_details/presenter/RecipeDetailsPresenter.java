package com.mustafa.dishdash.main.recipe_details.presenter;

import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.network.GetMealByIdNetworkCallBack;
import com.mustafa.dishdash.main.data_layer.network.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.recipe_details.view.RecipeDetailsView;

public class RecipeDetailsPresenter implements GetMealByIdNetworkCallBack {
    RecipeDetailsView view;
    MealsRepository repository;

    public RecipeDetailsPresenter(RecipeDetailsView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void getMealById(String id){
        repository.getMealById(this, id);
    }


    @Override
    public void onGetMealByIdCallSuccess(MealsItem meal) {
        view.recipeDetailsResponseSuccess(meal);
    }

    @Override
    public void onGetMealByIdCallFail(String errorMessage) {
        view.recipeDetailsResponseFail(errorMessage);
    }
}
