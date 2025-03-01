package com.mustafa.dishdash.main.meals.data_layer;

import com.mustafa.dishdash.main.meals.data_layer.model.MealsResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepository {
    private static MealsRepository instance;
    private MealRemoteDataSource remoteDataSource;

    private MealsRepository(MealRemoteDataSource mealRemoteDataSource) {
        this.remoteDataSource = mealRemoteDataSource;
    }

    public static MealsRepository getInstance(MealRemoteDataSource remoteDataSource) {
        if (instance == null) {
            instance = new MealsRepository(remoteDataSource);
        }
        return instance;
    }

    public Single<MealsResponse> getMealsByCategory(String category) {
        return remoteDataSource
                .getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MealsResponse> getMealsByCountry(String country) {
        return remoteDataSource
                .getMealsByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MealsResponse> getMealsByIngredient(String ingredient) {
        return remoteDataSource
                .getMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
