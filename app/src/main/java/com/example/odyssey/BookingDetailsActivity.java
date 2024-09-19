package com.example.odyssey;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class BookingDetailsActivity extends AppCompatActivity {



    private static final String TAG = "BookingDetailsActivity";
    private ImageView carImage,backButton;
    private CalendarView pickupDatePicker;
    private TextView carDetails;
    private TextView carRating;
    private TextView  additionalInfo;
    private ImageView ownerImage, renterImage;
    private TextView ownerName, ownerEmail, renterName, renterEmail,pickupDate,pickupTime, returnDate, returnTime;

    private Button advancePaymentButton, cancelButton;
    private DatePickerDialog.OnDateSetListener dateSet;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_details_activity);

        // Initialize views
        backButton=findViewById(R.id.back_button);
        carImage = findViewById(R.id.car_image);
        carDetails = findViewById(R.id.car_name);
        carRating = findViewById(R.id.car_rating);
        pickupDate = findViewById(R.id.pickup_date_picker_hint);
        pickupTime = findViewById(R.id.Pickup_date_timePicker_hint);
        returnDate = findViewById(R.id.return_date_picker_hint);
        returnTime = findViewById(R.id.return_date_time_hint);
        additionalInfo = findViewById(R.id.additional_info);
        ownerImage = findViewById(R.id.owner_image);
        renterImage = findViewById(R.id.renter_image);
        ownerName = findViewById(R.id.owner_name);
        ownerEmail = findViewById(R.id.owner_email);
        renterName = findViewById(R.id.renter_name);
        renterEmail = findViewById(R.id.renter_email);
//        availabilityCalendar = findViewById(R.id.availability_calendar);
        advancePaymentButton = findViewById(R.id.advance_payment_button);
        cancelButton = findViewById(R.id.cancel_button);

        // Set listeners for buttons
//        advancePaymentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Create an Intent to start the second activity
//                Intent intent = new Intent(BookingDetailsActivity.this, PaymentDetailsActivity.class);
//                startActivity(intent);
//            }
//        });

        cancelButton.setOnClickListener(view -> {
            // Handle cancel button click
        });
        pickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(pickupDate);
            }
        });

        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(pickupTime);
            }
        });
        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(returnDate);
            }
        });
        returnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(returnTime);
            }
        });
        // You can set other necessary data to views here
        setCarImage(R.drawable.car_pic);
        setRenterImage(R.drawable.car_image);
        setOwnerImage(R.drawable.car_image);
        setText(carDetails,"Toyota Axios");
        setText(ownerName,"MD NAZIRUL ISLAM ");
        setText(renterName,"MD TAIJUL ISLAM");
        setText( ownerEmail,"bijoykk1512@gmail.com");
        setText( renterEmail,"joykk1512@gmail.com");
        updateRating(carRating,4f);
        additionalInfo.setOnClickListener(v -> showAcOptionsDialog(additionalInfo));
    }
    private void showAcOptionsDialog(TextView field) {
        final String[] acOptions = {"On", "Off"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select A/C Status")
                .setItems(acOptions, (dialog, which) -> {
                    String selectedOption = acOptions[which];
                    field.setText(selectedOption);
                });
        builder.create().show();
    }
    private void updateRating(TextView field,float rating) {

        field.setText(String.format("%.1f", rating));
    }
    private void setText(TextView field,String str){
        field.setText(str);
    }

    private void showDatePicker(TextView field) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                BookingDetailsActivity.this,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                getDateSetListener(field),
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private DatePickerDialog.OnDateSetListener getDateSetListener(TextView field) {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(TAG, "onDateSet: date: " + day + "/" + (month + 1) + "/" + year);
                String selectedDate = day + "/" + (month + 1) + "/" + year;
                field.setText(selectedDate);
                field.setTextColor(Color.BLACK);
            }
        };
    }
    private void showTimePicker(TextView field) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);


        TimePickerDialog dialog = new TimePickerDialog(
                BookingDetailsActivity.this,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                getTimeSetListener(field),
                hour, minute, true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private TimePickerDialog.OnTimeSetListener getTimeSetListener(TextView field) {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String am;
                if(hour>12) {
                    am ="PM";
                    hour-=12;
                    if(hour==0){
                        am="AM";
                        hour=12;
                    }
                }
                else if(hour==12){
                    am="PM";
                }
                else if(hour==0) {
                    am="AM";
                    hour=12;
                } else am = "AM";
                Log.d(TAG, "onTimeSet: time: " + hour + ":" + String.format("%02d", minute));
                String selectedTime = hour + ":" + String.format("%02d", minute)+" "+am;
                field.setText(selectedTime);
                field.setTextColor(Color.BLACK);
            }
        };
    }
    private void setCarImage(int drawableResId) {carImage.setImageResource(drawableResId);}
    private void setRenterImage(int drawableResId) {
        renterImage.setImageResource(drawableResId);
    }
    private void setOwnerImage(int drawableResId) {
        ownerImage.setImageResource(drawableResId);
    }

}

