package com.mustafa.dishdash.main.data_layer;

import android.annotation.SuppressLint;
import android.util.Pair;

import com.mustafa.dishdash.main.data_layer.db.favorites.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.future_planes.FuturePlanesLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.FavoritesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.GetRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.UploadRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.FuturePlanesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.GetRemoteFuturePlanesCallBack;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.UploadFuturePlanesCallBack;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.entities.FuturePlaneEntity;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.pojo.meals_short_details.MealsList;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.Meal;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.data_layer.pojo.search.MealsResponse;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;
import com.mustafa.dishdash.main.data_layer.pojo.search.categories.CategoryResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.countries.CountriesResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.filter_by_name.FilterByNameResponse;
import com.mustafa.dishdash.main.data_layer.pojo.search.ingredients.IngredientsResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepository {
    private static MealsRepository instance;
    private MealsRemoteDatasource mealsRemoteDatasource;
    private TodayMealLocalDatasource todayMealLocalDatasource;
    private FavoritesMealsLocalDatasource favoritesMealsLocalDatasource;
    private FavoritesRemoteDatasource favoritesRemoteDatasource;
    private FuturePlanesLocalDatasource futurePlanesLocalDatasource;
    private FuturePlanesRemoteDatasource futurePlanesRemoteDatasource;

    public MealsRepository(MealsRemoteDatasource mealsRemoteDatasource,
                           TodayMealLocalDatasource todayMealLocalDatasource,
                           FavoritesMealsLocalDatasource favoritesMealsLocalDatasource,
                           FavoritesRemoteDatasource favoritesRemoteDatasource,
                           FuturePlanesRemoteDatasource futurePlanesRemoteDatasource,
                           FuturePlanesLocalDatasource futurePlanesLocalDatasource) {

        this.mealsRemoteDatasource = mealsRemoteDatasource;
        this.todayMealLocalDatasource = todayMealLocalDatasource;
        this.favoritesMealsLocalDatasource = favoritesMealsLocalDatasource;
        this.favoritesRemoteDatasource = favoritesRemoteDatasource;
        this.futurePlanesLocalDatasource = futurePlanesLocalDatasource;
        this.futurePlanesRemoteDatasource = futurePlanesRemoteDatasource;
    }

    public static MealsRepository getInstance(MealsRemoteDatasource mealsRemoteDatasource,
                                              TodayMealLocalDatasource todayMealLocalDatasource,
                                              FavoritesMealsLocalDatasource favoritesMealsLocalDatasource,
                                              FavoritesRemoteDatasource favoritesRemoteDatasource,
                                              FuturePlanesRemoteDatasource futurePlanesRemoteDatasource,
                                              FuturePlanesLocalDatasource futurePlanesLocalDatasource) {
        if (instance == null) {
            instance = new MealsRepository(mealsRemoteDatasource,
                    todayMealLocalDatasource,
                    favoritesMealsLocalDatasource,
                    favoritesRemoteDatasource,
                    futurePlanesRemoteDatasource,
                    futurePlanesLocalDatasource);
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
                .onErrorResumeWith(futurePlanesLocalDatasource
                        .getFuturePlaneByMealId(id)
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


    public Flowable<List<FuturePlane>> getAllFuturePlanes() {
        return futurePlanesLocalDatasource
                .getAllFuturePlanes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<FuturePlane> getFuturePlaneByMealId(String mealId) {
        return futurePlanesLocalDatasource
                .getFuturePlaneByMealId(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable insertFuturePlane(FuturePlane futurePlane) {
        return futurePlanesLocalDatasource
                .insertFuturePlane(futurePlane)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteFuturePlane(FuturePlane futurePlane) {
        return futurePlanesLocalDatasource
                .deleteFuturePlane(futurePlane)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable clearFuturePlanes() {
        return futurePlanesLocalDatasource
                .clearFuturePlanes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getAllFuturePlanes(GetRemoteFuturePlanesCallBack callBack) {
        futurePlanesRemoteDatasource.getFuturePlanes(callBack);
    }

    public void uploadFuturePlanes(UploadFuturePlanesCallBack callBack, List<FuturePlaneEntity> futurePlanes) {
        futurePlanesRemoteDatasource.uploadFuturePlanes(callBack, futurePlanes);
    }


    public Flowable<List<MealsItem>> getFavoriteMeals() {
        return favoritesMealsLocalDatasource
                .getFavoritesMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable insertFavoriteMeal(MealsItem meal) {
        return favoritesMealsLocalDatasource
                .insetFavoriteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Completable removeFavoriteMeal(MealsItem meal) {
        return favoritesMealsLocalDatasource
                .deleteFavoriteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable clearFavorites() {
        return favoritesMealsLocalDatasource
                .clearFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getAllFavoriteMeals(GetRemoteFavoriteMealsCallBack callBack) {
        favoritesRemoteDatasource.getFavoriteMeals(callBack);
    }


    public void uploadFavoriteMeals(UploadRemoteFavoriteMealsCallBack callBack, List<String> mealsIds) {
        favoritesRemoteDatasource.uploadFavoriteMeals(callBack, mealsIds);
    }


    public Single<CategoryResponse> getCategories() {
        return mealsRemoteDatasource
                .getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<IngredientsResponse> getIngredients() {
        return mealsRemoteDatasource
                .getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<CountriesResponse> getCountries() {
        return mealsRemoteDatasource
                .getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<FilterByNameResponse> filterByMealName(String query) {
        return mealsRemoteDatasource
                .filterByMealName(query);
    }

    public Single<Integer> getCountMealsOfMainIngredientByMainIngredient(String strIngredient) {
        return mealsRemoteDatasource
                .getMealsByIngredient(strIngredient)
                .subscribeOn(Schedulers.io())
                .map(mealsResponse -> mealsResponse.getMeals().size())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MealsResponse> getMealsByCategory(String category) {
        return mealsRemoteDatasource
                .getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MealsResponse> getMealsByCountry(String country) {
        return mealsRemoteDatasource
                .getMealsByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MealsResponse> getMealsByIngredient(String ingredient) {
        return mealsRemoteDatasource
                .getMealsByIngredient(ingredient)
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
