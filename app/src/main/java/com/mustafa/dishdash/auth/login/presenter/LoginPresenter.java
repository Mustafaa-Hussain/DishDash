package com.mustafa.dishdash.auth.login.presenter;

import static com.mustafa.dishdash.auth.login.view.LoginView.FILL_EMAIL;
import static com.mustafa.dishdash.auth.login.view.LoginView.FILL_PASSWORD;
import static com.mustafa.dishdash.auth.login.view.LoginView.INVALID_EMAIL;
import static com.mustafa.dishdash.auth.login.view.LoginView.PASSWORD_LENGTH;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.auth.data_layer.firebase.AuthNetworkCallback;
import com.mustafa.dishdash.auth.login.view.LoginView;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.GetRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.UploadRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.utils.EmailValidation;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter implements AuthNetworkCallback, GetRemoteFavoriteMealsCallBack, UploadRemoteFavoriteMealsCallBack {
    private final MealsRepository mealRepository;
    private AuthRepository authRepository;
    private FavoriteMealsRepository favoriteMealsRepository;
    private CompositeDisposable compositeDisposable;
    private LoginView view;

    public LoginPresenter(
            AuthRepository repository
            , FavoriteMealsRepository favoriteMealsRepository
            , MealsRepository mealsRepository
            , LoginView view) {
        this.authRepository = repository;
        this.favoriteMealsRepository = favoriteMealsRepository;
        this.mealRepository = mealsRepository;
        this.view = view;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void authenticateUser(String email, String password) {

        if (isAuthenticated()) {
            view.onAuthenticationSuccess(getCurrentAuthenticatedUsername());
            return;
        }

        boolean error = false;
        if (email == null || email.isEmpty()) {
            view.emailError(FILL_EMAIL);
            error = true;
        }

        if (password != null && password.length() < 8) {
            view.passwordError(PASSWORD_LENGTH);
            error = true;
        }

        if (password == null || password.isEmpty()) {
            view.passwordError(FILL_PASSWORD);
            error = true;
        }

        if (error) {
            return;
        }

        if (EmailValidation.isValidEmail(email)) {
            view.showProgressbar();
            authRepository.authenticateUser(this, email, password);
        } else {
            view.emailError(INVALID_EMAIL);
        }
    }

    public void authenticateUserWithToken(Task<GoogleSignInAccount> task) {
        view.showProgressbar();
        authRepository.authenticateUser(this, task);
    }

    public boolean isAuthenticated() {
        return authRepository.isAuthenticated();
    }

    public String getCurrentAuthenticatedUsername() {
        return authRepository.getCurrentAuthenticatedUsername();
    }

    @Override
    public void onAuthSuccess(FirebaseUser user) {
        view.hideProgressbar();
        view.onAuthenticationSuccess(user.getDisplayName());
    }

    @Override
    public void onAuthFailed(String errorMsg) {
        view.hideProgressbar();
        view.onAuthenticationFailed(errorMsg);
    }


    public void syncUserData() {
        //download data from remote
        //insert retrieved data into database
        favoriteMealsRepository.getAllFavoriteMeals(this);
    }

    @Override
    public void getAllFavoriteMealsRemoteOnSuccess(List<String> mealsIds) {
        //insert retrieved data into database
        compositeDisposable.add(
                Observable
                        .fromIterable(mealsIds)
                        .subscribeOn(Schedulers.io())
                        .flatMapSingle(mealId -> mealRepository.getMealById(mealId))
                        .flatMapCompletable(mealsItemBooleanPair ->
                                favoriteMealsRepository.insertFavoriteMeal(mealsItemBooleanPair.first))
                        .subscribe(() -> {
                            compositeDisposable.add(
                                    favoriteMealsRepository
                                            .getFavoriteMeals()
                                            .flatMap(mealsItems ->
                                                    Flowable.fromIterable(mealsItems)
                                                            .map(mealsItem -> mealsItem.getIdMeal())
                                                            .collect(Collectors.toList()).toFlowable())
                                            .subscribe(mealsId -> {
                                                favoriteMealsRepository
                                                        .uploadFavoriteMeals(LoginPresenter.this, mealsId);
                                            }));
                        }));
    }

    @Override
    public void getAllFavoriteMealsRemoteOnFail(String errorMsg) {
        view.onDataSyncedFail();
    }

    @Override
    public void onUploadFavoriteMealsRemoteOnSuccess() {
        view.onDataSyncedSuccess();
    }

    @Override
    public void onUploadFavoriteMealsRemoteOnFail(String errorMsg) {
        view.onDataSyncedFail();
    }


    public void close(){
        compositeDisposable.clear();
    }
}
