package com.mustafa.dishdash.main.data_layer.firebase.future_plane;

public interface UploadFuturePlanesCallBack {
    void onUploadFuturePlanesRemoteOnSuccess();

    void onUploadFuturePlanesRemoteOnFail(String userNotLoggedIn);
}
