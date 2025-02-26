package com.mustafa.dishdash.main.Planes.presenter;

import com.mustafa.dishdash.main.Planes.view.PlanesView;
import com.mustafa.dishdash.main.data_layer.FuturePlanesRepository;
import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.entities.FuturePlaneEntity;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.UploadFuturePlanesCallBack;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class PlanesPresenter implements UploadFuturePlanesCallBack {
    private CompositeDisposable compositeDisposable;
    private FuturePlanesRepository futurePlanesRepository;
    private PlanesView view;

    public PlanesPresenter(FuturePlanesRepository futurePlanesRepository, PlanesView view) {
        this.futurePlanesRepository = futurePlanesRepository;
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    public void getAllFuturePlanes() {
        compositeDisposable.add(
                futurePlanesRepository
                        .getAllFuturePlanes()
                        .subscribe(futurePlanes ->
                                        view.onGetAllFuturePlanesSuccess(futurePlanes),
                                error -> view.onGetAllFuturePlanesFail(error.getMessage())));
    }

    public void deleteFuturePlanes(FuturePlane futurePlane) {
        compositeDisposable.add(
                futurePlanesRepository
                        .deleteFuturePlane(futurePlane)
                        .subscribe(() -> {
                                    view.onDeleteFuturePlaneSuccess();
                                    syncFutureData();
                                },
                                error -> view.onDeleteFuturePlaneFail(error.getMessage()))
        );
    }

    private void syncFutureData() {
        compositeDisposable.add(
                futurePlanesRepository
                        .getAllFuturePlanes()
                        .flatMap(futurePlanes ->
                                Flowable.fromIterable(futurePlanes)
                                        .map(futurePlane ->
                                                new FuturePlaneEntity(futurePlane.getIdMeal(),
                                                        futurePlane.getDay(),
                                                        futurePlane.getMonth(),
                                                        futurePlane.getYear()))
                                        .collect(Collectors.toList()).toFlowable())
                        .subscribe(futurePlaneEntities -> {
                            futurePlanesRepository
                                    .uploadFuturePlanes(PlanesPresenter.this, futurePlaneEntities);
                        }));
    }

    public void close() {
        compositeDisposable.clear();
    }

    @Override
    public void onUploadFuturePlanesRemoteOnSuccess() {
        view.onSyncSuccess();
    }

    @Override
    public void onUploadFuturePlanesRemoteOnFail(String userNotLoggedIn) {
        view.onUserNotLoggedIn();
    }
}
