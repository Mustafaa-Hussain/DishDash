package com.mustafa.dishdash.main.profile.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.auth.AuthenticationActivity;
import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.auth.data_layer.firebase.UserRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.db.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.FavoritesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;
import com.mustafa.dishdash.main.profile.presenter.ProfilePresenter;

public class ProfileFragment extends Fragment implements ProfileView {

    private Button btnSignOut, btnSyncData;

    private ProfilePresenter presenter;

    public ProfileFragment() {
        // Required empty public constructor
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
        TextView textView = view.findViewById(R.id.profile);
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), AuthenticationActivity.class);
            startActivity(intent);
        });
        setupUI(view);

        presenter = new ProfilePresenter(MealsRepository.getInstance(new MealsRemoteDatasource()
                , new TodayMealLocalDatasource(getContext())
                , new FavoritesMealsLocalDatasource(getContext()))
                , FavoriteMealsRepository.getInstance(new FavoritesMealsLocalDatasource(getContext())
                , new FavoritesRemoteDatasource())
                , this);


        btnSyncData.setOnClickListener(v -> {
            presenter.syncUserData();
        });


        btnSignOut.setOnClickListener(v -> {
            presenter.clearFavorites();
            FirebaseAuth.getInstance().signOut();
        });
    }

    private void setupUI(View view) {
        btnSignOut = view.findViewById(R.id.sign_out);
        btnSyncData = view.findViewById(R.id.btn_sync_firebase);

    }

    @Override
    public void syncDataSuccessfully() {
        if (getContext() != null)
            Toast.makeText(getContext(), "Data sync successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void syncDataFailed(String errorMsg) {
        if (getContext() != null)
            Toast.makeText(getContext(), "Failed to sync data!", Toast.LENGTH_SHORT).show();
    }
}