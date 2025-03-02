package com.mustafa.dishdash.main.meals.view;

import com.mustafa.dishdash.main.data_layer.pojo.search.MealsItem;

import java.util.List;

public interface MealsView {
    void onMealResponseSuccess(List<MealsItem> mealsItemList);

    void onMealResponseFail(String errorMsg);
}
