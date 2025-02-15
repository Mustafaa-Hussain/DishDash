package com.mustafa.dishdash.retrofit;

import com.mustafa.dishdash.retrofit.models.random_meal.RandomMeal;
import com.mustafa.dishdash.retrofit.models.meals_short_details.MealsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("random.php")
    Call<RandomMeal> getRandomMeal();

    @GET("filter.php")
    Call<MealsList> getAllMealsByIngredientName(@Query("i") String ingredient);

    @GET("lookup.php")
    Call<RandomMeal> getMealById(@Query("i") String id);
}
