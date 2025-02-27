package com.mustafa.dishdash.main.profile.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.FuturePlanesRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.GetRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.UploadRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.profile.view.ProfileView;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenter {
    private FavoriteMealsRepository favoriteMealsRepository;
    private FuturePlanesRepository futurePlanesRepository;
    private AuthRepository authRepository;
    private ProfileView view;
    private CompositeDisposable compositeDisposable;

    public ProfilePresenter(FuturePlanesRepository futurePlanesRepository
            , FavoriteMealsRepository favoriteMealsRepository
            , AuthRepository authRepository
            , ProfileView view) {
        this.favoriteMealsRepository = favoriteMealsRepository;
        this.futurePlanesRepository = futurePlanesRepository;
        this.authRepository = authRepository;
        this.view = view;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void clearFavorites() {
        compositeDisposable.add(
                favoriteMealsRepository
                        .clearFavorites()
                        .subscribe());

        compositeDisposable.add(
                futurePlanesRepository
                        .clearFuturePlanes()
                        .subscribe());
    }

    public boolean isAuthenticated() {
        return authRepository.isAuthenticated();
    }

    public void logoutUser() {
        authRepository.logoutUser();
    }

    public void close() {
        compositeDisposable.clear();
    }
}
