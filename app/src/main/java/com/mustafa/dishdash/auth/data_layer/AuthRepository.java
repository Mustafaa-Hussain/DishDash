package com.mustafa.dishdash.auth.data_layer;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.mustafa.dishdash.auth.data_layer.firebase.AuthNetworkCallback;
import com.mustafa.dishdash.auth.data_layer.firebase.UserRemoteDatasource;

public class AuthRepository {
    private UserRemoteDatasource remoteDatasource;
    private static AuthRepository instance;

    private AuthRepository(UserRemoteDatasource remoteDatasource) {
        this.remoteDatasource = remoteDatasource;
    }

    public static AuthRepository getInstance(UserRemoteDatasource remoteDatasource) {
        if (instance == null) {
            instance = new AuthRepository(remoteDatasource);
        }
        return instance;
    }

    public void authenticateUser(AuthNetworkCallback callback, String email, String password) {
        remoteDatasource.authenticateUser(callback, email, password);
    }

    public void authenticateUser(AuthNetworkCallback callback, Task<GoogleSignInAccount> token) {
        remoteDatasource.authenticateUser(callback, token);
    }


    public void registerUser(AuthNetworkCallback callback, String username, String email, String password) {
        remoteDatasource.registerUser(callback, username, email, password);
    }

    public boolean isAuthenticated() {
        return remoteDatasource.isAuthenticated();
    }

    public String getCurrentAuthenticatedUsername() {
        return remoteDatasource.getCurrentAuthenticated();
    }

}
