package com.mustafa.dishdash.main.data_layer.network;

import static com.mustafa.dishdash.utils.Constant.API_URL;

import com.mustafa.dishdash.main.data_layer.network.pojo.meals_short_details.MealsList;
import com.mustafa.dishdash.main.data_layer.network.pojo.random_meal.Meal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDatasource {

    private ApiHomeService apiService;

    public MealsRemoteDatasource() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiHomeService.class);
    }

    public void getRandomMeal(GetRandomMealNetworkCallBack randomMealNetworkCallBack) {
        Call<Meal> callRandomMeal = apiService.getRandomMeal();

        callRandomMeal.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if (response.isSuccessful() && !response.body().getMeals().isEmpty()) {
                    randomMealNetworkCallBack.onRandomMealCallSuccess(response.body().getMeals().get(0));
                } else {
                    randomMealNetworkCallBack.onRandomMealCallFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<Meal> call, Throwable throwable) {
                randomMealNetworkCallBack.onRandomMealCallFail(throwable.getMessage());
            }
        });
    }

    public void getMealById(GetMealByIdNetworkCallBack getMealByIdNetworkCallBack, String id) {
        Call<Meal> mealCall = apiService.getMealById(id);

        mealCall.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if (response.isSuccessful()) {
                    getMealByIdNetworkCallBack.onGetMealByIdCallSuccess(response.body().getMeals().get(0));
                } else {
                    getMealByIdNetworkCallBack.onGetMealByIdCallFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<Meal> call, Throwable throwable) {
                getMealByIdNetworkCallBack.onGetMealByIdCallFail(throwable.getMessage());
            }
        });
    }

    public void getAllMeals(GetAllMealNetworkCallBack getAllMealNetworkCallBack) {
        Call<MealsList> callMeals = apiService.getAllMealsByIngredientName("");
        callMeals.enqueue(new Callback<MealsList>() {
            @Override
            public void onResponse(Call<MealsList> call, Response<MealsList> response) {
                if (response.isSuccessful()) {
                    getAllMealNetworkCallBack.onGetAllMealCallSuccess(response.body());
                } else {
                    getAllMealNetworkCallBack.onGetAllMealCallFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<MealsList> call, Throwable throwable) {
                getAllMealNetworkCallBack.onGetAllMealCallFail(throwable.getMessage());
            }
        });
    }
}
