package com.mustafa.dishdash.main.data_layer.firebase.favorite_meals;

public interface UploadRemoteFavoriteMealsCallBack {
    void onUploadFavoriteMealsRemoteOnSuccess();

    void onUploadFavoriteMealsRemoteOnFail(String errorMsg);

}
