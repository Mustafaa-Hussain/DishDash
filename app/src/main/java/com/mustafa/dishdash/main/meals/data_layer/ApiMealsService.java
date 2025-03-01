package com.mustafa.dishdash.main.meals.data_layer;

import com.mustafa.dishdash.main.meals.data_layer.model.MealsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiMealsService {
    @GET("filter.php")
    Single<MealsResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealsResponse> getMealsByCountry(@Query("a") String country);

    @GET("filter.php")
    Single<MealsResponse> getMealsByIngredient(@Query("i") String ingredient);

}
