package com.example.swasthyasangam;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONException;
import org.json.JSONObject;

public class LabTestBookActivity extends AppCompatActivity implements PaymentResultListener {
    EditText edname, edaddress, edpincode, edcontact;
    Button BtnBooking;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);
        edname = findViewById(R.id.LBfullname);
        edaddress = findViewById(R.id.LBaddress);
        edcontact = findViewById(R.id.LBcontactnumber);
        edpincode = findViewById(R.id.LBpincode);
        BtnBooking = findViewById(R.id.ButtonLBbook);
        tv = findViewById(R.id.price);

        Intent it = getIntent();
        String price = it.getStringExtra("price").toString();

        tv.setText(price+"/-");

        BtnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Check if any of the fields are empty
                    if (TextUtils.isEmpty(edname.getText().toString()) ||
                            TextUtils.isEmpty(edaddress.getText().toString()) ||
                            TextUtils.isEmpty(edcontact.getText().toString()) ||
                            TextUtils.isEmpty(edpincode.getText().toString())) {
                        Toast.makeText(LabTestBookActivity.this, "Please fill out all the details", Toast.LENGTH_SHORT).show();
                        return; // Don't proceed further
                    }
                String[] price = getIntent().getStringExtra("price").split(":");
                if (price.length >= 2) {
                    String samount = price[1];
                    int amount = Math.round(Float.parseFloat(samount) * 100);

                    // initialize Razorpay account.
                    Checkout checkout = new Checkout();

                    // set your id as below
                    checkout.setKeyID("rzp_test_UfdnSXSrLGwDgR");

                    // set image
                    checkout.setImage(R.drawable.logo);

                    // initialize json object
                    JSONObject object = new JSONObject();
                    try {
                        // to put name
                        object.put("name", "Swasthya Sangam");

                        // put description
                        object.put("description", "Test payment");

                        // to set theme color
                        object.put("theme.color", "");

                        // put the currency
                        object.put("currency", "INR");

                        // put amount
                        object.put("amount", amount);

                        // put mobile number
                        object.put("prefill.contact", "9967060640");

                        // put email
                        object.put("prefill.email", "chaudharyshyam@gmail.com");

                        // open razorpay to checkout activity
                        checkout.open(LabTestBookActivity.this, object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        // this method is called on payment success.
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();

        Intent it = getIntent();
        String[] price = it.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = it.getStringExtra("date");
        String time = it.getStringExtra("time");

        // Store the data after successful payment
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", " ").toString();
        Database database = new Database(getApplicationContext(), "SwasthayaSangam", null, 1);
        database.addOrder(username, edname.getText().toString(), edaddress.getText().toString(), edcontact.getText().toString(), Integer.parseInt(edpincode.getText().toString()), date.toString(), time.toString(), Float.parseFloat(price[1].toString()), "lab");
        database.removeCart(username, "lab");
        Toast.makeText(getApplicationContext(), "Your Booking is done Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LabTestBookActivity.this, HomeActivity.class));
    }

    @Override
    public void onPaymentError(int i, String s) {
        // on payment failed.
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }
}



