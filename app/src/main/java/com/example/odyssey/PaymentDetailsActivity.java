package com.example.odyssey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentDetailsActivity extends AppCompatActivity {

    private TextView pickupDateTimeValue;
    private TextView dropoffDateTimeValue;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private Button sendReqBtn;
    private Button cancelBtn;
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

        // Initialize UI elements
        pickupDateTimeValue = findViewById(R.id.pickup_date_time_value);
        dropoffDateTimeValue = findViewById(R.id.dropoff_date_time_value);

        sendReqBtn = findViewById(R.id.send_req_btn);
        cancelBtn = findViewById(R.id.cancel_button);

        // Retrieve booking details passed via intent
        Intent intent = getIntent();
        if (intent != null) {
            String pickupDatetime = intent.getStringExtra("PICKUP_DATETIME");
            String dropoffDatetime = intent.getStringExtra("DROPOFF_DATETIME");

            if (pickupDatetime != null) {
                pickupDateTimeValue.setText(convertToDisplayFormat(pickupDatetime));
            } else {
                pickupDateTimeValue.setText("No Pickup Date/Time");
            }

            if (dropoffDatetime != null) {
                dropoffDateTimeValue.setText(convertToDisplayFormat(dropoffDatetime));
            } else {
                dropoffDateTimeValue.setText("No Drop-off Date/Time");
            }
        } else {
            Toast.makeText(this, "No booking details received.", Toast.LENGTH_SHORT).show();
            finish();
        }

        cancelBtn.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        sendReqBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Request Send button Clicked", Toast.LENGTH_LONG).show();
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
    public static String convertToDisplayFormat(String datetime) {
        try {
            // Adjust the input formatter to match the incoming format
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // Display formatter remains the same
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy");
            
            // Parse and format the datetime
            LocalDateTime dateTime = LocalDateTime.parse(datetime, inputFormatter);
            return dateTime.format(displayFormatter);
        } catch (Exception e) {
            Log.e("DateConversionError", "Invalid date format: " + datetime, e);
            return "Invalid Date";
        }
    }
}