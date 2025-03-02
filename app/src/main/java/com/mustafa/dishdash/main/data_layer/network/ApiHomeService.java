package com.mustafa.dishdash.main.data_layer.network;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.Meal;
import com.mustafa.dishdash.main.data_layer.pojo.meals_short_details.MealsList;
import com.mustafa.dishdash.main.data_layer.pojo.search.MealsResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.categories.CategoryResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.countries.CountriesResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.filter_by_name.FilterByNameResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.ingredients.IngredientsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHomeService {
    @GET("random.php")
    Single<Meal> getRandomMeal();

    @GET("filter.php")
    Single<MealsList> getAllMealsByIngredientName(@Query("i") String ingredient);

    @GET("lookup.php")
    Single<Meal> getMealById(@Query("i") String id);


    @GET("categories.php")
    Single<CategoryResponse> getCategories();

    @GET("list.php?i=list")
    Single<IngredientsResponse> getIngredients();

    @GET("list.php?a=list")
    Single<CountriesResponse> getCountries();

    @GET("search.php")
    Single<FilterByNameResponse> filterByMealName(@Query("s") String query);

    @GET("filter.php")
    Single<MealsResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealsResponse> getMealsByCountry(@Query("a") String country);

    @GET("filter.php")
    Single<MealsResponse> getMealsByIngredient(@Query("i") String ingredient);
}
