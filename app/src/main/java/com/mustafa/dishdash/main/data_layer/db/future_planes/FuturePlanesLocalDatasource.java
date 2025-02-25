package com.mustafa.dishdash.main.data_layer.db.future_planes;

import android.content.Context;

import com.mustafa.dishdash.main.data_layer.db.SavedMealsDB;
import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FuturePlanesLocalDatasource {
    private FuturePlanesDAO dao;

    public FuturePlanesLocalDatasource(Context context) {
        dao = SavedMealsDB.getInstance(context).getFuturePlanesDAO();
    }

    public Flowable<List<FuturePlane>> getAllFuturePlanes() {
        return dao.getAllFuturePlanes();
    }

    public Single<FuturePlane> getFuturePlaneByMealId(String mealId) {
        return dao.getFuturePlaneByMealId(mealId);
    }

    public Completable insertFuturePlane(FuturePlane futurePlane) {
        return dao.insertFuturePlane(futurePlane);
    }

    public Completable deleteFuturePlane(FuturePlane futurePlane) {
        return dao.deleteFuturePlane(futurePlane);
    }

}
