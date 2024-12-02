package com.example.odyssey;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

public class UploadVehicle extends AppCompatActivity {

    private TextView tvOwnerProfile, tvMainImage, tvFrontImage, tvBackImage, tvLeftImage, tvRightImage, tvInteriorImage;
    private TextInputLayout licensePlateInputLayout, chassisNumberInputLayout, seatsInputLayout, modelNameInputLayout, mileageInputLayout, yearInputLayout, colorInputLayout;
    private Button btnOwnerProfile, btnMainImage, btnFrontImage, btnBackImage, btnLeftImage, btnRightImage, btnInteriorImage, submitButton;

    private TextView currentTextView; // Keeps track of the target TextView during file selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_vehcile);

        // Initialize TextInputLayout fields
        licensePlateInputLayout = findViewById(R.id.get_license_plate);
        chassisNumberInputLayout = findViewById(R.id.get_chassis_number);
        seatsInputLayout = findViewById(R.id.get_seats);
        modelNameInputLayout = findViewById(R.id.get_model_name);
        mileageInputLayout = findViewById(R.id.get_milage);
        yearInputLayout = findViewById(R.id.get_year);
        colorInputLayout = findViewById(R.id.get_color);

        // Initialize Buttons
        btnOwnerProfile = findViewById(R.id.btn_owner_profile);
        btnMainImage = findViewById(R.id.btn_main_image);
        btnFrontImage = findViewById(R.id.btn_front_image);
        btnBackImage = findViewById(R.id.btn_back_image);
        btnLeftImage = findViewById(R.id.btn_left_image);
        btnRightImage = findViewById(R.id.btn_right_image);
        btnInteriorImage = findViewById(R.id.btn_interior_image);

        // Initialize TextViews
        tvOwnerProfile = findViewById(R.id.tv_owner_profile);
        tvMainImage = findViewById(R.id.tv_main_image);
        tvFrontImage = findViewById(R.id.tv_front_image);
        tvBackImage = findViewById(R.id.tv_back_image);
        tvLeftImage = findViewById(R.id.tv_left_image);
        tvRightImage = findViewById(R.id.tv_right_image);
        tvInteriorImage = findViewById(R.id.tv_interior_image);

        // Register a file picker result launcher
        ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedFile = result.getData().getData();
                        if (selectedFile != null) {
                            String fileName = getFileName(selectedFile);
                            if (currentTextView != null) {
                                currentTextView.setText(fileName);
                            }
                            Toast.makeText(this, "File Selected: " + fileName, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "File selection canceled", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Set onClick listeners for file selection buttons
        btnOwnerProfile.setOnClickListener(v -> openFilePicker(tvOwnerProfile, filePickerLauncher));
        btnMainImage.setOnClickListener(v -> openFilePicker(tvMainImage, filePickerLauncher));
        btnFrontImage.setOnClickListener(v -> openFilePicker(tvFrontImage, filePickerLauncher));
        btnBackImage.setOnClickListener(v -> openFilePicker(tvBackImage, filePickerLauncher));
        btnLeftImage.setOnClickListener(v -> openFilePicker(tvLeftImage, filePickerLauncher));
        btnRightImage.setOnClickListener(v -> openFilePicker(tvRightImage, filePickerLauncher));
        btnInteriorImage.setOnClickListener(v -> openFilePicker(tvInteriorImage, filePickerLauncher));

        // Submit Button Listener
        submitButton = findViewById(R.id.upload_btn);
        submitButton.setOnClickListener(v -> validateAndSubmitForm());
    }

    // Method to open the file picker
    private void openFilePicker(TextView targetTextView, ActivityResultLauncher<Intent> launcher) {
        currentTextView = targetTextView;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Adjust MIME type as needed (e.g., "image/*" for images)
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        launcher.launch(Intent.createChooser(intent, "Select File"));
    }

    // Helper method to extract the file name from a URI
    private String getFileName(Uri uri) {
        String fileName = uri.getLastPathSegment();
        if (fileName != null && fileName.contains("/")) {
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }
        return fileName != null ? fileName : "Unknown File";
    }

    // Form validation and submission
    private void validateAndSubmitForm() {
        // Check if any input field is empty
        if (isFieldEmpty(licensePlateInputLayout, "License Plate") ||
                isFieldEmpty(chassisNumberInputLayout, "Chassis Number") ||
                isFieldEmpty(seatsInputLayout, "Seats") ||
                isFieldEmpty(modelNameInputLayout, "Model Name") ||
                isFieldEmpty(mileageInputLayout, "Mileage") ||
                isFieldEmpty(yearInputLayout, "Year") ||
                isFieldEmpty(colorInputLayout, "Color")) {
            return; // Stop form submission if any field is empty
        }

        // Check file fields for selection
        String[] fileFields = {"Owner Profile", "Main Image", "Front Image", "Back Image", "Left Image", "Right Image", "Interior Image"};
        TextView[] textViews = {tvOwnerProfile, tvMainImage, tvFrontImage, tvBackImage, tvLeftImage, tvRightImage, tvInteriorImage};

        for (int i = 0; i < fileFields.length; i++) {
            if (TextUtils.isEmpty(textViews[i].getText())) {
                Toast.makeText(this, "Please select " + fileFields[i], Toast.LENGTH_SHORT).show();
                return; // Stop form submission if this file is not selected
            }
        }

        // If all validations pass, submit the form
        Toast.makeText(this, "Form Submitted Successfully!", Toast.LENGTH_SHORT).show();
        // Submit form logic (e.g., API call)
    }


    // Helper method to check if a field is empty
    private boolean isFieldEmpty(TextInputLayout inputLayout, String fieldName) {
        EditText editText = inputLayout.getEditText(); // Get EditText from TextInputLayout
        if (editText != null && TextUtils.isEmpty(editText.getText().toString().trim())) {
            inputLayout.setError(fieldName + " is required");
            inputLayout.requestFocus();
            return true;
        }else{
            inputLayout.setError(null);
            return false;
        }
    }
}
