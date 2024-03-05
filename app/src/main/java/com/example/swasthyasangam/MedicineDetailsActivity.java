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
public class MedicineDetailsActivity extends AppCompatActivity {
    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button  btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);
        tvPackageName = findViewById(R.id.MPackageName);
        tvTotalCost = findViewById(R.id.textViewMedicinecost);
        edDetails = findViewById(R.id.editTextMTextMultiLine);
        edDetails.setKeyListener(null);
//        btnBack = findViewById(R.id.buttonMBack);
        btnAddToCart = findViewById(R.id.buttonAddToCart);

        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText(intent.getStringExtra("text3")+"/-");


//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MedicineDetailsActivity.this,MedicineActivity.class));
//            }
//        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences =getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username","").toString();
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat(intent.getStringExtra("text3").toString());

                Database database = new Database(getApplicationContext(),"SwasthayaSangam",null,1);

                if(database.checkCart(username,product)==1){
                    Toast.makeText(getApplicationContext(),"Product Already Added",Toast.LENGTH_SHORT).show();
                }else{
                    database.addCart(username,product,price,"Medicine");
                    Toast.makeText(getApplicationContext(),"Record Inserted To Cart",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MedicineDetailsActivity.this,MedicineActivity.class));
                }
            }
        });


    }
}