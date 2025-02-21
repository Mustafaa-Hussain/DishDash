package com.mustafa.dishdash.auth.register.presenter;

import static com.mustafa.dishdash.auth.register.view.RegisterView.FILL_CONFIRM_PASSWORD;
import static com.mustafa.dishdash.auth.register.view.RegisterView.FILL_EMAIL;
import static com.mustafa.dishdash.auth.register.view.RegisterView.FILL_PASSWORD;
import static com.mustafa.dishdash.auth.register.view.RegisterView.FILL_USERNAME;
import static com.mustafa.dishdash.auth.register.view.RegisterView.INVALID_EMAIL_FROMATE;
import static com.mustafa.dishdash.auth.register.view.RegisterView.PASSWORD_LENGTH;
import static com.mustafa.dishdash.auth.register.view.RegisterView.PASSWORD_NOT_MATCH;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.auth.data_layer.firebase.AuthNetworkCallback;
import com.mustafa.dishdash.auth.register.view.RegisterView;
import com.mustafa.dishdash.utils.EmailValidation;

public class RegisterPresenter implements AuthNetworkCallback {
    private AuthRepository repository;
    private RegisterView view;

    public RegisterPresenter(AuthRepository repository, RegisterView view) {
        this.repository = repository;
        this.view = view;
    }

    public void registerUser(String username, String email, String password, String confirmPassword) {

        if (isAuthenticated()) {
            view.onRegisterSuccess(getCurrentAuthenticatedUsername());
            return;
        }

        boolean error = false;

        if (username == null || username.isEmpty()) {
            view.usernameError(FILL_USERNAME);
            error = true;
        }

        if (email == null || email.isEmpty()) {
            view.emailError(FILL_EMAIL);
            error = true;
        }

        if (password != null && password.length() < 8) {
            view.passwordError(PASSWORD_LENGTH);
            error = true;
        }

        if (password == null || password.isEmpty()) {
            view.passwordError(FILL_PASSWORD);
            error = true;
        }

        if (confirmPassword == null || confirmPassword.isEmpty()) {
            view.passwordError(FILL_CONFIRM_PASSWORD);
            error = true;
        }

        if (confirmPassword != null && password != null && !password.equals(confirmPassword)) {
            view.passwordError(PASSWORD_NOT_MATCH);
            error = true;
        }

        if (error) {
            return;
        }

        if (EmailValidation.isValidEmail(email)) {
            view.showProgressbar();
            repository.registerUser(this, username, email, password);
        } else {
            view.emailError(INVALID_EMAIL_FROMATE);
        }
    }

    public void registerUserWithToken(Task<GoogleSignInAccount> task) {
        view.showProgressbar();
        repository.authenticateUser(this, task);
    }

    @Override
    public void onAuthSuccess(FirebaseUser user) {
        view.hideProgressbar();
        view.onRegisterSuccess(user.getDisplayName());
    }

    @Override
    public void onAuthFailed(String errorMsg) {
        view.hideProgressbar();
        view.onRegisterFailed(errorMsg);
    }

    public boolean isAuthenticated() {
        return repository.isAuthenticated();
    }

    public String getCurrentAuthenticatedUsername() {
        return repository.getCurrentAuthenticatedUsername();
    }
}
