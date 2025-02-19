package com.mustafa.dishdash.main.home.presenter;

import com.mustafa.dishdash.main.home.data_layer.MealsRepository;
import com.mustafa.dishdash.main.home.data_layer.network.GetAllMealNetworkCallBack;
import com.mustafa.dishdash.main.home.data_layer.network.GetMealByIdNetworkCallBack;
import com.mustafa.dishdash.main.home.data_layer.network.GetRandomMealNetworkCallBack;
import com.mustafa.dishdash.main.home.data_layer.network.models.meals_short_details.MealsList;
import com.mustafa.dishdash.main.home.data_layer.network.models.random_meal.Meal;
import com.mustafa.dishdash.main.home.data_layer.network.models.random_meal.MealsItem;
import com.mustafa.dishdash.main.home.view.HomeView;

import java.util.Calendar;

public class HomePresenter implements GetAllMealNetworkCallBack, GetMealByIdNetworkCallBack, GetRandomMealNetworkCallBack {

    private HomeView view;
    private MealsRepository repository;

    public HomePresenter(HomeView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void getRandomMeal() {
        int id = repository.getSavedMealId();
        int day = repository.getDateOfSavedMeal();

        if (id == -1 || day != Calendar.getInstance().get(Calendar.DATE)) {
            repository.getRandomMeal(this);
        } else {
            repository.getMealById(this, id + "");
        }
    }

    public void getAllMeals() {
        repository.getAllMeals(this);
    }

    @Override
    public void onGetAllMealCallSuccess(MealsList mealsList) {
        view.allMealsResultSuccess(mealsList);
    }

    @Override
    public void onGetAllMealCallFail(String errorMessage) {
        view.allMealsResultFail(errorMessage);
    }

    @Override
    public void onGetMealByIdCallSuccess(MealsItem meal) {
        view.mealByIdResultSuccess(meal);
    }

    @Override
    public void onGetMealByIdCallFail(String errorMessage) {
        view.mealByIdResultFail(errorMessage);
    }

    @Override
    public void onRandomMealCallSuccess(MealsItem meal) {
        view.randomMealResultSuccess(meal);
        repository.saveTodayMealId(Integer.parseInt(meal.getIdMeal()));
    }

    @Override
    public void onRandomMealCallFail(String errorMessage) {
        view.randomMealResultFail(errorMessage);
    }
}
