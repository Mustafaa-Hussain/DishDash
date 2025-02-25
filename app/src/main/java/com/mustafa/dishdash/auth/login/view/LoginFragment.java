package com.mustafa.dishdash.auth.login.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.auth.data_layer.firebase.UserRemoteDatasource;
import com.mustafa.dishdash.auth.login.presenter.LoginPresenter;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.db.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.FavoritesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;

public class LoginFragment extends Fragment implements LoginView {

    private TextView txtEmail, txtPassword;
    private Button btnLogin;
    private ImageView loginByGoogle;
    private TextView gotoRegistration;
    private RelativeLayout overlayLayout;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int REQ_ONE_TAP = 2;
    private LoginPresenter presenter;

    public LoginFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);

        presenter = new LoginPresenter(AuthRepository.getInstance(new UserRemoteDatasource(getActivity())),
                FavoriteMealsRepository.getInstance(new FavoritesMealsLocalDatasource(getContext()), new FavoritesRemoteDatasource()),
                MealsRepository.getInstance(new MealsRemoteDatasource(), new TodayMealLocalDatasource(getContext()), new FavoritesMealsLocalDatasource(getContext())),
                this);

        gotoRegistration.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment());
        });

        btnLogin.setOnClickListener(v -> {
            presenter.authenticateUser(txtEmail.getText().toString(), txtPassword.getText().toString());
        });


        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        loginByGoogle.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, REQ_ONE_TAP);
        });

    }

    private void setupUI(View view) {
        gotoRegistration = view.findViewById(R.id.login);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPassword = view.findViewById(R.id.txtPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        loginByGoogle = view.findViewById(R.id.login_with_google);
        overlayLayout = view.findViewById(R.id.overlay_layout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ONE_TAP) {
            presenter.authenticateUserWithToken(GoogleSignIn.getSignedInAccountFromIntent(data));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter.isAuthenticated()) {
            this.onAuthenticationSuccess(presenter.getCurrentAuthenticatedUsername());
        }
    }

    @Override
    public void onStop() {
        presenter.close();
        super.onStop();
    }

    @Override
    public void onAuthenticationSuccess(String username) {
        Toast.makeText(getContext(), "Login successfully", Toast.LENGTH_SHORT).show();
        presenter.syncUserData();
        getActivity().finish();
    }

    @Override
    public void onAuthenticationFailed(String errorMsg) {
        Toast.makeText(getContext(), "Error: " + errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailError(String errorMsg) {
        switch (errorMsg) {
            case FILL_EMAIL:
                txtEmail.setError("Fill email!");
                break;
            case INVALID_EMAIL:
                txtEmail.setError("Not valid Email Address!");
        }
    }

    @Override
    public void passwordError(String errorMsg) {
        switch (errorMsg) {
            case FILL_PASSWORD:
                txtPassword.setError("Fill password!");
                break;
            case PASSWORD_LENGTH:
                txtPassword.setError("Short password length!");
        }
    }

    @Override
    public void showProgressbar() {
        overlayLayout.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        overlayLayout.setVisibility(GONE);
    }

    @Override
    public void onDataSyncedSuccess() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.data_synced, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataSyncedFail() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.failed_to_sync_data, Toast.LENGTH_SHORT).show();
        }
    }
}