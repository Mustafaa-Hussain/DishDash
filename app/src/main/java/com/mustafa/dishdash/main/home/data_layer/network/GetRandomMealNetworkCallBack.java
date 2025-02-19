package com.mustafa.dishdash.main.home.data_layer.network;

import com.mustafa.dishdash.main.home.data_layer.network.models.random_meal.Meal;
import com.mustafa.dishdash.main.home.data_layer.network.models.random_meal.MealsItem;

public interface GetRandomMealNetworkCallBack {
    void onRandomMealCallSuccess(MealsItem meal);
    void onRandomMealCallFail(String errorMessage);
}
