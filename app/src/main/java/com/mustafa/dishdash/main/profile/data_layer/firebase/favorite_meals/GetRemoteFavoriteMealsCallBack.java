package com.mustafa.dishdash.main.profile.data_layer.firebase.favorite_meals;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

import java.util.List;

public interface GetRemoteFavoriteMealsCallBack {
    void getAllFavoriteMealsRemoteOnSuccess(List<MealsItem> favoriteMeals);
    void getAllFavoriteMealsRemoteOnFail(String errorMsg);
}
