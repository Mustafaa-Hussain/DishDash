package com.mustafa.dishdash.main.home.data_layer.network;

import com.mustafa.dishdash.main.home.data_layer.network.models.random_meal.Meal;
import com.mustafa.dishdash.main.home.data_layer.network.models.meals_short_details.MealsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHomeService {
    @GET("random.php")
    Call<Meal> getRandomMeal();

    @GET("filter.php")
    Call<MealsList> getAllMealsByIngredientName(@Query("i") String ingredient);

    @GET("lookup.php")
    Call<Meal> getMealById(@Query("i") String id);
}
