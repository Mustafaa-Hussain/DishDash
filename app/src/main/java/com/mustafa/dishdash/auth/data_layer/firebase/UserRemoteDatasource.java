package com.mustafa.dishdash.auth.data_layer.firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserRemoteDatasource {
    private FirebaseAuth auth;
    private Activity activity;

    public UserRemoteDatasource(Activity activity) {
        this.activity = activity;
        auth = FirebaseAuth.getInstance();
    }

    public void authenticateUser(AuthNetworkCallback callback, String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.onAuthSuccess(auth.getCurrentUser());

                        } else {
                            callback.onAuthFailed(task.getException().getMessage());
                        }
                    }
                });
    }

    public void authenticateUser(AuthNetworkCallback callback, Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(activity, t -> {
                        if (task.isSuccessful()) {
                            callback.onAuthSuccess(mAuth.getCurrentUser());
                        } else {
                            callback.onAuthFailed(t.getException().getMessage());
                        }
                    });
        } catch (ApiException e) {
            callback.onAuthFailed(e.getMessage());
        }
    }

    public void registerUser(AuthNetworkCallback callback, String username, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUsername(callback, username);
                        } else {
                            callback.onAuthFailed(task.getException().getMessage());
                        }
                    }
                });
    }

    public void updateUsername(AuthNetworkCallback callback, String username) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        auth.getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onAuthSuccess(auth.getCurrentUser());
                        }
                    }
                });
    }

    public boolean isAuthenticated() {
        return auth.getCurrentUser() != null;
    }

    public String getCurrentAuthenticatedUsername() {
        if (isAuthenticated()) {
            return auth.getCurrentUser().getDisplayName();
        } else {
            return null;
        }
    }

    public String getCurrentAuthenticatedUserEmail() {
        if (isAuthenticated()) {
            return auth.getCurrentUser().getEmail();
        } else {
            return null;
        }
    }

    public void logoutUser() {
        auth.signOut();
    }
}
