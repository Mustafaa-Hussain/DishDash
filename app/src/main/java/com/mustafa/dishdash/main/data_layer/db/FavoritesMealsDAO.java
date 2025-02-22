package com.mustafa.dishdash.main.data_layer.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mustafa.dishdash.main.data_layer.db.entities.FavoriteMeal;

import java.util.List;

@Dao
public interface FavoritesMealsDAO {

    @Query("select * from favorites_meals where userEmail = :userEmail")
    LiveData<List<FavoriteMeal>> getFavoritesMeals(String userEmail);

    @Query("select * from favorites_meals where userEmail = :userEmail and idMeal = :idMeal")
    LiveData<FavoriteMeal> getFavoriteMealById(String userEmail, String idMeal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insetFavoriteMeal(FavoriteMeal favoritesMeal);

    @Delete
    void deleteFavoriteMeal(FavoriteMeal favoritesMeal);
}
