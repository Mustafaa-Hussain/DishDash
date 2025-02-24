package com.mustafa.dishdash.main.data_layer;

import android.annotation.SuppressLint;
import android.util.Pair;

import com.mustafa.dishdash.main.data_layer.db.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.pojo.meals_short_details.MealsList;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.Meal;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepository {
    private static MealsRepository instance;
    private MealsRemoteDatasource mealsRemoteDatasource;
    private TodayMealLocalDatasource todayMealLocalDatasource;
    private FavoritesMealsLocalDatasource favoritesMealsLocalDatasource;

    private MealsRepository(MealsRemoteDatasource mealsRemoteDatasource,
                            TodayMealLocalDatasource todayMealLocalDatasource,
                            FavoritesMealsLocalDatasource favoritesMealsDatasource) {
        this.mealsRemoteDatasource = mealsRemoteDatasource;
        this.todayMealLocalDatasource = todayMealLocalDatasource;
        this.favoritesMealsLocalDatasource = favoritesMealsDatasource;
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

    public Single<Pair<MealsItem, Boolean>> getMealById(String id) {
        return Single.zip(getMeal(id),
                        favoritesMealsLocalDatasource.isFavoriteMeal(id),
                        (meal, count) -> new Pair<>(meal.getMeals().get(0), count == 1)
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    private Single<Meal> getMeal(String id) {
        return mealsRemoteDatasource
                .getMealById(id)
                .onErrorResumeWith(favoritesMealsLocalDatasource
                        .getFavoriteMealById(id)
                        .map(Meal::new))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Meal> getRandomMeal() {
        return mealsRemoteDatasource
                .getRandomMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MealsList> getAllMeals() {
        return mealsRemoteDatasource
                .getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public String getSavedMealId() {
        return todayMealLocalDatasource.getSavedMealId();
    }

    public int getDateOfSavedMeal() {
        return todayMealLocalDatasource.getDateOfSavedMeal();
    }

    public void saveTodayMealId(String id) {
        todayMealLocalDatasource.saveTodayMealId(id);
    }
}
