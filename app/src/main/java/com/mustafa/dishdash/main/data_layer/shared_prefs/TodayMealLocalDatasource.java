package com.mustafa.dishdash.main.data_layer.shared_prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.mustafa.dishdash.utils.Constant;

import java.util.Calendar;

public class TodayMealLocalDatasource {
    private Context context;

    public TodayMealLocalDatasource(Context context) {
        this.context = context;
    }

    public int getSavedMealId() {
        SharedPreferences sharedPrefs = context.getSharedPreferences(Constant.DAY_RANDOM_MEAL, Context.MODE_PRIVATE);
        return sharedPrefs.getInt(Constant.MEAL_ID, -1);
    }

    public int getDateOfSavedMeal(){
        SharedPreferences sharedPrefs = context.getSharedPreferences(Constant.DAY_RANDOM_MEAL, Context.MODE_PRIVATE);
        return sharedPrefs.getInt(Constant.DAY, -1);
    }

    public void saveTodayMealId(int id) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(Constant.DAY_RANDOM_MEAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(Constant.MEAL_ID, id);
        editor.putInt(Constant.DAY, Calendar.getInstance().get(Calendar.DATE));
        editor.apply();
    }
}
