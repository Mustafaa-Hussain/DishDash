package com.mustafa.dishdash.main.home.data_layer.network;

import com.mustafa.dishdash.main.home.data_layer.network.models.meals_short_details.MealsList;

public interface GetAllMealNetworkCallBack {
    void onGetAllMealCallSuccess(MealsList mealsList);
    void onGetAllMealCallFail(String errorMessage);
}
