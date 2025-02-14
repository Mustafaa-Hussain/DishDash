package com.mustafa.dishdash.retrofit.models.meals_short_details;

import com.google.gson.annotations.SerializedName;

public class MealShortDetails {

	@SerializedName("strMealThumb")
	private String strMealThumb;

	@SerializedName("idMeal")
	private String idMeal;

	@SerializedName("strMeal")
	private String strMeal;

	public String getStrMealThumb(){
		return strMealThumb;
	}

	public String getIdMeal(){
		return idMeal;
	}

	public String getStrMeal(){
		return strMeal;
	}
}