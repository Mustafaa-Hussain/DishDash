package com.mustafa.dishdash.auth.register.view;

public interface RegisterView {
    String FILL_USERNAME = "fill_username";
    String FILL_EMAIL = "fill_email";
    String INVALID_EMAIL_FROMATE = "invalid_email";
    String FILL_PASSWORD = "fill_password";
    String FILL_CONFIRM_PASSWORD = "fill_confirm_password";
    String PASSWORD_LENGTH = "password_length";
    String PASSWORD_NOT_MATCH = "password_not_match";

    void onRegisterSuccess(String user);

    void onRegisterFailed(String errorMsg);

    void usernameError(String errorMsg);

    void emailError(String errorMsg);

    void passwordError(String errorMsg);

    void showProgressbar();
    void hideProgressbar();

    void onDataSyncedFail();

    void onDataSyncedSuccess();
}
