package com.mustafa.dishdash.adabters;

import android.view.View;

import com.mustafa.dishdash.retrofit.models.meals_short_details.MealShortDetails;

public interface MealClickListener {
    void onClick(View view, MealShortDetails meal);
}
