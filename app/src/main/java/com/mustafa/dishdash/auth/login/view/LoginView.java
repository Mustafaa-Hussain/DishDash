package com.mustafa.dishdash.auth.login.view;

import com.google.firebase.auth.FirebaseUser;

public interface LoginView {
    String FILL_EMAIL = "fill_email";
    String INVALID_EMAIL = "invalid_email";
    String FILL_PASSWORD = "fill_password";
    String PASSWORD_LENGTH = "password_length";

    void onAuthenticationSuccess(String user);

    void onAuthenticationFailed(String errorMsg);

    void emailError(String errorMsg);

    void passwordError(String errorMsg);

    void showProgressbar();

    void hideProgressbar();
}
