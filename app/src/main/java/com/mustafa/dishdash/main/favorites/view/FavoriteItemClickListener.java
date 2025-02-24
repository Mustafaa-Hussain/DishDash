package com.mustafa.dishdash.main.favorites.view;

import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

public interface FavoriteItemClickListener {
    void toggleFavoriteClickListener(MealsItem meal);

    void itemClickListener(MealsItem meal);
}
