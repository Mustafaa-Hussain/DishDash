package com.mustafa.dishdash.main.data_layer.firebase.future_plane.entities;

public class FuturePlaneEntity {
    private String mealId;
    private int day;
    private int month;
    private int year;

    public FuturePlaneEntity() {
    }

    public FuturePlaneEntity(String mealId, int day, int month, int year) {
        this.mealId = mealId;
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public FuturePlaneEntity(String mealId, long day, long month, long year) {
        this.mealId = mealId;
        this.day = (int) day;
        this.month = (int) month;
        this.year = (int) year;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "FuturePlaneEntity{" +
                "mealId='" + mealId + '\'' +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
