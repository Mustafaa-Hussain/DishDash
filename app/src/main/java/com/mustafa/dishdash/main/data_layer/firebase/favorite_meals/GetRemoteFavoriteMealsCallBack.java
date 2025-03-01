package com.mustafa.dishdash.main.data_layer.firebase.favorite_meals;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

import java.util.List;

public interface GetRemoteFavoriteMealsCallBack {
    void getAllFavoriteMealsRemoteOnSuccess(List<String> mealsIds);
    void getAllFavoriteMealsRemoteOnFail(String errorMsg);
}
