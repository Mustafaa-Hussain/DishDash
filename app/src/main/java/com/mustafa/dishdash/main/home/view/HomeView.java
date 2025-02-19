package com.mustafa.dishdash.main.home.view;

import com.mustafa.dishdash.main.data_layer.network.pojo.meals_short_details.MealsList;
import com.mustafa.dishdash.main.data_layer.network.pojo.random_meal.MealsItem;

public interface HomeView {
    void allMealsResultSuccess(MealsList meal);

    void allMealsResultFail(String errorMessage);

    void mealByIdResultSuccess(MealsItem meal);

    void mealByIdResultFail(String errorMessage);

    void randomMealResultSuccess(MealsItem meal);

    void randomMealResultFail(String errorMessage);
}
