package com.mustafa.dishdash.main.home.presenter;

import android.annotation.SuppressLint;

import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.home.view.HomeView;

import java.util.Calendar;

public class HomePresenter {

    private HomeView view;
    private MealsRepository repository;

    public HomePresenter(HomeView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @SuppressLint("CheckResult")
    public void getRandomMeal() {
        String id = repository.getSavedMealId();
        int day = repository.getDateOfSavedMeal();

        if (id.isEmpty() || day != Calendar.getInstance().get(Calendar.DATE)) {
            repository
                    .getRandomMeal()
                    .subscribe(mealsList -> {
                                view.randomMealResultSuccess(mealsList.getMeals().get(0));
                                repository.saveTodayMealId(mealsList.getMeals().get(0).getIdMeal());
                            },
                            error -> view.randomMealResultFail(error.getMessage()));
        } else {
            repository
                    .getMealById(id)
                    .subscribe(mealsItemBooleanPair -> view.mealByIdResultSuccess(mealsItemBooleanPair.first),
                            error -> view.mealByIdResultFail(error.getMessage()));
        }
    }

    @SuppressLint("CheckResult")
    public void getAllMeals() {
        repository
                .getAllMeals()
                .subscribe(mealsList -> view.allMealsResultSuccess(mealsList),
                        error -> view.allMealsResultFail(error.getMessage()));
    }
}
