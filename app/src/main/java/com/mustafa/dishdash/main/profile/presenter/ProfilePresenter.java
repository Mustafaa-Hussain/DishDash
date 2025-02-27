package com.mustafa.dishdash.main.profile.presenter;

import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.FuturePlanesRepository;
import com.mustafa.dishdash.main.profile.view.ProfileView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

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

    public void logoutUser() {
        authRepository.logoutUser();
        clearLocalData();
    }

    private void clearLocalData() {
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

    public void close() {
        compositeDisposable.clear();
    }
}
