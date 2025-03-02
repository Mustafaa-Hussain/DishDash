package com.mustafa.dishdash.main.search.presenter;

import static com.mustafa.dishdash.utils.Constants.INGREDIENT_IMAGE_URL;
import static com.mustafa.dishdash.utils.Constants.SMALL_IMAGE_EXTENSION;

import android.annotation.SuppressLint;

import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.search.view.SearchFragmentView;
import com.mustafa.dishdash.main.search.view.adapter.SearchItem;
import com.mustafa.dishdash.utils.CountryNameConverter;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {
    private SearchFragmentView view;
    private MealsRepository repository;

    public SearchPresenter(SearchFragmentView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @SuppressLint("CheckResult")
    public void getCategories() {
        repository.getCategories()
                .flatMapObservable(categoryResponse ->
                        Observable.fromIterable(categoryResponse.getCategories()))
                .map(categoriesItem ->
                        new SearchItem(categoriesItem.getIdCategory(),
                                categoriesItem.getStrCategory(),
                                categoriesItem.getStrCategoryThumb()))
                .collect(Collectors.toList())
                .subscribe(searchItemList -> view.onGetCategoriesSuccess(searchItemList),
                        error -> view.onGetCategoriesFail(error.getMessage()));
    }


    public Observable<List<SearchItem>> filterByMealName(String query) {
        return repository.filterByMealName(query)
                .subscribeOn(Schedulers.io())
                .flatMapObservable(filterByNameResponse ->
                        Observable
                                .fromIterable(filterByNameResponse.getMeals())
                                .map(mealsItem -> new SearchItem(mealsItem.getIdMeal(),
                                        mealsItem.getStrMeal(),
                                        mealsItem.getStrMealThumb())))
                .toList()
                .toObservable();
    }

    @SuppressLint("CheckResult")
    public void getIngredients() {
        repository.getIngredients()
                .flatMapObservable(ingredientsResponse ->
                        Observable.fromIterable(ingredientsResponse.getMeals()))
                .map(ingredientItem ->
                        new SearchItem(ingredientItem.getIdIngredient(),
                                ingredientItem.getStrIngredient(),
                                INGREDIENT_IMAGE_URL +
                                        ingredientItem.getStrIngredient()
                                        + SMALL_IMAGE_EXTENSION))
                .collect(Collectors.toList())
                .subscribe(searchItemList -> view.onGetCategoriesSuccess(searchItemList),
                        error -> view.onGetCategoriesFail(error.getMessage()));
    }

    private Single<Integer> isMainIngredient(String strIngredient) {
        return repository
                .getCountMealsOfMainIngredientByMainIngredient(strIngredient);
    }

    @SuppressLint("CheckResult")
    public void getCountries() {
        repository.getCountries()
                .flatMapObservable(countriesResponse ->
                        Observable.fromIterable(countriesResponse.getMeals()))
                .map(mealsItem -> new SearchItem("",
                        mealsItem.getStrArea(),
                        CountryNameConverter.getCountryThumbnail(mealsItem.getStrArea())))
                .collect(Collectors.toList())
                .subscribe(searchItemList -> view.onGetCategoriesSuccess(searchItemList),
                        error -> view.onGetCategoriesFail(error.getMessage()));

    }
}
