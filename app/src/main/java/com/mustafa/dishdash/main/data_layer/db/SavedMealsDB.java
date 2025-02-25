package com.mustafa.dishdash.main.data_layer.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mustafa.dishdash.main.data_layer.db.favorites.FavoritesMealsDAO;
import com.mustafa.dishdash.main.data_layer.db.future_planes.FuturePlanesDAO;
import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;


@Database(entities = {MealsItem.class, FuturePlane.class}, version = 1)
public abstract class SavedMealsDB extends RoomDatabase {
    private static SavedMealsDB instance;

    public abstract FavoritesMealsDAO getFavoritesMealsDAO();

    public abstract FuturePlanesDAO getFuturePlanesDAO();

    public static synchronized SavedMealsDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            SavedMealsDB.class, "saved_meals")
                    .build();
        }
        return instance;
    }
}
