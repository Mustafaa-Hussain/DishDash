package com.mustafa.dishdash.main.data_layer.firebase.future_plane;

import com.mustafa.dishdash.main.data_layer.firebase.future_plane.entities.FuturePlaneEntity;

import java.util.List;

public interface GetRemoteFuturePlanesCallBack {
    void getAllFuturePlanesRemoteOnSuccess(List<FuturePlaneEntity> strings);
    void getAllFuturePlanesRemoteOnFail(String errorMsg);
}
