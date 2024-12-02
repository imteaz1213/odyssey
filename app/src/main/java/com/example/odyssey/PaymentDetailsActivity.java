package com.example.odyssey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentDetailsActivity extends AppCompatActivity {

    private TextView pickupDateTimeValue;
    private TextView dropoffDateTimeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        // Initialize UI elements
        pickupDateTimeValue = findViewById(R.id.pickup_date_time_value);
        dropoffDateTimeValue = findViewById(R.id.dropoff_date_time_value);

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