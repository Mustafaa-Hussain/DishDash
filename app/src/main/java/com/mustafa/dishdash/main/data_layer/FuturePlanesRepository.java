package com.mustafa.dishdash.main.data_layer;

import com.mustafa.dishdash.main.data_layer.db.future_planes.FuturePlanesLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.entities.FuturePlaneEntity;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.FuturePlanesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.GetRemoteFuturePlanesCallBack;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.UploadFuturePlanesCallBack;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FuturePlanesRepository {
    private static FuturePlanesRepository instance;
    private FuturePlanesLocalDatasource futurePlanesLocalDatasource;
    private FuturePlanesRemoteDatasource futurePlanesRemoteDatasource;

    private FuturePlanesRepository(FuturePlanesLocalDatasource futurePlanesLocalDatasource
            , FuturePlanesRemoteDatasource futurePlanesRemoteDatasource) {
        this.futurePlanesLocalDatasource = futurePlanesLocalDatasource;
        this.futurePlanesRemoteDatasource = futurePlanesRemoteDatasource;
    }

    public static FuturePlanesRepository getInstance(FuturePlanesLocalDatasource futurePlanesLocalDatasource
            , FuturePlanesRemoteDatasource futurePlanesRemoteDatasource) {
        if (instance == null) {
            instance = new FuturePlanesRepository(futurePlanesLocalDatasource,
                    futurePlanesRemoteDatasource);
        }
        return instance;
    }

    public Flowable<List<FuturePlane>> getAllFuturePlanes() {
        return futurePlanesLocalDatasource
                .getAllFuturePlanes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<FuturePlane> getFuturePlaneByMealId(String mealId) {
        return futurePlanesLocalDatasource
                .getFuturePlaneByMealId(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable insertFuturePlane(FuturePlane futurePlane) {
        return futurePlanesLocalDatasource
                .insertFuturePlane(futurePlane)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteFuturePlane(FuturePlane futurePlane) {
        return futurePlanesLocalDatasource
                .deleteFuturePlane(futurePlane)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable clearFuturePlanes() {
        return futurePlanesLocalDatasource
                .clearFuturePlanes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getAllFuturePlanes(GetRemoteFuturePlanesCallBack callBack) {
        futurePlanesRemoteDatasource.getFuturePlanes(callBack);
    }

    public void uploadFuturePlanes(UploadFuturePlanesCallBack callBack, List<FuturePlaneEntity> futurePlanes) {
        futurePlanesRemoteDatasource.uploadFuturePlanes(callBack, futurePlanes);
    }

}
