package com.mustafa.dishdash.main.data_layer.network;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

public interface GetMealByIdNetworkCallBack {
    void onGetMealByIdCallSuccess(MealsItem meal);
    void onGetMealByIdCallFail(String errorMessage);
}
