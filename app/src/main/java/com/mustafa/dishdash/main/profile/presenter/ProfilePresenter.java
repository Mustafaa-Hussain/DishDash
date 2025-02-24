package com.mustafa.dishdash.main.profile.presenter;

import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.profile.data_layer.FavoritesRepository;
import com.mustafa.dishdash.main.profile.data_layer.firebase.favorite_meals.GetRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.profile.data_layer.firebase.favorite_meals.UploadRemoteFavoriteMealsCallBack;
import com.mustafa.dishdash.main.profile.view.ProfileView;

import java.util.List;

public class ProfilePresenter implements GetRemoteFavoriteMealsCallBack, UploadRemoteFavoriteMealsCallBack {
    private FavoritesRepository favoritesRepository;
    private FavoriteMealsRepository favoriteMealsRepository;
    private AuthRepository authRepository;
    private ProfileView view;

    public ProfilePresenter(FavoritesRepository favoritesRepository
            , FavoriteMealsRepository favoriteMealsRepository
            , AuthRepository authRepository
            , ProfileView view) {
        this.favoritesRepository = favoritesRepository;
        this.favoriteMealsRepository = favoriteMealsRepository;
        this.authRepository = authRepository;
        this.view = view;
    }

    public void syncUserData() {
        //download data from remote
        //insert retrieved data into database
        //upload new data to remote
        favoritesRepository.getAllFavoriteMeals(this);
    }

    @Override
    public void getAllFavoriteMealsRemoteOnSuccess(List<MealsItem> favoriteMeals) {

        //insert retrieved data into database
        if (favoriteMeals != null) {
            favoriteMeals.forEach(meal -> {
                favoriteMealsRepository.insertFavoriteMeal(meal);
            });
        }

        //upload new data to remote
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
