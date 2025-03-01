package com.mustafa.dishdash.main.meals.presenter;

import android.annotation.SuppressLint;

import com.mustafa.dishdash.main.meals.data_layer.MealsRepository;
import com.mustafa.dishdash.main.meals.view.MealsView;

public class MealsPresenter {
    private MealsRepository repository;
    private MealsView view;

    public MealsPresenter(MealsRepository repository, MealsView view) {
        this.repository = repository;
        this.view = view;
    }

    @SuppressLint("CheckResult")
    public void getMealsByCategory(String category) {
        repository.getMealsByCategory(category)
                .subscribe(mealsResponse -> view.onMealResponseSuccess(mealsResponse.getMeals()),
                        error -> view.onMealResponseFail(error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getMealsByCountry(String country) {
        repository.getMealsByCountry(country)
                .subscribe(mealsResponse -> view.onMealResponseSuccess(mealsResponse.getMeals()),
                        error -> view.onMealResponseFail(error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getMealsByIngredient(String ingredient) {
        repository.getMealsByCategory(ingredient)
                .subscribe(mealsResponse -> view.onMealResponseSuccess(mealsResponse.getMeals()),
                        error -> view.onMealResponseFail(error.getMessage()));
    }

    public void getData(String filterType, String query) {
        switch (filterType) {
            case "c":
                getMealsByCategory(query);
                break;
            case "a":
                getMealsByCountry(query);
                break;
            case "i":
                getMealsByIngredient(query);
                break;
        }
    }
}
