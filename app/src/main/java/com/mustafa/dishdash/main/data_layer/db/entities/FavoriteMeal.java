package com.mustafa.dishdash.main.data_layer.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

@Entity(tableName = "favorites_meals", primaryKeys = {"userEmail", "idMeal"})
public class FavoriteMeal extends MealsItem {
    @NonNull
    private String userEmail;
    public FavoriteMeal(@NonNull String userEmail, MealsItem meal) {
        super(meal);
        this.userEmail = userEmail;
    }

    public FavoriteMeal() {
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }
}
