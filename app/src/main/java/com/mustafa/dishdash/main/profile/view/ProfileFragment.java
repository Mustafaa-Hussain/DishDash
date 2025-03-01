package com.mustafa.dishdash.main.profile.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.auth.AuthenticationActivity;
import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.auth.data_layer.firebase.UserRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.FuturePlanesRepository;
import com.mustafa.dishdash.main.data_layer.db.favorites.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.future_planes.FuturePlanesLocalDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.FavoritesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.FuturePlanesRemoteDatasource;
import com.mustafa.dishdash.main.profile.presenter.ProfilePresenter;

public class ProfileFragment extends Fragment implements ProfileView {

    private Button btnSignOut;

    private View notLoggedInGroup;
    private TextView login;
    private TextView username, email;
    private ImageView userImage;
    private ProfilePresenter presenter;

    public ProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);

        presenter = new ProfilePresenter(
                FuturePlanesRepository.getInstance(
                        new FuturePlanesLocalDatasource(getContext()),
                        new FuturePlanesRemoteDatasource())
                , FavoriteMealsRepository.getInstance(new FavoritesMealsLocalDatasource(getContext())
                , new FavoritesRemoteDatasource())
                , AuthRepository.getInstance(new UserRemoteDatasource(getActivity()))
                , this);


        login.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AuthenticationActivity.class));
        });
        btnSignOut.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.are_sure_you_want_to_logout)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        presenter.logoutUser();
                        showUserNotLoggedIn();
                    })
                    .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });
            builder.create().show();

        });
    }

    @Override
    public void onResume() {
        if (!presenter.isAuthenticated()) {
            showUserNotLoggedIn();
        } else {
            hideUserNotLoggedIn();
            username.setText(presenter.getUsername());
            email.setText(presenter.getEmail());
        }
        super.onResume();
    }

    private void showUserNotLoggedIn() {
        notLoggedInGroup.setVisibility(VISIBLE);
        //hide other component
        btnSignOut.setVisibility(GONE);
        username.setVisibility(GONE);
        email.setVisibility(GONE);
        userImage.setVisibility(GONE);
    }

    private void hideUserNotLoggedIn() {
        notLoggedInGroup.setVisibility(GONE);

        btnSignOut.setVisibility(VISIBLE);;
        username.setVisibility(VISIBLE);
        email.setVisibility(VISIBLE);
        userImage.setVisibility(VISIBLE);
    }

    @Override
    public void onStop() {
        presenter.close();
        super.onStop();
    }

    private void setupUI(View view) {
        btnSignOut = view.findViewById(R.id.sign_out);
        notLoggedInGroup = view.findViewById(R.id.not_logged_in_group);
        login = view.findViewById(R.id.login);
        username = view.findViewById(R.id.txtUsername);
        email = view.findViewById(R.id.txtEmail);
        userImage = view.findViewById(R.id.user_image);
    }
}