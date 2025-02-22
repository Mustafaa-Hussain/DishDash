package com.mustafa.dishdash.main.data_layer;

import androidx.lifecycle.LiveData;

import com.mustafa.dishdash.main.data_layer.db.FavoritesMealsDB;
import com.mustafa.dishdash.main.data_layer.db.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.entities.FavoriteMeal;
import com.mustafa.dishdash.main.data_layer.network.GetAllMealNetworkCallBack;
import com.mustafa.dishdash.main.data_layer.network.GetMealByIdNetworkCallBack;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.network.GetRandomMealNetworkCallBack;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;

import java.util.List;

public class MealsRepository {
    private static MealsRepository instance;
    private MealsRemoteDatasource mealsRemoteDatasource;
    private TodayMealLocalDatasource todayMealLocalDatasource;
    private FavoritesMealsLocalDatasource favoritesMealsDatasource;

    private MealsRepository(MealsRemoteDatasource mealsRemoteDatasource,
                            TodayMealLocalDatasource todayMealLocalDatasource,
                            FavoritesMealsLocalDatasource favoritesMealsDatasource) {
        this.mealsRemoteDatasource = mealsRemoteDatasource;
        this.todayMealLocalDatasource = todayMealLocalDatasource;
        this.favoritesMealsDatasource = favoritesMealsDatasource;
    }

    public static MealsRepository getInstance(MealsRemoteDatasource mealsRemoteDatasource,
                                              TodayMealLocalDatasource todayMealLocalDatasource,
                                              FavoritesMealsLocalDatasource favoritesMealsDatasource) {
        if (instance == null) {
            instance = new MealsRepository(mealsRemoteDatasource,
                    todayMealLocalDatasource,
                    favoritesMealsDatasource);
        }
        return instance;
    }

    public void getMealById(GetMealByIdNetworkCallBack getMealByIdNetworkCallBack, String id) {
        mealsRemoteDatasource.getMealById(getMealByIdNetworkCallBack, id);
    }

    public void getRandomMeal(GetRandomMealNetworkCallBack randomMealNetworkCallBack) {
        mealsRemoteDatasource.getRandomMeal(randomMealNetworkCallBack);
    }

    public void getAllMeals(GetAllMealNetworkCallBack getAllMealNetworkCallBack) {
        mealsRemoteDatasource.getAllMeals(getAllMealNetworkCallBack);
    }

    public int getSavedMealId() {
        return todayMealLocalDatasource.getSavedMealId();
    }

    public int getDateOfSavedMeal() {
        return todayMealLocalDatasource.getDateOfSavedMeal();
    }

    public void saveTodayMealId(int id) {
        todayMealLocalDatasource.saveTodayMealId(id);
    }

    public LiveData<List<FavoriteMeal>> getFavoriteMealList(String userEmail) {
        return favoritesMealsDatasource.getFavoritesMeals(userEmail);
    }

    public void insertFavoriteMeal(FavoriteMeal favoritesMeal) {
        favoritesMealsDatasource.insetFavoriteMeal(favoritesMeal);
    }

    public void removeFavoriteMeal(FavoriteMeal favoritesMeal) {
        favoritesMealsDatasource.deleteFavoriteMeal(favoritesMeal);
    }

    public LiveData<FavoriteMeal> getFavoriteMealById(String userEmail, String mealId) {
        return favoritesMealsDatasource.getFavoriteMealById(userEmail, mealId);
    }

}
