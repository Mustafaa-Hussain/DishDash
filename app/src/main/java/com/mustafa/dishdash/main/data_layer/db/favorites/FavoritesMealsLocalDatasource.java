package com.mustafa.dishdash.main.data_layer.db.favorites;

import android.content.Context;

import com.mustafa.dishdash.main.data_layer.db.SavedMealsDB;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FavoritesMealsLocalDatasource {
    private FavoritesMealsDAO dao;

    public FavoritesMealsLocalDatasource(Context context) {
        dao = SavedMealsDB.getInstance(context).getFavoritesMealsDAO();
    }

    public Flowable<List<MealsItem>> getFavoritesMeals() {
        return dao.getFavoritesMeals();
    }

    public Single<MealsItem> getFavoriteMealById(String mealId) {
        return dao.getFavoriteMealById(mealId);
    }

    public Single<Integer> isFavoriteMeal(String id) {
        return dao.isFavoriteMeal(id);
    }

    public Completable insetFavoriteMeal(MealsItem meal) {
        return dao.insetFavoriteMeal(meal);
    }

    public Completable deleteFavoriteMeal(MealsItem meal) {
        return dao.deleteFavoriteMeal(meal);
    }

    public Completable clearFavorites(){
        return dao.clearFavorites();
    }
}
