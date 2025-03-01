package com.mustafa.dishdash.main.search.data_layer.models.countries;

import java.util.List;

public class CountriesResponse{
	private List<MealsItem> meals;

	public List<MealsItem> getMeals(){
		return meals;
	}
}