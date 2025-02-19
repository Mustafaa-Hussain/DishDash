package com.mustafa.dishdash.main.data_layer.network;

import com.mustafa.dishdash.main.data_layer.network.pojo.random_meal.MealsItem;

public interface GetRandomMealNetworkCallBack {
    void onRandomMealCallSuccess(MealsItem meal);
    void onRandomMealCallFail(String errorMessage);
}
