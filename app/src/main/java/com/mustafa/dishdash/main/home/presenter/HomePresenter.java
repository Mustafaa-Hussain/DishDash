package com.mustafa.dishdash.main.home.presenter;

import android.annotation.SuppressLint;

import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.home.view.HomeView;

import java.util.Calendar;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class HomePresenter {

    private HomeView view;
    private MealsRepository mealsRepository;
    private AuthRepository authRepository;
    private CompositeDisposable compositeDisposable;

    public HomePresenter(HomeView view
            , MealsRepository repository
            , AuthRepository authRepository) {
        this.view = view;
        this.mealsRepository = repository;
        this.authRepository = authRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    public void getRandomMeal() {
        String id = mealsRepository.getSavedMealId();
        int day = mealsRepository.getDateOfSavedMeal();

        if (id.isEmpty() || day != Calendar.getInstance().get(Calendar.DATE)) {
            compositeDisposable.add(
                    mealsRepository
                            .getRandomMeal()
                            .subscribe(mealsList -> {
                                        view.randomMealResultSuccess(mealsList.getMeals().get(0));
                                        mealsRepository.saveTodayMealId(mealsList.getMeals().get(0).getIdMeal());
                                    },
                                    error -> view.randomMealResultFail(error.getMessage())));
        } else {
            compositeDisposable.add(
                    mealsRepository
                            .getMealById(id)
                            .subscribe(mealsItemBooleanPair -> view.mealByIdResultSuccess(mealsItemBooleanPair.first),
                                    error -> view.mealByIdResultFail(error.getMessage())));
        }
    }

    @SuppressLint("CheckResult")
    public void getAllMeals() {
        compositeDisposable.add(
                mealsRepository
                        .getAllMeals()
                        .subscribe(mealsList -> view.allMealsResultSuccess(mealsList),
                                error -> view.allMealsResultFail(error.getMessage())));
    }

    public void getRegisteredUser() {
        if (authRepository.isAuthenticated()) {
            view.registeredUser(authRepository.getCurrentAuthenticatedUsername());
        } else {
            view.userNotLoggedIn();
        }
    }

    public void close() {
        compositeDisposable.clear();
    }

}
