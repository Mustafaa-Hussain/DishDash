package com.mustafa.dishdash.main.meals.view;

import com.mustafa.dishdash.main.meals.data_layer.model.MealsItem;

import java.util.List;

public interface MealsView {
    void onMealResponseSuccess(List<MealsItem> mealsItemList);

    void onMealResponseFail(String errorMsg);
}
