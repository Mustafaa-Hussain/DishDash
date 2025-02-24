package com.mustafa.dishdash.main.data_layer;

import com.mustafa.dishdash.main.data_layer.db.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteMealsRepository {

    private static FavoriteMealsRepository instance;
    private FavoritesMealsLocalDatasource favoritesMealsDatasource;

    private FavoriteMealsRepository(FavoritesMealsLocalDatasource favoritesMealsDatasource) {
        this.favoritesMealsDatasource = favoritesMealsDatasource;
    }

    public static FavoriteMealsRepository getInstance(FavoritesMealsLocalDatasource favoritesMealsDatasource) {
        if (instance == null) {
            instance = new FavoriteMealsRepository(favoritesMealsDatasource);
        }
        return instance;
    }

    public Flowable<List<MealsItem>> getFavoriteMeals() {
        return favoritesMealsDatasource
                .getFavoritesMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable insertFavoriteMeal(MealsItem meal) {
        return favoritesMealsDatasource
                .insetFavoriteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable removeFavoriteMeal(MealsItem meal) {
        return favoritesMealsDatasource
                .deleteFavoriteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MealsItem> getFavoriteMealById(String mealId) {
        return favoritesMealsDatasource
                .getFavoriteMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Integer> isFavoriteMeal(String id) {
        return favoritesMealsDatasource
                .isFavoriteMeal(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
