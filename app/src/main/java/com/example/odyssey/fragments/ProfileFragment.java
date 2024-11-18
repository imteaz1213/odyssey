package com.example.odyssey.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.odyssey.R;
import com.example.odyssey.SigninActivity;

public class ProfileFragment extends Fragment {

    private Button logoutBtn;
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logoutBtn = view.findViewById(R.id.logoutBtn);
        sharedPreferences = requireActivity().getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);

        logoutBtn.setOnClickListener(v->logout());

        return view;
    }
    private void logout() {
        sharedPreferences.edit().remove("authToken").apply();
        Toast.makeText(getContext(), "Logged Out Successfully", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getContext(), SigninActivity.class));
        requireActivity().finish();
    }
}