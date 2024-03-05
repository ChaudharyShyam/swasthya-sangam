package com.example.swasthyasangam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    private int[] images = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
    private int currentIndex = 0;
    private Handler handler;
    private final int SWITCH_INTERVAL = 3000; // Switch interval in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_dashboard) {
                startActivity(new Intent(getApplicationContext(), OrderDetailsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_notifications) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();

//        CardView exit = findViewById(R.id.Logout);
//        exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.clear();
//                editor.apply();
//                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//            }
//        });

        CardView doctor = findViewById(R.id.FindDoctor);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DoctorActivity.class));
            }
        });

        CardView LabTest = findViewById(R.id.cardLabTest);
        LabTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LabTestActivity.class));
            }
        });

        CardView Articals = findViewById(R.id.HealthArtical);
        Articals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, HospitalMapsActivity.class));
            }
        });

//        CardView OrderDetail = findViewById(R.id.OrderDetail);
//        OrderDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, OrderDetailsActivity.class));
//            }
//        });


        CardView Medicine = findViewById(R.id.cardBuyMedicine);
        Medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MedicineActivity.class));
            }
        });



        // Image Switcher Initialization
        imageSwitcher = findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });

        // Handle Image Switching
        imageSwitcher.setImageResource(images[currentIndex]);
        imageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImage();
            }
        });

        // Automatic Image Switching
        handler = new Handler();
        handler.postDelayed(imageSwitchRunnable, SWITCH_INTERVAL);
    }

    private void switchImage() {
        currentIndex = (currentIndex + 1) % images.length;
        imageSwitcher.setImageResource(images[currentIndex]);

        // Restart the timer for the next automatic switch
        handler.removeCallbacks(imageSwitchRunnable);
        handler.postDelayed(imageSwitchRunnable, SWITCH_INTERVAL);
    }

    private Runnable imageSwitchRunnable = new Runnable() {
        @Override
        public void run() {
            switchImage();
        }
    };
}
