package com.example.odyssey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.odyssey.api.ApiService;
import com.example.odyssey.api.RetrofitClient;
import com.example.odyssey.models.ApiResponse;
import com.example.odyssey.models.BookingRequest;
import com.example.odyssey.models.PaymentRequest;
import com.example.odyssey.models.PaymentResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetailsActivity extends AppCompatActivity {

    private TextView pickupDateTimeValue;
    private TextView dropoffDateTimeValue;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private Button sendReqBtn;
    private Button cancelBtn;
    private String bearerToken;
    private SharedPreferences sharedPreferences;

    private WebView webView; // Add WebView as a class member for handling navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Payment Details");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        sharedPreferences = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        bearerToken = sharedPreferences.getString("authToken", null);
        if (bearerToken == null || bearerToken.isEmpty()) {
            Toast.makeText(this, "Unauthorized User", Toast.LENGTH_LONG).show();
            logout();
        }

        // Initialize UI elements
        pickupDateTimeValue = findViewById(R.id.pickup_date_time_value);
        dropoffDateTimeValue = findViewById(R.id.dropoff_date_time_value);

        sendReqBtn = findViewById(R.id.send_req_btn);
        cancelBtn = findViewById(R.id.cancel_button);

        // Retrieve booking details passed via intent
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String driverId = extras.getString("DRIVER_ID", "N/A");
                String pickupDatetime = extras.getString("PICKUP_DATETIME", "N/A");
                String dropoffDatetime = extras.getString("DROPOFF_DATETIME", "N/A");
                String pickupLocation = extras.getString("PICKUP_LOCATION", "N/A");
                String dropoffLocation = extras.getString("DROPOFF_LOCATION", "N/A");
                String numberOfPassengers = extras.getString("NUMBER_OF_PASSENGER", "0");
                String numberOfStoppages = extras.getString("NUMBER_OF_STOPPAGE", "0");

                pickupDateTimeValue.setText(convertToDisplayFormat(pickupDatetime));
                dropoffDateTimeValue.setText(convertToDisplayFormat(dropoffDatetime));

                BookingRequest bookingRequest = new BookingRequest(
                        driverId,
                        pickupDatetime,
                        dropoffDatetime,
                        pickupLocation,
                        dropoffLocation,
                        numberOfPassengers,
                        numberOfStoppages
                );

                sendReqBtn.setOnClickListener(v -> makePaymentRequest("50")); // Example amount
            } else {
                Toast.makeText(this, "No booking details received.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "No intent data received.", Toast.LENGTH_SHORT).show();
            finish();
        }

        cancelBtn.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void makePaymentRequest(String amount) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<PaymentResponse> call = apiService.makePayment(new PaymentRequest(amount));
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PaymentResponse paymentResponse = response.body();
                    String status = paymentResponse.getStatus();
                    String message = paymentResponse.getMessage();
                    String url = paymentResponse.getUrl();

                    if ("true".equalsIgnoreCase(status) && url != null) {
                        openPaymentPage(url); // Redirect to payment page
                    } else {
                        Toast.makeText(PaymentDetailsActivity.this, message != null ? message : "Payment failed.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PaymentDetailsActivity.this, "Unexpected response from server.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Log.e("PaymentError", "Error while making payment request", t);
                Toast.makeText(PaymentDetailsActivity.this, "Payment request failed! "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openPaymentPage(String url) {
        setContentView(R.layout.activity_payment_webview); // Load WebView layout
        webView = findViewById(R.id.payment_webview);

        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript
        webView.getSettings().setDomStorageEnabled(true); // Enable DOM storage
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        webView.loadUrl(url); // Load payment URL
    }


    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    private void logout() {
        sharedPreferences.edit().remove("authToken").apply();
        Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, SigninActivity.class));
        finish();
    }

    public static String convertToDisplayFormat(String datetime) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy");
            LocalDateTime dateTime = LocalDateTime.parse(datetime, inputFormatter);
            return dateTime.format(displayFormatter);
        } catch (Exception e) {
            Log.e("DateConversionError", "Invalid date format: " + datetime, e);
            return "Invalid Date";
        }
    }
}
