package com.mustafa.dishdash.main.data_layer.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mustafa.dishdash.main.data_layer.db.entities.FavoriteMeal;

import java.util.List;

public class FavoritesMealsLocalDatasource {
    private FavoritesMealsDAO dao;

    public FavoritesMealsLocalDatasource(Context context) {
        dao = FavoritesMealsDB.getInstance(context).getFavoritesMealsDAO();
    }

    public LiveData<List<FavoriteMeal>> getFavoritesMeals(String userEmail) {
        return dao.getFavoritesMeals(userEmail);
    }

    public void insetFavoriteMeal(FavoriteMeal favoritesMeal) {
        new Thread(() -> {
            dao.insetFavoriteMeal(favoritesMeal);
        }).start();
    }

    public void deleteFavoriteMeal(FavoriteMeal favoritesMeal) {
        new Thread(() -> {
            dao.deleteFavoriteMeal(favoritesMeal);
        }).start();
    }

    public LiveData<FavoriteMeal> getFavoriteMealById(String userEmail, String mealId) {
        return dao.getFavoriteMealById(userEmail, mealId);
    }
}
