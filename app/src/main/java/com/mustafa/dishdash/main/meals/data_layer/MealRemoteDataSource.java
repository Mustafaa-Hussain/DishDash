package com.mustafa.dishdash.main.meals.data_layer;

import com.mustafa.dishdash.main.meals.data_layer.model.MealsResponse;
import com.mustafa.dishdash.utils.Constants;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSource {
    private ApiMealsService apiMealsService;

    public MealRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiMealsService = retrofit.create(ApiMealsService.class);
    }

    public Single<MealsResponse> getMealsByCategory(String category) {
        return apiMealsService.getMealsByCategory(category);
    }

    public Single<MealsResponse> getMealsByCountry(String country) {
        return apiMealsService.getMealsByCountry(country);
    }

    public Single<MealsResponse> getMealsByIngredient(String ingredient) {
        return apiMealsService.getMealsByIngredient(ingredient);
    }

}
