package com.mustafa.dishdash.main.search.data_layer;


import com.mustafa.dishdash.main.search.data_layer.models.categories.CategoryResponse;
import com.mustafa.dishdash.main.search.data_layer.models.countries.CountriesResponse;
import com.mustafa.dishdash.main.search.data_layer.models.filter_by_name.FilterByNameResponse;
import com.mustafa.dishdash.main.search.data_layer.models.ingredients.IngredientsResponse;
import com.mustafa.dishdash.main.search.view.adapter.SearchItem;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiSearchService {
    @GET("categories.php")
    Single<CategoryResponse> getCategories();

    @GET("list.php?i=list")
    Single<IngredientsResponse> getIngredients();

    @GET("list.php?a=list")
    Single<CountriesResponse> getCountries();

    @GET("search.php")
    Single<FilterByNameResponse> filterByMealName(@Query("s") String query);
}
