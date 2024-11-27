package com.example.odyssey;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.odyssey.api.ApiService;
import com.example.odyssey.api.RetrofitClient;
import com.example.odyssey.models.VehicleResponse;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsActivity extends AppCompatActivity {

    private ImageView car_image;
    private TextView car_name;
    private TextView car_rating;
    private LinearLayout pickup_date_container;
    private LinearLayout pickup_time_container;
    private TextView pickupDate;
    private TextView pickupTime;
    private TextView returnDate;
    private TextView returnTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        car_image = findViewById(R.id.car_image);
        car_name = findViewById(R.id.car_name);
        car_rating = findViewById(R.id.car_rating);

        pickup_date_container = findViewById(R.id.pickup_date_container);
        pickup_time_container = findViewById(R.id.pickup_time_container);

        pickupDate = findViewById(R.id.pickup_datepicker_hint);
        pickupTime = findViewById(R.id.pickup_timepicker_hint);
//        returnDate = findViewById(R.id.return_date_picker_hint);
//        returnTime = findViewById(R.id.return_date_time_hint);

        String carId = getIntent().getStringExtra("CAR_ID");
        if (carId != null) {
            try {
                int vehicleId = Integer.parseInt(carId);
                fetchVehicleById(vehicleId);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid vehicle ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Car ID not provided!", Toast.LENGTH_SHORT).show();
            finish();
        }


        pickup_date_container.setOnClickListener(v-> showDatePicker(pickupDate));
        pickup_time_container.setOnClickListener(v-> showTimePicker(pickupTime));
    }

    private void fetchVehicleById(int vehicleId) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<VehicleResponse> call = apiService.getVehicleById(vehicleId);
        call.enqueue(new Callback<VehicleResponse>() {
            @Override
            public void onResponse(Call<VehicleResponse> call, Response<VehicleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    VehicleResponse vehicleResponse = response.body();

                    if ("true".equals(vehicleResponse.getStatus())) {
                        Glide.with(BookingDetailsActivity.this)
                                .load(vehicleResponse.getData().getMain_image())
                                .placeholder(R.drawable.car1)
                                .error(R.drawable.car1)
                                .into(car_image);

                        car_name.setText(vehicleResponse.getData().getModel());

                    } else {
                        Toast.makeText(BookingDetailsActivity.this, vehicleResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BookingDetailsActivity.this, "Failed to fetch details: Invalid response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VehicleResponse> call, Throwable t) {
                Toast.makeText(BookingDetailsActivity.this, "Failed to fetch vehicle details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker(TextView field) {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(
                BookingDetailsActivity.this,
                (view, year, month, dayOfMonth) ->
                        field.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year)),
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void showTimePicker(TextView field) {
        Calendar cal = Calendar.getInstance();
        new TimePickerDialog(
                this,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                (view, hourOfDay, minute) -> field.setText(String.format("%02d:%02d", hourOfDay, minute)),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
        ).show();
    }

}

