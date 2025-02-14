package com.mustafa.dishdash.home;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mustafa.dishdash.R;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationItemView;

    Fragment homeFragment,
            searchFragment,
            favoritesFragment,
            calenderFragment,
            profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationItemView = findViewById(R.id.bottomNavigationView);
        bottomNavigationItemView.setSelectedItemId(R.id.action_home);

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        favoritesFragment = new FavoritesFragment();
        calenderFragment = new CalenderFragment();
        profileFragment = new ProfileFragment();

        setCurrentFragment(homeFragment);

        bottomNavigationItemView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    setCurrentFragment(homeFragment);
                    break;
                case R.id.action_search:
                    setCurrentFragment(searchFragment);
                    break;
                case R.id.action_favorites:
                    setCurrentFragment(favoritesFragment);
                    break;
                case R.id.action_calender:
                    setCurrentFragment(calenderFragment);
                    break;
                case R.id.action_profile:
                    setCurrentFragment(profileFragment);
                    break;
                default:
                    return false;
            }
            return true;
        });

    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }
}