package com.mustafa.dishdash.main.data_layer.network;

import com.mustafa.dishdash.main.data_layer.network.pojo.meals_short_details.MealsList;

public interface GetAllMealNetworkCallBack {
    void onGetAllMealCallSuccess(MealsList mealsList);
    void onGetAllMealCallFail(String errorMessage);
}
