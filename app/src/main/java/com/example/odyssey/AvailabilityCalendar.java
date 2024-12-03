package com.example.odyssey;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.odyssey.databinding.ActivityAvailabilityCalendarBinding;

public class AvailabilityCalendar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAvailabilityCalendarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_calendar);
    }
}