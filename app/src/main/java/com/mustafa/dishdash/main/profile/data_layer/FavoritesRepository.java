package com.mustafa.dishdash.main.profile.data_layer;

import com.mustafa.dishdash.main.profile.data_layer.firebase.favorite_meals.FavoritesRemoteDatasource;
import com.mustafa.dishdash.main.profile.data_layer.firebase.favorite_meals.GetRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.profile.data_layer.firebase.favorite_meals.UploadRemoteFavoriteMealsCallBack;

import java.util.List;

public class FavoritesRepository {
    public static FavoritesRepository instance;
    private FavoritesRemoteDatasource favoritesRemoteDatasource;

    private FavoritesRepository(FavoritesRemoteDatasource favoritesRemoteDatasource) {
        this.favoritesRemoteDatasource = favoritesRemoteDatasource;
    }

    public static FavoritesRepository getInstance(FavoritesRemoteDatasource favoritesRemoteDatasource) {
        if (instance == null) {
            instance = new FavoritesRepository(favoritesRemoteDatasource);
        }
        return instance;
    }

    public void getAllFavoriteMeals(GetRemoteFavoriteMealsCallBack callBack) {
        favoritesRemoteDatasource.getFavoriteMeals(callBack);
    }

    public void uploadFavoriteMeals(UploadRemoteFavoriteMealsCallBack callBack, List<String> mealsIds) {
        favoritesRemoteDatasource.uploadFavoriteMeals(callBack, mealsIds);
    }

}
