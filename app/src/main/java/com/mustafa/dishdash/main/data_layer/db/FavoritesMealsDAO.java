package com.mustafa.dishdash.main.data_layer.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavoritesMealsDAO {

    @Query("select * from favorites_meals")
    Flowable<List<MealsItem>> getFavoritesMeals();

    @Query("select * from favorites_meals where idMeal = :idMeal")
    Single<MealsItem> getFavoriteMealById(String idMeal);

    @Query("select COUNT(*) from favorites_meals where idMeal = :idMeal")
    Single<Integer> isFavoriteMeal(String idMeal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insetFavoriteMeal(MealsItem favoritesMeal);

    @Delete
    Completable deleteFavoriteMeal(MealsItem favoritesMeal);

    @Query("delete from favorites_meals")
    Completable clearFavorites();
}
