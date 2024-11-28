package com.example.odyssey;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.odyssey.api.ApiService;
import com.example.odyssey.api.RetrofitClient;
import com.example.odyssey.models.VehicleResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView car_image;
    private TextView car_name;
    private TextView car_rating;
    private LinearLayout pickup_date_container;
    private LinearLayout pickup_time_container;
    private LinearLayout dropoff_date_container;
    private LinearLayout dropoff_time_container;
    private TextView pickupDate;
    private TextView pickupTime;
    private TextView dropoffDate;
    private TextView dropoffTime;
    private GoogleMap pickupMap;
    private GoogleMap dropoffMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        car_image = findViewById(R.id.car_image);
        car_name = findViewById(R.id.car_name);
        car_rating = findViewById(R.id.car_rating);

        pickup_date_container = findViewById(R.id.pickup_date_container);
        pickup_time_container = findViewById(R.id.pickup_time_container);
        dropoff_date_container = findViewById(R.id.dropoff_date_container);
        dropoff_time_container = findViewById(R.id.dropoff_time_container);

        pickupDate = findViewById(R.id.pickup_datepicker_hint);
        pickupTime = findViewById(R.id.pickup_timepicker_hint);
        dropoffDate = findViewById(R.id.dropoff_datepicker_hint);
        dropoffTime = findViewById(R.id.dropoff_timepicker_hint);

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

        pickup_date_container.setOnClickListener(v -> showDatePicker(pickupDate));
        pickup_time_container.setOnClickListener(v -> showTimePicker(pickupTime));
        dropoff_date_container.setOnClickListener(v -> showDatePicker(dropoffDate));
        dropoff_time_container.setOnClickListener(v -> showTimePicker(dropoffTime));

        SupportMapFragment pickupMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.pickup_map);

        SupportMapFragment dropoffMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dropoff_map);

        if (pickupMapFragment != null) {
            pickupMapFragment.getMapAsync(googleMap -> {
                pickupMap = googleMap;
                setupMapClickListener(pickupMap, "Pickup");
            });
        }

        if (dropoffMapFragment != null) {
            dropoffMapFragment.getMapAsync(googleMap -> {
                dropoffMap = googleMap;
                setupMapClickListener(dropoffMap, "Dropoff");
            });
        }
    }

    private void setupMapClickListener(GoogleMap map, String mapType) {
        map.setOnMapClickListener(latLng -> {
            map.clear();
            String message = String.format(Locale.getDefault(), "%s Map Clicked: %.4f, %.4f", mapType, latLng.latitude, latLng.longitude);
            Toast.makeText(BookingDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
            map.addMarker(new MarkerOptions().position(latLng).title(mapType + " Default Location"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng , 10));
        });
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
//                        car_rating.setText(String.format(Locale.getDefault(), "%.1f", vehicleResponse.getData().getRating()));
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
                this,
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
                (view, hourOfDay, minute) ->
                        field.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
        ).show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}