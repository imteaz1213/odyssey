package com.example.odyssey.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.odyssey.ChangePasswordActivity;
import com.example.odyssey.MakeComplainActivity;
import com.example.odyssey.R;
import com.example.odyssey.TakeLeaveActivity;
import com.example.odyssey.UploadVehicle;

public class SettingFragment extends Fragment {
    private LinearLayout vehicleBtn;
    private LinearLayout availabilityBtn;
    private LinearLayout leaveBtn;
    private LinearLayout complainBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        vehicleBtn = view.findViewById(R.id.vehicle_btn);
        availabilityBtn = view.findViewById(R.id.availability_btn);
        leaveBtn = view.findViewById(R.id.leave_btn);
        complainBtn = view.findViewById(R.id.complain_btn);

        vehicleBtn.setOnClickListener(v -> startActivity(new Intent(v.getContext(), UploadVehicle.class)));
//        availabilityBtn.setOnClickListener(v -> startActivity(new Intent(v.getContext(), UploadVehicle.class)));
        leaveBtn.setOnClickListener(v -> startActivity(new Intent(v.getContext(), TakeLeaveActivity.class)));
        complainBtn.setOnClickListener(v -> startActivity(new Intent(v.getContext(), MakeComplainActivity.class)));

        return view;
    }
}