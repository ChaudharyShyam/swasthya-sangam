package com.example.swasthyasangam;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class OrderDetailsActivity extends AppCompatActivity {
    private String[][] order_details = {};
    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    ListView lst;

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
        setContentView(R.layout.activity_order_details);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        lst = findViewById(R.id.ODListView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_dashboard) {
                // Handle search action
                return true;
            } else if (item.getItemId() == R.id.navigation_notifications) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        Database database = new Database(getApplicationContext(), "SwasthayaSangam", null, 1);
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", " ").toString();
        ArrayList dbData = database.getOrderData(username);
        Collections.reverse(dbData);

        order_details = new String[dbData.size()][];
        for (int i = 0; i < order_details.length; i++) {
            order_details[i] = new String[5];
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            order_details[i][0] = strData[0];
            order_details[i][1] = strData[1];
            if (strData[2].compareTo("medicine") == 0) {
                order_details[i][3] = "Del:" + strData[4];
            } else {
                order_details[i][3] = "Del:" + strData[4] + " " + strData[5];
            }
            order_details[i][2] = "Rs." + strData[6];
            order_details[i][4] = strData[7];
        }

        list = new ArrayList<>();
        for (int i = 0; i < order_details.length; i++) {
            item = new HashMap<>();
            item.put("line1", order_details[i][0]);
            item.put("line2", order_details[i][1]);
            item.put("line3", order_details[i][2]);
            item.put("line4", order_details[i][3]);
            item.put("line5", order_details[i][4]);
            list.add(item);
        }

        OrderDetailsAdapter adapter = new OrderDetailsAdapter(this, list);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toggle visibility of expandable layout
                View expandableLayout = view.findViewById(R.id.expandableLayout);
                if (expandableLayout.getVisibility() == View.VISIBLE) {
                    expandableLayout.setVisibility(View.GONE);
                } else {
                    expandableLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
