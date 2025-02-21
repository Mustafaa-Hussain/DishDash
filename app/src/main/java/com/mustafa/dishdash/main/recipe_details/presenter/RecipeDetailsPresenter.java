package com.mustafa.dishdash.main.recipe_details.presenter;

import static com.mustafa.dishdash.utils.Constants.ingredientImageUrl;

import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.network.GetMealByIdNetworkCallBack;
import com.mustafa.dishdash.main.data_layer.network.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.recipe_details.view.RecipeDetailsView;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsPresenter implements GetMealByIdNetworkCallBack {
    RecipeDetailsView view;
    MealsRepository repository;

    public RecipeDetailsPresenter(RecipeDetailsView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void getMealById(String id) {
        repository.getMealById(this, id);
    }


    @Override
    public void onGetMealByIdCallSuccess(MealsItem mealItem) {
        List<Pair<String, String>> ingredients = new ArrayList<>();
        addIngredientToList(ingredients, mealItem);
        view.recipeDetailsResponseSuccess(mealItem, ingredients);
    }

    private void addIngredientToList(List<Pair<String, String>> ingredients, MealsItem mealItem) {

        addIngredient(ingredients, mealItem.getStrIngredient1(), mealItem.getStrMeasure1());
        addIngredient(ingredients, mealItem.getStrIngredient2(), mealItem.getStrMeasure2());
        addIngredient(ingredients, mealItem.getStrIngredient3(), mealItem.getStrMeasure3());
        addIngredient(ingredients, mealItem.getStrIngredient4(), mealItem.getStrMeasure4());
        addIngredient(ingredients, mealItem.getStrIngredient5(), mealItem.getStrMeasure5());
        addIngredient(ingredients, mealItem.getStrIngredient6(), mealItem.getStrMeasure6());
        addIngredient(ingredients, mealItem.getStrIngredient7(), mealItem.getStrMeasure7());
        addIngredient(ingredients, mealItem.getStrIngredient8(), mealItem.getStrMeasure8());
        addIngredient(ingredients, mealItem.getStrIngredient9(), mealItem.getStrMeasure9());
        addIngredient(ingredients, mealItem.getStrIngredient10(), mealItem.getStrMeasure10());

        addIngredient(ingredients, mealItem.getStrIngredient11(), mealItem.getStrMeasure12());
        addIngredient(ingredients, mealItem.getStrIngredient12(), mealItem.getStrMeasure12());
        addIngredient(ingredients, mealItem.getStrIngredient13(), mealItem.getStrMeasure13());
        addIngredient(ingredients, mealItem.getStrIngredient14(), mealItem.getStrMeasure14());
        addIngredient(ingredients, mealItem.getStrIngredient15(), mealItem.getStrMeasure15());
        addIngredient(ingredients, mealItem.getStrIngredient16(), mealItem.getStrMeasure16());
        addIngredient(ingredients, mealItem.getStrIngredient17(), mealItem.getStrMeasure17());
        addIngredient(ingredients, mealItem.getStrIngredient18(), mealItem.getStrMeasure18());
        addIngredient(ingredients, mealItem.getStrIngredient19(), mealItem.getStrMeasure19());
        addIngredient(ingredients, mealItem.getStrIngredient20(), mealItem.getStrMeasure20());
    }

    private void addIngredient(List<Pair<String, String>> ingredients, String ingredientTitle, String ingredientMeasurement) {
        if (ingredientTitle != null && !ingredientTitle.trim().isEmpty()) {
            ingredients.add(new Pair<>(ingredientTitle, ingredientMeasurement));
        }
    }

    @Override
    public void onGetMealByIdCallFail(String errorMessage) {
        view.recipeDetailsResponseFail(errorMessage);
    }
}
