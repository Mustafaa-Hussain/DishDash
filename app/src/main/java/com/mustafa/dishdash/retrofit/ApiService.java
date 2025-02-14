package com.mustafa.dishdash.retrofit;

import com.mustafa.dishdash.retrofit.models.random_meal.RandomMeal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("random.php")
    Call<RandomMeal> getRandomMeal();
}
