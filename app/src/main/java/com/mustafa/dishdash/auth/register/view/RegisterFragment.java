package com.mustafa.dishdash.auth.register.view;

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
import com.mustafa.dishdash.auth.register.presenter.RegisterPresenter;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.db.favorites.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.future_planes.FuturePlanesLocalDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.FavoritesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.FuturePlanesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;

public class RegisterFragment extends Fragment implements RegisterView {

    private TextView txtUsername, txtEmail, txtPassword, txtConfirmPassword;
    private TextView gotoLogin;
    private Button btnRegister;
    private ImageView registerByGoogle;
    private RelativeLayout overlayLayout;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int REQ_ONE_TAP = 2;
    private RegisterPresenter presenter;


    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);


        gotoLogin.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment());
        });

        presenter = new RegisterPresenter(AuthRepository.getInstance(new UserRemoteDatasource(getActivity())),
                MealsRepository.getInstance(
                        new MealsRemoteDatasource(),
                        new TodayMealLocalDatasource(getContext()),
                        new FavoritesMealsLocalDatasource(getContext()),
                        new FavoritesRemoteDatasource(),
                        new FuturePlanesRemoteDatasource(),
                        new FuturePlanesLocalDatasource(getContext())),
                this);

        btnRegister.setOnClickListener(v -> {
            presenter.registerUser(txtUsername.getText().toString(),
                    txtEmail.getText().toString(),
                    txtPassword.getText().toString(),
                    txtConfirmPassword.getText().toString());
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        registerByGoogle.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, REQ_ONE_TAP);
        });

    }

    private void setupUI(View view) {
        gotoLogin = view.findViewById(R.id.login);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtConfirmPassword = view.findViewById(R.id.txtConfirmPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        registerByGoogle = view.findViewById(R.id.register_with_google);
        overlayLayout = view.findViewById(R.id.overlay_layout);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (presenter.isAuthenticated()) {
            this.onRegisterSuccess(presenter.getCurrentAuthenticatedUsername());
        }
    }

    @Override
    public void onStop() {
        presenter.close();
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ONE_TAP) {
            presenter.registerUserWithToken(GoogleSignIn.getSignedInAccountFromIntent(data));
        }
    }

    @Override
    public void onRegisterSuccess(String user) {
        Toast.makeText(getContext(), "Register successfully!", Toast.LENGTH_SHORT).show();
        presenter.syncUserData();
        getActivity().finish();
    }

    @Override
    public void onRegisterFailed(String errorMsg) {
        if (errorMsg != null)
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void usernameError(String errorMsg) {
        if (errorMsg.equals(FILL_USERNAME)) {
            txtUsername.setError(getString(R.string.fill_username));
        }
    }

    @Override
    public void emailError(String errorMsg) {
        switch (errorMsg) {
            case FILL_EMAIL:
                txtEmail.setError(getString(R.string.fill_email));
                break;
            case INVALID_EMAIL_FROMATE:
                txtEmail.setError(getString(R.string.not_valid_email_address));
        }
    }

    @Override
    public void passwordError(String errorMsg) {
        switch (errorMsg) {
            case FILL_PASSWORD:
                txtPassword.setError(getString(R.string.fill_password));
                break;
            case PASSWORD_LENGTH:
                txtPassword.setError(getString(R.string.short_password_length));
                break;
            case PASSWORD_NOT_MATCH:
                txtConfirmPassword.setError(getString(R.string.password_not_matches));
                break;
            case FILL_CONFIRM_PASSWORD:
                txtConfirmPassword.setError(getString(R.string.fill_confirm_password));
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
    public void onDataSyncedFail() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.failed_to_sync_data, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataSyncedSuccess() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.data_synced, Toast.LENGTH_SHORT).show();
        }
    }
}