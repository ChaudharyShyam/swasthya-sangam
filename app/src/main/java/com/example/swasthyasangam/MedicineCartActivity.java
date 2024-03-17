package com.example.swasthyasangam;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MedicineCartActivity extends AppCompatActivity {
    HashMap<String,String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;
    private DatePickerDialog datePickerDialog;
    private Button DateButton,btnCheckOut;
    private String[][] packages = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_cart);

        DateButton = findViewById(R.id.buttonMCartDate);
        btnCheckOut = findViewById(R.id.buttonMCartCheckOut);
        tvTotal = findViewById(R.id.textViewMCartCost);
        lst=findViewById(R.id.listViewMCartList);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        Database database = new Database(getApplicationContext(),"SwasthayaSangam",null,1);

        float totalAmount = 0;
        ArrayList<String> dbData = database.getCartData(username, "Medicine");

        packages = new String[dbData.size()][];
        for (int i = 0; i < packages.length; i++){
            packages[i] = new String[5];
        }
        for (int i = 0; i < dbData.size(); i++){
            String arrData = dbData.get(i);
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0] = strData[0];
            packages[i][4] = "Cost: " + strData[1] + "/-";
            totalAmount += Float.parseFloat(strData[1]);
        }
        tvTotal.setText("Total Cost: " + totalAmount);

        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++){
            item = new HashMap<>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", packages[i][4]);
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_line,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MedicineCartActivity.this, MedicineBookActivity.class);
                it.putExtra("price", tvTotal.getText());
                it.putExtra("date", DateButton.getText());
                startActivity(it);
            }
        });

        initDatePicker();
        DateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MedicineCartActivity.this);
                builder.setMessage("Do you want to remove this package from the cart?");
                builder.setPositiveButton("Remove", (dialog, which) -> {
                    removeItemFromCart(position);
                    SharedPreferences sharedPreferences =getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString("username","").toString();
                    Database database = new Database(getApplicationContext(),"SwasthayaSangam",null,1);

                    if (database.isCartEmpty(username, "Medicine")) {
                        // Cart is empty, redirect to the previous activity
                        startActivity(new Intent(MedicineCartActivity.this, MedicineActivity.class));
                        finish(); // Finish this activity to prevent going back to it using the back button
                        return; // Return to avoid further execution of the code
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
            }
        });
    }

    private void removeItemFromCart(int position) {
        HashMap<String, String> selectedItem = list.get(position);
        String productName = selectedItem.get("line1");

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        Database database = new Database(getApplicationContext(), "SwasthayaSangam", null, 1);
        database.removePackage(username, productName);

        list.remove(position);
        sa.notifyDataSetChanged();

        updateTotalCost();
    }

    private void updateTotalCost() {
        float totalAmount = 0;
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> item = (HashMap<String, String>) list.get(i);
            String costString = item.get("line5").substring(6); // Extracting cost from "Cost :..."
            try {
                totalAmount += Float.parseFloat(costString);
            } catch (NumberFormatException e) {
                // Handle any potential parsing errors (e.g., if the cost string is not in the expected format)
                e.printStackTrace();
            }
        }
        tvTotal.setText("Total Cost: " + totalAmount);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int i, int i1, int i2) {
                i1=i1+1;
                DateButton.setText(i2+"/"+i1+"/"+i);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,date);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+86400000);
    }
}
