package com.example.odyssey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.odyssey.adaptars.CarDetailsTabAdaptar;
import com.example.odyssey.fragments.CarDetailsAboutFragment;
import com.example.odyssey.fragments.CarDetailsReviewFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CarDetailsActivity extends AppCompatActivity {

    private ImageView mainCarImage;
    private Button bookNowButton;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private CarDetailsTabAdaptar tabAdapter;
    private LinearLayout imageContainer;
    private ImageView horizontalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        mainCarImage = findViewById(R.id.main_car_image);
        mainCarImage.setImageResource(R.drawable.car_image);
        viewPager = findViewById(R.id.view_pager_container);
        tabLayout = findViewById(R.id.tab_container);
        bookNowButton = findViewById(R.id.bottomBtn);
        imageContainer = findViewById(R.id.horizontal_images_container);

        String carId = getIntent().getStringExtra("CAR_ID");
        if (carId != null) {
            System.out.println("Car ID: " + carId);
        } else {
            Toast.makeText(this, "No Car ID received", Toast.LENGTH_SHORT).show();
        }

        bookNowButton.setOnClickListener(v -> {
            startActivity(new Intent(this, BookingDetailsActivity.class));
        });

        setupTabs();
        setupImageSlider();
    }

    private void setupImageSlider() {
        int[] imageIds = {R.drawable.car1, R.drawable.car2, R.drawable.car3, R.drawable.car4, R.drawable.car5};

        for (int imageId : imageIds) {
            horizontalImage = new ImageView(this);
            horizontalImage.setLayoutParams(new LinearLayout.LayoutParams(300, 150));
            horizontalImage.setPadding(10, 0, 10, 0);
            horizontalImage.setImageResource(imageId);
            horizontalImage.setOnClickListener(v -> mainCarImage.setImageResource(imageId));
            imageContainer.addView(horizontalImage);
        }
    }

    private void setupTabs() {
        tabAdapter = new CarDetailsTabAdaptar(this);
        tabAdapter.addFragment(new CarDetailsAboutFragment(), "About");
        tabAdapter.addFragment(new CarDetailsReviewFragment(), "Review");
        viewPager.setAdapter(tabAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(tabAdapter.getPageTitle(position))
        ).attach();
    }

}
