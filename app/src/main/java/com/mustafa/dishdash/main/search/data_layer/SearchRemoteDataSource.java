package com.mustafa.dishdash.main.search.data_layer;

import static com.mustafa.dishdash.utils.Constants.API_URL;

import com.mustafa.dishdash.main.search.data_layer.models.categories.CategoryResponse;
import com.mustafa.dishdash.main.search.data_layer.models.countries.CountriesResponse;
import com.mustafa.dishdash.main.search.data_layer.models.filter_by_name.FilterByNameResponse;
import com.mustafa.dishdash.main.search.data_layer.models.ingredients.IngredientsResponse;
import com.mustafa.dishdash.main.search.data_layer.models.ingredients.MealsItem;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRemoteDataSource {
    private ApiSearchService apiSearchService;

    public SearchRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiSearchService = retrofit.create(ApiSearchService.class);
    }

    Single<CategoryResponse> getCategories() {
        return apiSearchService.getCategories();
    }

    Single<IngredientsResponse> getIngredients() {
        return apiSearchService.getIngredients();
    }

    public Single<CountriesResponse> getCountries() {
        return apiSearchService.getCountries();
    }

    public Single<FilterByNameResponse> filterByMealName(String query) {
        return apiSearchService.filterByMealName(query);
    }

    public Single<List<MealsItem>> filterByMainIngredients(String strIngredient) {
        return apiSearchService
                .filterByMainIngredients(strIngredient);
    }
}
