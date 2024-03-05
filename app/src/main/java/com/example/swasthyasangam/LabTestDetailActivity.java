package com.example.swasthyasangam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LabTestDetailActivity extends AppCompatActivity {
    TextView tvPackageName, tvTotalCost;
    EditText edPackageDetails;
    Button btnAddToCart; //,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_detail);

        tvPackageName = findViewById(R.id.LDPackageName);
        tvTotalCost = findViewById(R.id.textViewPackagecost);
        edPackageDetails = findViewById(R.id.editTextLDTextMultiLine);
//        btnBack = findViewById(R.id.buttonLDBack);
        btnAddToCart = findViewById(R.id.buttonAddToCart);

        edPackageDetails.setKeyListener(null);

        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edPackageDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost"+intent.getStringExtra("text3")+"/-");

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LabTestDetailActivity.this,LabTestActivity.class));
//            }
//        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username","").toString();
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat(intent.getStringExtra("text3").toString());

                Database database = new Database(getApplicationContext(),"SwasthayaSangam",null,1);

                if(database.checkCart(username,product)==1){
                    Toast.makeText(getApplicationContext(),"Product Already Added",Toast.LENGTH_SHORT).show();
                }else{
                    database.addCart(username,product,price,"lab");
                    Toast.makeText(getApplicationContext(),"Record Inserted To Cart",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LabTestDetailActivity.this,LabTestActivity.class));
                    finish();
                }

            }
        });
    }
}