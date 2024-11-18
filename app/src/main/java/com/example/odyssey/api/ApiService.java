package com.example.odyssey.api;

import com.example.odyssey.models.LoginRequest;
import com.example.odyssey.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("authentication/login.php")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}

