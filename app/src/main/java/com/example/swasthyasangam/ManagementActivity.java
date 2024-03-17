package com.example.swasthyasangam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        CardView userData = findViewById(R.id.cardLabTest);
        userData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementActivity.this,UserDataActivity.class));
            }
        });


        CardView ordersData = findViewById(R.id.cardBuyMedicine);
        ordersData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementActivity.this,OrdersDataActivity.class));
            }
        });

        CardView profileData = findViewById(R.id.FindDoctor);
        profileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementActivity.this,ProfileDataActivity.class));
            }
        });

        CardView problems = findViewById(R.id.HealthArtical);
        problems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagementActivity.this,ProblemsDataActivity.class));
            }
        });
    }
}