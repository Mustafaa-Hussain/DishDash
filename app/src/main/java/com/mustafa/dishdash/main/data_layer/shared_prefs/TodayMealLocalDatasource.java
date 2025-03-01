package com.mustafa.dishdash.main.data_layer.shared_prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.mustafa.dishdash.utils.Constants;

import java.util.Calendar;

public class TodayMealLocalDatasource {
    private Context context;

    public TodayMealLocalDatasource(Context context) {
        this.context = context;
    }

    public String getSavedMealId() {
        SharedPreferences sharedPrefs = context.getSharedPreferences(Constants.DAY_RANDOM_MEAL, Context.MODE_PRIVATE);
        return sharedPrefs.getString(Constants.MEAL_ID, "");
    }

    public int getDateOfSavedMeal(){
        SharedPreferences sharedPrefs = context.getSharedPreferences(Constants.DAY_RANDOM_MEAL, Context.MODE_PRIVATE);
        return sharedPrefs.getInt(Constants.DAY, -1);
    }

    public void saveTodayMealId(String id) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(Constants.DAY_RANDOM_MEAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(Constants.MEAL_ID, id);
        editor.putInt(Constants.DAY, Calendar.getInstance().get(Calendar.DATE));
        editor.apply();
    }
}
