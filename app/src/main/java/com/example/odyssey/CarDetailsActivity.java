package com.example.odyssey;

import android.os.Bundle;
import android.widget.ImageView;
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
    private ImageView main_car_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        main_car_image = findViewById(R.id.main_car_image);
//        String carId = getIntent().getStringExtra("CAR_ID");
        String carId = "2";
        if (carId != null) {
            fetchVehicleById(Integer.parseInt(carId));
        } else {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
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
                        System.out.println("Hello");
                        System.out.println(vehicleResponse.getData().getVehicle_id());
                        Toast.makeText(CarDetailsActivity.this, vehicleResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Glide.with(CarDetailsActivity.this)
                                .load(vehicleResponse.getData().getMain_image())
                                .placeholder(R.drawable.car1)
                                .error(R.drawable.car1)
                                .into(main_car_image);
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