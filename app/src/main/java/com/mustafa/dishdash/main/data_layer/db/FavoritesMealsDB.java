package com.mustafa.dishdash.main.data_layer.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mustafa.dishdash.main.data_layer.db.entities.FavoriteMeal;

@Database(entities = {FavoriteMeal.class}, version = 1)
public abstract class FavoritesMealsDB extends RoomDatabase {
    private static FavoritesMealsDB instance;

    public abstract FavoritesMealsDAO getFavoritesMealsDAO();

    public static FavoritesMealsDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FavoritesMealsDB.class, "favorites_meals")
                    .build();
        }
        return instance;
    }
}
