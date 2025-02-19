package com.mustafa.dishdash.main.home.view.adabters;

import android.view.View;

import com.mustafa.dishdash.main.home.data_layer.network.models.meals_short_details.MealShortDetails;

public interface MealClickListener {
    void onItemClick(String id);
    void onClickAddToFavorites(String id);
    void onClickAddToCalender(String id);
}
