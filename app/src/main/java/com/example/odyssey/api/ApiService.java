package com.example.odyssey.api;

import com.example.odyssey.models.LoginRequest;
import com.example.odyssey.models.LoginResponse;
import com.example.odyssey.models.ProfileResponse;
import com.example.odyssey.models.RegistrationRequest;
import com.example.odyssey.models.RegistrationResponse;
import com.example.odyssey.models.VehicleListResponse;
import com.example.odyssey.models.VehicleResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("authentication/login.php")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("authentication/registration.php")
    Call<RegistrationResponse> userRegistration(@Body RegistrationRequest registrationRequest);

    @GET("authentication/profile.php")
    Call<ProfileResponse> getUserProfile(@Header("Authorization") String authToken);

    @GET("vehicle/vehicle-list.php")
    Call<VehicleListResponse> getAllVehicles();

    @GET("vehicle/vehicle-list.php")
    Call<VehicleResponse> getVehicleById(@Query("vehicleId") int vehicleId);
}

