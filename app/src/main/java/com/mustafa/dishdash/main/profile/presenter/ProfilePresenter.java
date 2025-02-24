package com.mustafa.dishdash.main.profile.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.GetRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.UploadRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.profile.view.ProfileView;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenter implements GetRemoteFavoriteMealsCallBack, UploadRemoteFavoriteMealsCallBack {
    private MealsRepository mealsRepository;
    private FavoriteMealsRepository favoriteMealsRepository;
    private ProfileView view;

    public ProfilePresenter(MealsRepository mealsRepository
            , FavoriteMealsRepository favoriteMealsRepository
            , ProfileView view) {
        this.mealsRepository = mealsRepository;
        this.favoriteMealsRepository = favoriteMealsRepository;
        this.view = view;
    }

    public void syncUserData() {
        //download data from remote
        //insert retrieved data into database
        favoriteMealsRepository.getAllFavoriteMeals(this);
    }

    public void clearFavorites() {
        favoriteMealsRepository
                .clearFavorites()
                .subscribe();
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAllFavoriteMealsRemoteOnSuccess(List<String> favoriteMeals) {

        //insert retrieved data into database
        Observable
                .fromIterable(favoriteMeals)
                .subscribeOn(Schedulers.io())
                .flatMapSingle(mealId -> mealsRepository.getMealById(mealId))
                .flatMapCompletable(mealsItemBooleanPair ->
                        favoriteMealsRepository.insertFavoriteMeal(mealsItemBooleanPair.first))
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        favoriteMealsRepository
                                .getFavoriteMeals()
                                .doOnNext(mealsItems -> Log.i("TAG", "1: onNext: " + mealsItems))
                                .flatMap(mealsItems ->
                                        Flowable.fromIterable(mealsItems)
                                                .map(mealsItem -> mealsItem.getIdMeal())
                                                .collect(Collectors.toList()).toFlowable())
                                .subscribe(mealsId -> {
                                    Log.i("TAG", "3: onSuccess: " + mealsId);
                                    favoriteMealsRepository
                                            .uploadFavoriteMeals(ProfilePresenter.this, mealsId);
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    @Override
    public void getAllFavoriteMealsRemoteOnFail(String errorMsg) {
        view.syncDataFailed(errorMsg);
    }

    @Override
    public void onUploadFavoriteMealsRemoteOnSuccess() {
        view.syncDataSuccessfully();
    }

    @Override
    public void onUploadFavoriteMealsRemoteOnFail(String errorMsg) {
        view.syncDataFailed(errorMsg);
    }
}
