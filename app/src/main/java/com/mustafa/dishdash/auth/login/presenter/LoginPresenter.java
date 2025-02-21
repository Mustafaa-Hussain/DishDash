package com.mustafa.dishdash.auth.login.presenter;

import static com.mustafa.dishdash.auth.login.view.LoginView.FILL_EMAIL;
import static com.mustafa.dishdash.auth.login.view.LoginView.FILL_PASSWORD;
import static com.mustafa.dishdash.auth.login.view.LoginView.INVALID_EMAIL;
import static com.mustafa.dishdash.auth.login.view.LoginView.PASSWORD_LENGTH;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.auth.data_layer.firebase.AuthNetworkCallback;
import com.mustafa.dishdash.auth.login.view.LoginView;
import com.mustafa.dishdash.utils.EmailValidation;

public class LoginPresenter implements AuthNetworkCallback {
    private AuthRepository repository;
    private LoginView view;

    public LoginPresenter(AuthRepository repository, LoginView view) {
        this.repository = repository;
        this.view = view;
    }

    public void authenticateUser(String email, String password) {

        if (isAuthenticated()) {
            view.onAuthenticationSuccess(getCurrentAuthenticatedUsername());
            return;
        }

        boolean error = false;
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

        if (error) {
            return;
        }

        if (EmailValidation.isValidEmail(email)) {
            view.showProgressbar();
            repository.authenticateUser(this, email, password);
        } else {
            view.emailError(INVALID_EMAIL);
        }
    }

    public void authenticateUserWithToken(Task<GoogleSignInAccount> task) {
        view.showProgressbar();
        repository.authenticateUser(this, task);
    }

    public boolean isAuthenticated() {
        return repository.isAuthenticated();
    }

    public String getCurrentAuthenticatedUsername() {
        return repository.getCurrentAuthenticatedUsername();
    }

    @Override
    public void onAuthSuccess(FirebaseUser user) {
        view.hideProgressbar();
        view.onAuthenticationSuccess(user.getDisplayName());
    }

    @Override
    public void onAuthFailed(String errorMsg) {
        view.hideProgressbar();
        view.onAuthenticationFailed(errorMsg);
    }
}
