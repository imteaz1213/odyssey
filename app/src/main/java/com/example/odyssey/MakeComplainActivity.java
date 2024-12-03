package com.example.odyssey;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.odyssey.api.ApiService;
import com.example.odyssey.api.RetrofitClient;
import com.example.odyssey.models.ComplaintsRequest;
import com.example.odyssey.models.ComplaintsResponse;
import com.example.odyssey.models.LoginRequest;
import com.example.odyssey.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MakeComplainActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button submitButton;

    private SharedPreferences sharedPreferences;
    private String bearerToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_complain);


        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        submitButton = findViewById(R.id.submitButton);

        sharedPreferences = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        bearerToken = sharedPreferences.getString("authToken", null);


        submitButton.setOnClickListener(v -> validateAndSubmit());
    }

    private void validateAndSubmit() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();


        if (TextUtils.isEmpty(title)) {
            titleEditText.setError("Title is required");
            titleEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            descriptionEditText.setError("Description is required");
            descriptionEditText.requestFocus();
            return;
        }

        if (description.length() < 20) {
            descriptionEditText.setError("Description must be at least 20 characters");
            descriptionEditText.requestFocus();
            return;
        }

        submitComplaint(title, description);
    }


    private void submitComplaint(String title, String description) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<ComplaintsResponse> call = apiService.makeComplaints("Bearer " + bearerToken, new ComplaintsRequest(title, description));

        call.enqueue(new Callback<ComplaintsResponse>() {

            @Override
            public void onResponse(Call<ComplaintsResponse> call, Response<ComplaintsResponse> response) {
                ComplaintsResponse complaintsResponse = response.body();
                if ("true".equals(complaintsResponse.getStatus()) && complaintsResponse.getMessage() != null) {
                    Toast.makeText(MakeComplainActivity.this, complaintsResponse.getMessage(), Toast.LENGTH_LONG).show();
                    titleEditText.setText("");
                    descriptionEditText.setText("");
                } else {
                    Toast.makeText(MakeComplainActivity.this, complaintsResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ComplaintsResponse> call, Throwable t) {
                Toast.makeText(MakeComplainActivity.this, "Network error. Please try again later.", Toast.LENGTH_LONG).show();
            }
        });
    }
}