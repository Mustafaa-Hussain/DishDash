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

import com.google.firebase.auth.FirebaseAuth;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.auth.AuthenticationActivity;

public class ProfileFragment extends Fragment {

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

        Button signOut = view.findViewById(R.id.sign_out);
        signOut.setOnClickListener(v-> FirebaseAuth.getInstance().signOut());
    }
}