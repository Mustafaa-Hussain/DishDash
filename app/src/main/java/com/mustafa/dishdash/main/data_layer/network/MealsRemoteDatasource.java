package com.mustafa.dishdash.main.data_layer.network;

import static com.mustafa.dishdash.utils.Constants.API_URL;

import com.mustafa.dishdash.main.data_layer.pojo.meals_short_details.MealsList;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.Meal;
import com.mustafa.dishdash.main.data_layer.pojo.search.MealsResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.categories.CategoryResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.countries.CountriesResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.filter_by_name.FilterByNameResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.ingredients.IngredientsResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDatasource {

    private ApiHomeService apiService;

    public MealsRemoteDatasource() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiHomeService.class);
    }

    public Single<Meal> getRandomMeal() {
        return apiService.getRandomMeal();
    }

    public Single<Meal> getMealById(String id) {
        return apiService.getMealById(id);
    }

    public Single<MealsList> getAllMeals() {
        return apiService.getAllMealsByIngredientName("");
    }


    public Single<CategoryResponse> getCategories() {
        return apiService.getCategories();
    }

    public Single<IngredientsResponse> getIngredients() {
        return apiService.getIngredients();
    }

    public Single<CountriesResponse> getCountries() {
        return apiService.getCountries();
    }

    public Single<FilterByNameResponse> filterByMealName(String query) {
        return apiService.filterByMealName(query);
    }

    public Single<MealsResponse> getMealsByCategory(String category) {
        return apiService.getMealsByCategory(category);
    }

    public Single<MealsResponse> getMealsByCountry(String country) {
        return apiService.getMealsByCountry(country);
    }

    public Single<MealsResponse> getMealsByIngredient(String ingredient) {
        return apiService.getMealsByIngredient(ingredient);
    }

}
