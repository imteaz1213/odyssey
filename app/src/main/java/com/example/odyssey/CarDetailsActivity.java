package com.example.odyssey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.odyssey.api.ApiService;
import com.example.odyssey.api.RetrofitClient;
import com.example.odyssey.models.VehicleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarDetailsActivity extends AppCompatActivity {
    private ImageView horizontalImage;
    private ImageView main_car_image;
    private LinearLayout imageContainer;
    private TextView car_title;
    private Button bookNowButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        main_car_image = findViewById(R.id.main_car_image);
        car_title = findViewById(R.id.car_title);
        imageContainer = findViewById(R.id.horizontal_images_container);
        bookNowButton = findViewById(R.id.bottom_btn);

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

        bookNowButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), BookingDetailsActivity.class);
            intent.putExtra("CAR_ID", carId);
            v.getContext().startActivity(intent);
        });
    }


    private void setupImageSlider(String mainImage, String frontImage, String backImage, String leftImage, String rightImage, String interiorImage) {
        String[] imageIds = { mainImage, frontImage, backImage, leftImage, rightImage, interiorImage};

        for (String imageId : imageIds) {
            horizontalImage = new ImageView(this);
            horizontalImage.setLayoutParams(new LinearLayout.LayoutParams(300, 150));
            horizontalImage.setPadding(10, 0, 10, 0);

            Glide.with(this)
                    .load(imageId)
                    .error(R.drawable.car1)
                    .into(horizontalImage);

            horizontalImage.setOnClickListener(v ->
                    Glide.with(this)
                            .load(imageId)
                            .error(R.drawable.car1)
                            .into(main_car_image)
            );

            imageContainer.addView(horizontalImage);
        }
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
                         Glide.with(CarDetailsActivity.this)
                                .load(vehicleResponse.getData().getMain_image())
                                .placeholder(R.drawable.car1)
                                .error(R.drawable.car1)
                                .into(main_car_image);

                        car_title.setText(vehicleResponse.getData().getModel());
                        setupImageSlider(
                                vehicleResponse.getData().getMain_image(),
                                vehicleResponse.getData().getFront_image(),
                                vehicleResponse.getData().getBack_image(),
                                vehicleResponse.getData().getLeft_image(),
                                vehicleResponse.getData().getRight_image(),
                                vehicleResponse.getData().getInterior_image()
                                );

                    } else {
                        Toast.makeText(CarDetailsActivity.this, vehicleResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CarDetailsActivity.this, "Failed to fetch details: Invalid response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VehicleResponse> call, Throwable t) {
                Toast.makeText(CarDetailsActivity.this, "Failed to fetch vehicle details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}