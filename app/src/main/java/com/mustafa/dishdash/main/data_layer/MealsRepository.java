package com.mustafa.dishdash.main.data_layer;

import com.mustafa.dishdash.main.data_layer.network.GetAllMealNetworkCallBack;
import com.mustafa.dishdash.main.data_layer.network.GetMealByIdNetworkCallBack;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.network.GetRandomMealNetworkCallBack;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;

public class MealsRepository {
    private static MealsRepository instance;
    private MealsRemoteDatasource mealsRemoteDatasource;
    TodayMealLocalDatasource todayMealLocalDatasource;

    private MealsRepository(MealsRemoteDatasource mealsRemoteDatasource,
                            TodayMealLocalDatasource todayMealLocalDatasource) {
        this.mealsRemoteDatasource = mealsRemoteDatasource;
        this.todayMealLocalDatasource = todayMealLocalDatasource;
    }

    public static MealsRepository getInstance(MealsRemoteDatasource mealsRemoteDatasource,
                                              TodayMealLocalDatasource todayMealLocalDatasource) {
        if (instance == null) {
            instance = new MealsRepository(mealsRemoteDatasource, todayMealLocalDatasource);
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
}
