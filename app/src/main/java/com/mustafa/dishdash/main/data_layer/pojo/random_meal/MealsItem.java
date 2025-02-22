package com.mustafa.dishdash.main.data_layer.pojo.random_meal;

import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MealsItem {
    @NonNull
    private String idMeal;
    private String strMeal;
    private String strArea;
    private String strCategory;
    private String strInstructions;
    private String strMealThumb;
    private String strYoutube;

    //ingredients
    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strIngredient10;
    private String strIngredient11;
    private String strIngredient12;
    private String strIngredient13;
    private String strIngredient14;
    private String strIngredient15;
    private String strIngredient16;
    private String strIngredient17;
    private String strIngredient18;
    private String strIngredient19;
    private String strIngredient20;

    //measurements
    private String strMeasure1;
    private String strMeasure2;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure9;
    private String strMeasure10;
    private String strMeasure11;
    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure14;
    private String strMeasure15;
    private String strMeasure16;
    private String strMeasure17;
    private String strMeasure18;
    private String strMeasure19;
    private String strMeasure20;

    public MealsItem(){}
    public MealsItem(MealsItem meal) {
        this.idMeal = meal.idMeal;
        this.strMeal = meal.strMeal;
        this.strArea = meal.strArea;
        this.strCategory = meal.strCategory;
        this.strInstructions = meal.strInstructions;
        this.strMealThumb = meal.strMealThumb;
        this.strYoutube = meal.strYoutube;

        // Copy ingredients
        this.strIngredient1 = meal.strIngredient1;
        this.strIngredient2 = meal.strIngredient2;
        this.strIngredient3 = meal.strIngredient3;
        this.strIngredient4 = meal.strIngredient4;
        this.strIngredient5 = meal.strIngredient5;
        this.strIngredient6 = meal.strIngredient6;
        this.strIngredient7 = meal.strIngredient7;
        this.strIngredient8 = meal.strIngredient8;
        this.strIngredient9 = meal.strIngredient9;
        this.strIngredient10 = meal.strIngredient10;
        this.strIngredient11 = meal.strIngredient11;
        this.strIngredient12 = meal.strIngredient12;
        this.strIngredient13 = meal.strIngredient13;
        this.strIngredient14 = meal.strIngredient14;
        this.strIngredient15 = meal.strIngredient15;
        this.strIngredient16 = meal.strIngredient16;
        this.strIngredient17 = meal.strIngredient17;
        this.strIngredient18 = meal.strIngredient18;
        this.strIngredient19 = meal.strIngredient19;
        this.strIngredient20 = meal.strIngredient20;

        // Copy measurements
        this.strMeasure1 = meal.strMeasure1;
        this.strMeasure2 = meal.strMeasure2;
        this.strMeasure3 = meal.strMeasure3;
        this.strMeasure4 = meal.strMeasure4;
        this.strMeasure5 = meal.strMeasure5;
        this.strMeasure6 = meal.strMeasure6;
        this.strMeasure7 = meal.strMeasure7;
        this.strMeasure8 = meal.strMeasure8;
        this.strMeasure9 = meal.strMeasure9;
        this.strMeasure10 = meal.strMeasure10;
        this.strMeasure11 = meal.strMeasure11;
        this.strMeasure12 = meal.strMeasure12;
        this.strMeasure13 = meal.strMeasure13;
        this.strMeasure14 = meal.strMeasure14;
        this.strMeasure15 = meal.strMeasure15;
        this.strMeasure16 = meal.strMeasure16;
        this.strMeasure17 = meal.strMeasure17;
        this.strMeasure18 = meal.strMeasure18;
        this.strMeasure19 = meal.strMeasure19;
        this.strMeasure20 = meal.strMeasure20;
    }


    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public String getStrIngredient1() {
        return strIngredient1;
    }

    public void setStrIngredient1(String strIngredient1) {
        this.strIngredient1 = strIngredient1;
    }

    public String getStrIngredient2() {
        return strIngredient2;
    }

    public void setStrIngredient2(String strIngredient2) {
        this.strIngredient2 = strIngredient2;
    }

    public String getStrIngredient3() {
        return strIngredient3;
    }

    public void setStrIngredient3(String strIngredient3) {
        this.strIngredient3 = strIngredient3;
    }

    public String getStrIngredient4() {
        return strIngredient4;
    }

    public void setStrIngredient4(String strIngredient4) {
        this.strIngredient4 = strIngredient4;
    }

    public String getStrIngredient5() {
        return strIngredient5;
    }

    public void setStrIngredient5(String strIngredient5) {
        this.strIngredient5 = strIngredient5;
    }

    public String getStrIngredient6() {
        return strIngredient6;
    }

    public void setStrIngredient6(String strIngredient6) {
        this.strIngredient6 = strIngredient6;
    }

    public String getStrIngredient7() {
        return strIngredient7;
    }

    public void setStrIngredient7(String strIngredient7) {
        this.strIngredient7 = strIngredient7;
    }

    public String getStrIngredient8() {
        return strIngredient8;
    }

    public void setStrIngredient8(String strIngredient8) {
        this.strIngredient8 = strIngredient8;
    }

    public String getStrIngredient9() {
        return strIngredient9;
    }

    public void setStrIngredient9(String strIngredient9) {
        this.strIngredient9 = strIngredient9;
    }

    public String getStrIngredient10() {
        return strIngredient10;
    }

    public void setStrIngredient10(String strIngredient10) {
        this.strIngredient10 = strIngredient10;
    }

    public String getStrIngredient11() {
        return strIngredient11;
    }

    public void setStrIngredient11(String strIngredient11) {
        this.strIngredient11 = strIngredient11;
    }

    public String getStrIngredient12() {
        return strIngredient12;
    }

    public void setStrIngredient12(String strIngredient12) {
        this.strIngredient12 = strIngredient12;
    }

    public String getStrIngredient13() {
        return strIngredient13;
    }

    public void setStrIngredient13(String strIngredient13) {
        this.strIngredient13 = strIngredient13;
    }

    public String getStrIngredient14() {
        return strIngredient14;
    }

    public void setStrIngredient14(String strIngredient14) {
        this.strIngredient14 = strIngredient14;
    }

    public String getStrIngredient15() {
        return strIngredient15;
    }

    public void setStrIngredient15(String strIngredient15) {
        this.strIngredient15 = strIngredient15;
    }

    public String getStrIngredient16() {
        return strIngredient16;
    }

    public void setStrIngredient16(String strIngredient16) {
        this.strIngredient16 = strIngredient16;
    }

    public String getStrIngredient17() {
        return strIngredient17;
    }

    public void setStrIngredient17(String strIngredient17) {
        this.strIngredient17 = strIngredient17;
    }

    public String getStrIngredient18() {
        return strIngredient18;
    }

    public void setStrIngredient18(String strIngredient18) {
        this.strIngredient18 = strIngredient18;
    }

    public String getStrIngredient19() {
        return strIngredient19;
    }

    public void setStrIngredient19(String strIngredient19) {
        this.strIngredient19 = strIngredient19;
    }

    public String getStrIngredient20() {
        return strIngredient20;
    }

    public void setStrIngredient20(String strIngredient20) {
        this.strIngredient20 = strIngredient20;
    }

    public String getStrMeasure1() {
        return strMeasure1;
    }

    public void setStrMeasure1(String strMeasure1) {
        this.strMeasure1 = strMeasure1;
    }

    public String getStrMeasure2() {
        return strMeasure2;
    }

    public void setStrMeasure2(String strMeasure2) {
        this.strMeasure2 = strMeasure2;
    }

    public String getStrMeasure3() {
        return strMeasure3;
    }

    public void setStrMeasure3(String strMeasure3) {
        this.strMeasure3 = strMeasure3;
    }

    public String getStrMeasure4() {
        return strMeasure4;
    }

    public void setStrMeasure4(String strMeasure4) {
        this.strMeasure4 = strMeasure4;
    }

    public String getStrMeasure5() {
        return strMeasure5;
    }

    public void setStrMeasure5(String strMeasure5) {
        this.strMeasure5 = strMeasure5;
    }

    public String getStrMeasure6() {
        return strMeasure6;
    }

    public void setStrMeasure6(String strMeasure6) {
        this.strMeasure6 = strMeasure6;
    }

    public String getStrMeasure7() {
        return strMeasure7;
    }

    public void setStrMeasure7(String strMeasure7) {
        this.strMeasure7 = strMeasure7;
    }

    public String getStrMeasure8() {
        return strMeasure8;
    }

    public void setStrMeasure8(String strMeasure8) {
        this.strMeasure8 = strMeasure8;
    }

    public String getStrMeasure9() {
        return strMeasure9;
    }

    public void setStrMeasure9(String strMeasure9) {
        this.strMeasure9 = strMeasure9;
    }

    public String getStrMeasure10() {
        return strMeasure10;
    }

    public void setStrMeasure10(String strMeasure10) {
        this.strMeasure10 = strMeasure10;
    }

    public String getStrMeasure11() {
        return strMeasure11;
    }

    public void setStrMeasure11(String strMeasure11) {
        this.strMeasure11 = strMeasure11;
    }

    public String getStrMeasure12() {
        return strMeasure12;
    }

    public void setStrMeasure12(String strMeasure12) {
        this.strMeasure12 = strMeasure12;
    }

    public String getStrMeasure13() {
        return strMeasure13;
    }

    public void setStrMeasure13(String strMeasure13) {
        this.strMeasure13 = strMeasure13;
    }

    public String getStrMeasure14() {
        return strMeasure14;
    }

    public void setStrMeasure14(String strMeasure14) {
        this.strMeasure14 = strMeasure14;
    }

    public String getStrMeasure15() {
        return strMeasure15;
    }

    public void setStrMeasure15(String strMeasure15) {
        this.strMeasure15 = strMeasure15;
    }

    public String getStrMeasure16() {
        return strMeasure16;
    }

    public void setStrMeasure16(String strMeasure16) {
        this.strMeasure16 = strMeasure16;
    }

    public String getStrMeasure17() {
        return strMeasure17;
    }

    public void setStrMeasure17(String strMeasure17) {
        this.strMeasure17 = strMeasure17;
    }

    public String getStrMeasure18() {
        return strMeasure18;
    }

    public void setStrMeasure18(String strMeasure18) {
        this.strMeasure18 = strMeasure18;
    }

    public String getStrMeasure19() {
        return strMeasure19;
    }

    public void setStrMeasure19(String strMeasure19) {
        this.strMeasure19 = strMeasure19;
    }

    public String getStrMeasure20() {
        return strMeasure20;
    }

    public void setStrMeasure20(String strMeasure20) {
        this.strMeasure20 = strMeasure20;
    }

    @Override
    public String toString() {
        return "MealsItem{" +
                "idMeal='" + idMeal + '\'' +
                ", strMeal='" + strMeal + '\'' +
                ", strArea='" + strArea + '\'' +
                ", strCategory='" + strCategory + '\'' +
                ", strInstructions='" + strInstructions + '\'' +
                ", strMealThumb='" + strMealThumb + '\'' +
                ", strYoutube='" + strYoutube + '\'' +
                ", strIngredient1='" + strIngredient1 + '\'' +
                ", strIngredient2='" + strIngredient2 + '\'' +
                ", strIngredient3='" + strIngredient3 + '\'' +
                ", strIngredient10='" + strIngredient10 + '\'' +
                ", strIngredient11='" + strIngredient11 + '\'' +
                ", strIngredient12='" + strIngredient12 + '\'' +
                ", strIngredient13='" + strIngredient13 + '\'' +
                ", strIngredient14='" + strIngredient14 + '\'' +
                ", strIngredient15='" + strIngredient15 + '\'' +
                ", strIngredient16='" + strIngredient16 + '\'' +
                ", strIngredient17='" + strIngredient17 + '\'' +
                ", strIngredient18='" + strIngredient18 + '\'' +
                ", strIngredient19='" + strIngredient19 + '\'' +
                ", strIngredient20='" + strIngredient20 + '\'' +
                ", strIngredient4='" + strIngredient4 + '\'' +
                ", strIngredient5='" + strIngredient5 + '\'' +
                ", strIngredient6='" + strIngredient6 + '\'' +
                ", strIngredient7='" + strIngredient7 + '\'' +
                ", strIngredient8='" + strIngredient8 + '\'' +
                ", strIngredient9='" + strIngredient9 + '\'' +
                ", strMeasure12='" + strMeasure12 + '\'' +
                ", strMeasure13='" + strMeasure13 + '\'' +
                ", strMeasure10='" + strMeasure10 + '\'' +
                ", strMeasure11='" + strMeasure11 + '\'' +
                ", strMeasure20='" + strMeasure20 + '\'' +
                ", strMeasure9='" + strMeasure9 + '\'' +
                ", strMeasure7='" + strMeasure7 + '\'' +
                ", strMeasure8='" + strMeasure8 + '\'' +
                ", strMeasure5='" + strMeasure5 + '\'' +
                ", strMeasure6='" + strMeasure6 + '\'' +
                ", strMeasure3='" + strMeasure3 + '\'' +
                ", strMeasure4='" + strMeasure4 + '\'' +
                ", strMeasure1='" + strMeasure1 + '\'' +
                ", strMeasure18='" + strMeasure18 + '\'' +
                ", strMeasure2='" + strMeasure2 + '\'' +
                ", strMeasure19='" + strMeasure19 + '\'' +
                ", strMeasure16='" + strMeasure16 + '\'' +
                ", strMeasure17='" + strMeasure17 + '\'' +
                ", strMeasure14='" + strMeasure14 + '\'' +
                ", strMeasure15='" + strMeasure15 + '\'' +
                '}';
    }

    public List<Pair<String, String>> getingredients() {
        List<Pair<String, String>> ingredients = new ArrayList<>();
        addIngredient(ingredients, strIngredient1, strMeasure1);
        addIngredient(ingredients, strIngredient2, strMeasure2);
        addIngredient(ingredients, strIngredient3, strMeasure3);
        addIngredient(ingredients, strIngredient4, strMeasure4);
        addIngredient(ingredients, strIngredient5, strMeasure5);
        addIngredient(ingredients, strIngredient6, strMeasure6);
        addIngredient(ingredients, strIngredient7, strMeasure7);
        addIngredient(ingredients, strIngredient8, strMeasure8);
        addIngredient(ingredients, strIngredient9, strMeasure9);
        addIngredient(ingredients, strIngredient10, strMeasure10);

        addIngredient(ingredients, strIngredient11, strMeasure12);
        addIngredient(ingredients, strIngredient12, strMeasure12);
        addIngredient(ingredients, strIngredient13, strMeasure13);
        addIngredient(ingredients, strIngredient14, strMeasure14);
        addIngredient(ingredients, strIngredient15, strMeasure15);
        addIngredient(ingredients, strIngredient16, strMeasure16);
        addIngredient(ingredients, strIngredient17, strMeasure17);
        addIngredient(ingredients, strIngredient18, strMeasure18);
        addIngredient(ingredients, strIngredient19, strMeasure19);
        addIngredient(ingredients, strIngredient20, strMeasure20);

        return ingredients;
    }

    private void addIngredient(List<Pair<String, String>> ingredients, String ingredientTitle, String ingredientMeasurement) {
        if (ingredientTitle != null && !ingredientTitle.trim().isEmpty()) {
            ingredients.add(new Pair<>(ingredientTitle, ingredientMeasurement));
        }
    }
}
