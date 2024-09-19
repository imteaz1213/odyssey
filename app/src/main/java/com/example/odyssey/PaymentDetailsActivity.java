package com.example.odyssey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PaymentDetailsActivity extends Activity {

    private TextView tvTitle,pickupDateTimeValue,returnDateTimeValue;
    private ImageView backButton;
    private TextView pickupDateTime;
    private TextView returnDateTime;
    private TextView totalDuration;
    private TextView totalDistance;
    private TextView acFeatureAmount;
    private TextView distanceFeatureAmount;
    private TextView totalAmount;
    private TextView advanceAmount, totalDurationvalue, totalDistanceValue, acFeatureAmountValue, distanceFeatureAmountValue,totalAmountValue,advanceAmountValue;
    private Button advancePaymentButton,cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        // Initialize views
        tvTitle = findViewById(R.id.tv_title);
        backButton = findViewById(R.id.back_button);
        pickupDateTime = findViewById(R.id.pickup_date_time);
        pickupDateTimeValue = findViewById(R.id.pickup_date_time_value);
        returnDateTime = findViewById(R.id.return_date_time);
        returnDateTimeValue = findViewById(R.id.return_date_time_value);
        totalDuration = findViewById(R.id.total_duration);
        totalDurationvalue = findViewById(R.id.total_duration_value);
        totalDistance = findViewById(R.id.total_distance);
        totalDistanceValue = findViewById(R.id.total_distance_value);
        acFeatureAmount = findViewById(R.id.ac_feature_amount);
        acFeatureAmountValue = findViewById(R.id.ac_feature_amount_value);
        distanceFeatureAmount = findViewById(R.id.distance_feature_amount);
        distanceFeatureAmountValue = findViewById(R.id.distance_feature_amount_value);
        totalAmount = findViewById(R.id.total_amount);
        totalAmountValue = findViewById(R.id.total_amount_value);
        advanceAmount = findViewById(R.id.advance_amount);
        advanceAmountValue = findViewById(R.id.advance_amount_value);
        advancePaymentButton = findViewById(R.id.advance_payment_button);
        cancelButton= findViewById(R.id.cancel_button);

        // Set onClickListener for back button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Or handle back navigation as needed
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Or handle back navigation as needed
            }
        });
        // Set onClickListener for send request button
        advancePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the second activity
                Intent intent = new Intent(PaymentDetailsActivity.this, CompleteTripActivity.class);
                startActivity(intent);
            }
        });

        // Example of setting text programmatically (replace with your actual data)
        pickupDateTimeValue.setText("18 Sept, 2024");
        returnDateTimeValue.setText("18 Sept, 2024");
        totalDurationvalue.setText("5 Hour৳");
        totalDistanceValue.setText("5");
        acFeatureAmountValue.setText("20*5=100 ৳");
        distanceFeatureAmountValue.setText("100*30=3000 ৳");
        totalAmountValue.setText("3100 ৳");
        advanceAmountValue.setText("3100*50%=1600 ৳");
    }
}

