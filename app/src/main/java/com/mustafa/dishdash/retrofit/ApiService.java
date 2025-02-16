package com.mustafa.dishdash.retrofit;

import com.mustafa.dishdash.retrofit.models.random_meal.Meal;
import com.mustafa.dishdash.retrofit.models.meals_short_details.MealsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("random.php")
    Call<Meal> getRandomMeal();

    @GET("filter.php")
    Call<MealsList> getAllMealsByIngredientName(@Query("i") String ingredient);

    @GET("lookup.php")
    Call<Meal> getMealById(@Query("i") String id);
}
