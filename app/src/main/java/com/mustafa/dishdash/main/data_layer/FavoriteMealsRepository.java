package com.mustafa.dishdash.main.data_layer;

import com.mustafa.dishdash.main.data_layer.db.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.FavoritesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.GetRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.UploadRemoteFavoriteMealsCallBack;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteMealsRepository {

    private static FavoriteMealsRepository instance;
    private FavoritesMealsLocalDatasource favoritesMealsLocalDatasource;

    private FavoritesRemoteDatasource favoritesRemoteDatasource;

    private FavoriteMealsRepository(FavoritesMealsLocalDatasource favoritesMealsLocalDatasource
            , FavoritesRemoteDatasource favoritesRemoteDatasource) {
        this.favoritesMealsLocalDatasource = favoritesMealsLocalDatasource;
        this.favoritesRemoteDatasource = favoritesRemoteDatasource;
    }

    public static FavoriteMealsRepository getInstance(FavoritesMealsLocalDatasource favoritesMealsDatasource
            , FavoritesRemoteDatasource favoritesRemoteDatasource) {
        if (instance == null) {
            instance = new FavoriteMealsRepository(favoritesMealsDatasource
                    , favoritesRemoteDatasource);
        }
        return instance;
    }

    public Flowable<List<MealsItem>> getFavoriteMeals() {
        return favoritesMealsLocalDatasource
                .getFavoritesMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable insertFavoriteMeal(MealsItem meal) {
        return favoritesMealsLocalDatasource
                .insetFavoriteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Completable removeFavoriteMeal(MealsItem meal) {
        return favoritesMealsLocalDatasource
                .deleteFavoriteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable clearFavorites() {
        return favoritesMealsLocalDatasource
                .clearFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getAllFavoriteMeals(GetRemoteFavoriteMealsCallBack callBack) {
        favoritesRemoteDatasource.getFavoriteMeals(callBack);
    }


    public void uploadFavoriteMeals(UploadRemoteFavoriteMealsCallBack callBack, List<String> mealsIds) {
        favoritesRemoteDatasource.uploadFavoriteMeals(callBack, mealsIds);
    }


}
