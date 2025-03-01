package com.mustafa.dishdash.auth.data_layer.firebase;

import com.google.firebase.auth.FirebaseUser;

public interface AuthNetworkCallback {
    void onAuthSuccess(FirebaseUser user);

    void onAuthFailed(String errorMsg);
}
