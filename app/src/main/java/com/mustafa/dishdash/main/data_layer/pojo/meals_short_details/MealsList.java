package com.mustafa.dishdash.main.data_layer.pojo.meals_short_details;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MealsList {

	@SerializedName("meals")
	private List<MealShortDetails> meals;

	public List<MealShortDetails> getMeals(){
		return meals;
	}
}