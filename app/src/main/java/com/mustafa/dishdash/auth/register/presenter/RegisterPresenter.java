package com.mustafa.dishdash.auth.register.presenter;

import static com.mustafa.dishdash.auth.register.view.RegisterView.FILL_CONFIRM_PASSWORD;
import static com.mustafa.dishdash.auth.register.view.RegisterView.FILL_EMAIL;
import static com.mustafa.dishdash.auth.register.view.RegisterView.FILL_PASSWORD;
import static com.mustafa.dishdash.auth.register.view.RegisterView.FILL_USERNAME;
import static com.mustafa.dishdash.auth.register.view.RegisterView.INVALID_EMAIL_FROMATE;
import static com.mustafa.dishdash.auth.register.view.RegisterView.PASSWORD_LENGTH;
import static com.mustafa.dishdash.auth.register.view.RegisterView.PASSWORD_NOT_MATCH;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.auth.data_layer.firebase.AuthNetworkCallback;
import com.mustafa.dishdash.auth.register.view.RegisterView;
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

public class RegisterPresenter implements AuthNetworkCallback, GetRemoteFavoriteMealsCallBack, UploadRemoteFavoriteMealsCallBack {
    private AuthRepository authRepository;
    private MealsRepository mealRepository;
    private CompositeDisposable compositeDisposable;
    private RegisterView view;

    public RegisterPresenter(
            AuthRepository authRepository
            , MealsRepository mealsRepository, RegisterView view) {
        this.authRepository = authRepository;
        this.mealRepository = mealsRepository;
        this.view = view;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void registerUser(String username, String email, String password, String confirmPassword) {

        if (isAuthenticated()) {
            view.onRegisterSuccess(getCurrentAuthenticatedUsername());
            return;
        }

        boolean error = false;

        if (username == null || username.isEmpty()) {
            view.usernameError(FILL_USERNAME);
            error = true;
        }

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

        if (confirmPassword == null || confirmPassword.isEmpty()) {
            view.passwordError(FILL_CONFIRM_PASSWORD);
            error = true;
        }

        if (confirmPassword != null && password != null && !password.equals(confirmPassword)) {
            view.passwordError(PASSWORD_NOT_MATCH);
            error = true;
        }

        if (error) {
            return;
        }

        if (EmailValidation.isValidEmail(email)) {
            view.showProgressbar();
            authRepository.registerUser(this, username, email, password);
        } else {
            view.emailError(INVALID_EMAIL_FROMATE);
        }
    }

    public void registerUserWithToken(Task<GoogleSignInAccount> task) {
        view.showProgressbar();
        authRepository.authenticateUser(this, task);
    }

    @Override
    public void onAuthSuccess(FirebaseUser user) {
        view.hideProgressbar();
        view.onRegisterSuccess(user.getDisplayName());
    }

    @Override
    public void onAuthFailed(String errorMsg) {
        view.hideProgressbar();
        view.onRegisterFailed(errorMsg);
    }

    public boolean isAuthenticated() {
        return authRepository.isAuthenticated();
    }

    public String getCurrentAuthenticatedUsername() {
        return authRepository.getCurrentAuthenticatedUsername();
    }

    public void syncUserData() {
        //download data from remote
        //insert retrieved data into database
        mealRepository.getAllFavoriteMeals(this);
    }

    @Override
    public void getAllFavoriteMealsRemoteOnSuccess(List<String> mealsIds) {
        //insert retrieved data into database
        if (mealsIds != null) {
            compositeDisposable.add(
                    Observable
                            .fromIterable(mealsIds)
                            .subscribeOn(Schedulers.io())
                            .flatMapSingle(mealId -> mealRepository.getMealById(mealId))
                            .flatMapCompletable(mealsItemBooleanPair ->
                                    mealRepository.insertFavoriteMeal(mealsItemBooleanPair.first))
                            .subscribe(() -> {
                                compositeDisposable.add(
                                        mealRepository
                                                .getFavoriteMeals()
                                                .flatMap(mealsItems ->
                                                        Flowable.fromIterable(mealsItems)
                                                                .map(mealsItem -> mealsItem.getIdMeal())
                                                                .collect(Collectors.toList()).toFlowable())
                                                .subscribe(mealsId -> {
                                                    mealRepository
                                                            .uploadFavoriteMeals(RegisterPresenter.this, mealsId);
                                                }));
                            }));
        }
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

    public void close() {
        compositeDisposable.clear();
    }
}
