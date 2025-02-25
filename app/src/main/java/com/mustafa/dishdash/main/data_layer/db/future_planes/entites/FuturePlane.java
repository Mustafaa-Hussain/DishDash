package com.mustafa.dishdash.main.data_layer.db.future_planes.entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

@Entity(tableName = "future_planes", primaryKeys = {"idMeal", "day", "month", "year"})
public class FuturePlane extends MealsItem {
    @NonNull
    private Integer day;
    @NonNull
    private Integer month;
    @NonNull
    private Integer year;

    public FuturePlane() {
        super();
    }

    public FuturePlane(MealsItem meal, Integer day, Integer month, Integer year) {
        super(meal);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
