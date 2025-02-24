package com.mustafa.dishdash.main.profile.data_layer.firebase.favorite_meals;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesRemoteDatasource {
    public static final String USER_NOT_LOGGED_IN = "user_not_logged_in";
    private static final String FAVORITE_COLLECTION_NAME = "favorites";
    private static final String FAV_MEALS = "fav_meals";
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;


    public FavoritesRemoteDatasource() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void getFavoriteMeals(GetRemoteFavoriteMealsCallBack callBack) {
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();
            DocumentReference documentReference = firestore
                    .collection(FAVORITE_COLLECTION_NAME)
                    .document(userId);

            documentReference.get()
                    .addOnSuccessListener(
                            documentSnapshot -> {
//                                Log.i("TAG", documentSnapshot.toObject(FavoriteList.class).getFavoriteMeals().toString());
//                                callBack.getAllFavoriteMealsRemoteOnSuccess(
//                                        documentSnapshot.toObject(FavoriteList.class).getFavoriteMeals());
                            })
                    .addOnFailureListener(e -> callBack.
                            getAllFavoriteMealsRemoteOnFail(e.getMessage()));
        } else {
            callBack.getAllFavoriteMealsRemoteOnFail(USER_NOT_LOGGED_IN);
        }
    }

    public void uploadFavoriteMeals(UploadRemoteFavoriteMealsCallBack callBack, List<String> mealsIds) {
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();

            Map<String, List<String>> fav_meals = new HashMap<>();

            fav_meals.put(FAV_MEALS, mealsIds);

            DocumentReference documentReference = firestore
                    .collection(FAVORITE_COLLECTION_NAME)
                    .document(userId);

            documentReference.set(fav_meals)
                    .addOnSuccessListener(unused -> callBack.onUploadFavoriteMealsRemoteOnSuccess())
                    .addOnFailureListener(e -> callBack.onUploadFavoriteMealsRemoteOnFail(e.getMessage()));

        } else {
            callBack.onUploadFavoriteMealsRemoteOnFail(USER_NOT_LOGGED_IN);
        }
    }

}
