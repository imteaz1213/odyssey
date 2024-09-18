package com.example.odyssey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CarDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mainCarImage;
    private LinearLayout carDetailsTabContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        setupHeader();
        setupMainImage();
        setupImageSlider();
        setupTabButtons();
    }

    private void setupHeader() {
        Button bottomBtn = findViewById(R.id.bottomBtn);
        bottomBtn.setOnClickListener(this);
    }

    private void setupMainImage() {
        mainCarImage = findViewById(R.id.main_car_image);
        mainCarImage.setImageResource(R.drawable.car1);
    }

    private void setupImageSlider() {
        int[] imageIds = {
                R.drawable.car1,
                R.drawable.car2,
                R.drawable.car3,
                R.drawable.car4,
                R.drawable.car5
        };

        LinearLayout imageContainer = findViewById(R.id.horizontal_images_container);
        for (int imageId : imageIds) {
            ImageView horizontalImage = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    300,
                    150
            );
            params.setMargins(10, 0, 10, 0);
            horizontalImage.setLayoutParams(params);
            horizontalImage.setImageResource(imageId);

            horizontalImage.setOnClickListener(view -> mainCarImage.setImageResource(imageId));

            imageContainer.addView(horizontalImage);
        }
    }

    private void setupTabButtons() {
        Button reviewBtn = findViewById(R.id.reviewBtn);
        Button aboutBtn = findViewById(R.id.aboutBtn);
        carDetailsTabContainer = findViewById(R.id.carDetailsTabContainer);
        updateCarDetailsContent(R.layout.about_car);
        reviewBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.reviewBtn) {
            updateCarDetailsContent(R.layout.car_review);
        } else if (view.getId() == R.id.aboutBtn) {
            updateCarDetailsContent(R.layout.about_car);
        }
    }

    private void updateCarDetailsContent(int layoutId) {
        carDetailsTabContainer.removeAllViews();
        View aboutCarView = getLayoutInflater().inflate(layoutId, carDetailsTabContainer, false);
        carDetailsTabContainer.addView(aboutCarView);
    }
}
