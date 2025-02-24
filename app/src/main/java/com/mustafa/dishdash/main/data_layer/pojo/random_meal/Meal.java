package com.mustafa.dishdash.main.data_layer.pojo.random_meal;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private List<MealsItem> meals;

    public Meal() {
    }

    public Meal(MealsItem meals) {
        this.meals = new ArrayList<>();
        this.meals.add(meals);
    }

    public List<MealsItem> getMeals() {
        return meals;
    }

}