package com.mustafa.dishdash.main.Planes.view;

import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;

import java.util.List;

public interface PlanesView {
    void onGetAllFuturePlanesSuccess(List<FuturePlane> futurePlanes);

    void onGetAllFuturePlanesFail(String errorMsg);

    void onDeleteFuturePlaneSuccess();

    void onDeleteFuturePlaneFail(String errorMsg);
    void onUserNotLoggedIn();

    void onSyncSuccess();
}
