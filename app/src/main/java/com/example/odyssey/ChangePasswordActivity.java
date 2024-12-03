package com.example.odyssey;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.odyssey.api.ApiService;
import com.example.odyssey.api.RetrofitClient;
import com.example.odyssey.models.ApiResponse;
import com.example.odyssey.models.PasswordRequest;
import com.example.odyssey.models.ProfileResponse;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.odyssey.databinding.ActivityChangePasswordBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText passwordInput;
    private EditText retypePasswordInput;
    private Button changePasswordButton;
    private SharedPreferences sharedPreferences;
    private String bearerToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        passwordInput = findViewById(R.id.passwordInput);
        retypePasswordInput = findViewById(R.id.retypePasswordInput);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        sharedPreferences = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        bearerToken = sharedPreferences.getString("authToken", null);

        changePasswordButton.setOnClickListener(v -> changePassword());
    }

    private void changePassword() {

        String password = passwordInput.getText().toString().trim();
        String retypePassword = retypePasswordInput.getText().toString().trim();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(retypePassword)) {
            Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check password length
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if passwords match
        if (!password.equals(retypePassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        Call<ApiResponse> call = apiService.updatePassword("Bearer " + bearerToken, new PasswordRequest(password));

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    Toast.makeText(ChangePasswordActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Failed to update password.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
