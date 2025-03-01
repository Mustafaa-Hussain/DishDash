package com.mustafa.dishdash.main.search.data_layer;

import com.mustafa.dishdash.main.search.data_layer.models.categories.CategoryResponse;
import com.mustafa.dishdash.main.search.data_layer.models.countries.CountriesResponse;
import com.mustafa.dishdash.main.search.data_layer.models.filter_by_name.FilterByNameResponse;
import com.mustafa.dishdash.main.search.data_layer.models.ingredients.IngredientsResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchRepository {
    private static SearchRepository instance;
    private SearchRemoteDataSource remoteDataSource;

    private SearchRepository(SearchRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static SearchRepository getInstance(SearchRemoteDataSource remoteDataSource) {
        if (instance == null) {
            instance = new SearchRepository(remoteDataSource);
        }
        return instance;
    }

    public Single<CategoryResponse> getCategories() {
        return remoteDataSource
                .getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<IngredientsResponse> getIngredients() {
        return remoteDataSource
                .getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<CountriesResponse> getCountries() {
        return remoteDataSource
                .getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<FilterByNameResponse> filterByMealName(String query) {
        return remoteDataSource
                .filterByMealName(query);
    }

    public Single<Integer> getCountMealsOfMainIngredientByMainIngredient(String strIngredient) {
        return remoteDataSource
                .filterByMainIngredients(strIngredient)
                .subscribeOn(Schedulers.io())
                .map(mealsItems -> mealsItems.size())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
