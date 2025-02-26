package com.mustafa.dishdash.main.data_layer.db.future_planes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FuturePlanesDAO {

    @Query("select * from future_planes")
    Flowable<List<FuturePlane>> getAllFuturePlanes();

    @Query("select * from future_planes where idMeal = :mealId")
    Single<FuturePlane> getFuturePlaneByMealId(String mealId);

    @Insert
    Completable insertFuturePlane(FuturePlane futurePlane);

    @Delete
    Completable deleteFuturePlane(FuturePlane futurePlane);

    @Query("delete from future_planes")
    Completable clearFuturePlanes();
}
