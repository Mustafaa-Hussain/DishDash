package com.mustafa.dishdash.main.profile.presenter;

import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.profile.view.ProfileView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProfilePresenter {
    private MealsRepository mealsRepository;
    private AuthRepository authRepository;
    private ProfileView view;
    private CompositeDisposable compositeDisposable;

    public ProfilePresenter(MealsRepository mealsRepository
            , AuthRepository authRepository
            , ProfileView view) {
        this.mealsRepository = mealsRepository;
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
                mealsRepository
                        .clearFavorites()
                        .subscribe());

        compositeDisposable.add(
                mealsRepository
                        .clearFuturePlanes()
                        .subscribe());
    }

    public boolean isAuthenticated() {
        return authRepository.isAuthenticated();
    }

    public String getUsername() {
        return authRepository.getCurrentAuthenticatedUsername();
    }

    public String getEmail() {
        return authRepository.getCurrentAuthenticatedUserEmail();
    }

    public void close() {
        compositeDisposable.clear();
    }
}
