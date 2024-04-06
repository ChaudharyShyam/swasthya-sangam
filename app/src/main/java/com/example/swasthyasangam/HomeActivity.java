package com.example.swasthyasangam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    private int[] images = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
    private int currentIndex = 0;
    private Handler handler;
    private final int SWITCH_INTERVAL = 3000; // Switch interval in milliseconds

    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private ArrayList<HashMap<String, String>> orderDetailsList;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Exit the app
                    }
                })
                .setNegativeButton("No", null) // Do nothing if "No" is clicked
                .show();
    }

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

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
// Set layout manager for horizontal scrolling
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Database database = new Database(getApplicationContext(), "SwasthayaSangam", null, 1);

// Fetch data from the database
        ArrayList<String> dbData = database.getUpcomingAppointments(username);

        if (dbData != null && !dbData.isEmpty()) {
            // Process data
            ArrayList<HashMap<String, String>> orderDetailsList = new ArrayList<>();
            for (String appointment : dbData) {
                String[] appointmentDetails = appointment.split("\\$");
                if (appointmentDetails.length >= 8) { // Ensure array length to prevent IndexOutOfBoundsException
                    HashMap<String, String> item = new HashMap<>();
                    item.put("fullname", appointmentDetails[1]);
                    item.put("address", appointmentDetails[2]);
                    item.put("date", appointmentDetails[5]);
                    item.put("time", appointmentDetails[6]);
                    item.put("amount", appointmentDetails[7]);
                    item.put("contactno", appointmentDetails[3]);
                    orderDetailsList.add(item);
                } else {
                    Log.e("DataError", "Invalid appointment details: " + appointment);
                }
            }

            // Set up adapter and bind it to the RecyclerView
            HomeAdapter adapter = new HomeAdapter(this, orderDetailsList);
            recyclerView.setAdapter(adapter);
        } else {
            // Handle case where no upcoming appointments are found
            Log.d("NoData", "No upcoming appointments found for user: " + username);
            // Optionally, display a message to the user indicating no appointments found
        }

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
