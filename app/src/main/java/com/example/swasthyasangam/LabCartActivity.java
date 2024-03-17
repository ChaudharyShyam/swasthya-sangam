package com.example.swasthyasangam;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class LabCartActivity extends AppCompatActivity {
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button DateButton,TimeButton,btnCheckOut; //,btnBack;
    private String[][] packages = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_cart);

        DateButton = findViewById(R.id.buttonCartDate);
        TimeButton = findViewById(R.id.buttonCartTime);
        btnCheckOut = findViewById(R.id.buttonCartCheckOut);
//        btnBack = findViewById(R.id.buttonCartback);
        tvTotal = findViewById(R.id.textViewCartCost);
        lst=findViewById(R.id.listViewCartList);




        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LabCartActivity.this);
                builder.setMessage("Do you want to remove this package from the cart?");
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Remove the selected item from the cart
                        removeItemFromCart(position);

                        SharedPreferences sharedPreferences =getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        String username = sharedPreferences.getString("username","").toString();
                        Database database = new Database(getApplicationContext(),"SwasthayaSangam",null,1);

                        if (database.isCartEmpty(username, "lab")) {
                            // Cart is empty, redirect to the previous activity
                            startActivity(new Intent(LabCartActivity.this, LabTestActivity.class));
                            finish(); // Finish this activity to prevent going back to it using the back button
                            return; // Return to avoid further execution of the code
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
            }
        });

        SharedPreferences sharedPreferences =getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","").toString();
        Database database = new Database(getApplicationContext(),"SwasthayaSangam",null,1);

        float totalAmount = 0;
        ArrayList dbData = database.getCartData(username,"lab");
        //Toast.makeText(getApplicationContext(),""+dbData,Toast.LENGTH_LONG).show();

        packages = new String[dbData.size()][];
        for (int i = 0; i < packages.length;i++){
            packages[i] = new String[5];
        }
        for (int i= 0; i<dbData.size();i++){
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0]= strData[0];
            packages[i][4]= "Cost :"+strData[1]+"/-";
            totalAmount = totalAmount + Float.parseFloat(strData[1]);
        }
        tvTotal.setText("Total Cost :"+totalAmount);

        list = new ArrayList();
        for (int i= 0;i<packages.length;i++){
            item = new HashMap<String,String>();
            item.put("line1",packages[i][0]);
            item.put("line2",packages[i][1]);
            item.put("line3",packages[i][2]);
            item.put("line4",packages[i][3]);
            item.put("line5",packages[i][4]);
            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multi_line,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e,});
        lst.setAdapter(sa);


//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LabCartActivity.this,LabTestActivity.class));
//            }
//        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LabCartActivity.this,LabTestBookActivity.class);
                it.putExtra("price",tvTotal.getText());
                it.putExtra("date",DateButton.getText());
                it.putExtra("time",TimeButton.getText());
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

        initTimePicker();
        TimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

    }

    private void removeItemFromCart(int position) {
        HashMap<String, String> selectedItem = (HashMap<String, String>) list.get(position);
        String productName = selectedItem.get("line1"); // Assuming line1 contains the product name

        // Remove the item from the cart database
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        Database database = new Database(getApplicationContext(), "SwasthayaSangam", null, 1);
        database.removePackage(username, productName);

        // Remove the item from the list
        list.remove(position);
        sa.notifyDataSetChanged();

        // Update total cost
        updateTotalCost();
    }

    // Method to update total cost after removing an item
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

    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int i, int i1) {
                TimeButton.setText(i+":"+i1);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        timePickerDialog = new TimePickerDialog(this, style,timeSetListener,hrs,mins,true);
    }
}