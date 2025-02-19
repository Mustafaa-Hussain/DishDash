package com.mustafa.dishdash.main.home.data_layer.network;

import com.mustafa.dishdash.main.home.data_layer.network.models.random_meal.Meal;
import com.mustafa.dishdash.main.home.data_layer.network.models.random_meal.MealsItem;

public interface GetMealByIdNetworkCallBack {
    void onGetMealByIdCallSuccess(MealsItem meal);
    void onGetMealByIdCallFail(String errorMessage);
}
