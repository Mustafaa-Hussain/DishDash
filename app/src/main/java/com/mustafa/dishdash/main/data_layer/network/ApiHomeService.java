package com.mustafa.dishdash.main.data_layer.network;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.Meal;
import com.mustafa.dishdash.main.data_layer.pojo.meals_short_details.MealsList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHomeService {
    @GET("random.php")
    Single<Meal> getRandomMeal();

    @GET("filter.php")
    Single<MealsList> getAllMealsByIngredientName(@Query("i") String ingredient);

    @GET("lookup.php")
    Single<Meal> getMealById(@Query("i") String id);
}
