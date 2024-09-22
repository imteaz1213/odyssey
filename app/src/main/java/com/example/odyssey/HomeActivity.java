package com.example.odyssey;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.odyssey.fragments.HistoryFragment;
import com.example.odyssey.fragments.HomeFragment;
import com.example.odyssey.fragments.ProfileFragment;
import com.example.odyssey.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(menuItem -> {
            Fragment fragment;

            if (menuItem.getItemId() == R.id.navigation_profile) {
                fragment = new ProfileFragment();
            } else if (menuItem.getItemId() == R.id.navigation_history) {
                fragment = new HistoryFragment();
            } else if (menuItem.getItemId() == R.id.navigation_settings) {
                fragment = new SettingFragment();
            } else {
                fragment = new HomeFragment();
            }

            load_fragment(fragment);
            return true;
        });

        bottomNavigation.setSelectedItemId(R.id.navigation_home);
    }

    private void load_fragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, fragment).commit();
    }
}