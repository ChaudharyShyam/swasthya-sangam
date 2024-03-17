package com.example.swasthyasangam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private String[][] packages =
            {
                    {"Full Body checkup","","","","1499"},
                    {"Blood Glucose Fasting","","","","829"},
                    {"COVID-19 Antibody","","","","450"},
                    {"Thyroid Check","","","","600"},
                    {"Immunity Check","","","","300"},
                    {"Allergy Test","","","","750"},
                    {"Heart Health Checkup","","","","1200"},
                    {"Vitamin D Test","","","","350"},
                    {"STD Panel","","","","900"},
                    {"Cancer Screening","","","","2000"}
            };
    private String[] package_details =
            {
                    "Blood Glucose Fasting\n"+
                            "Complete Hemogram\n"+
                            "HbA1c\n"+
                            "Kidney Function Test\n"+
                            "LDH Lactate Dehydrogenase , Serum\n"+
                            "Lipid Profile\n"+
                            "Liver Function Test",
                    "Blood Glucose Fasting",
                    "COVID-19 Antibody - IgG",
                    "Thyroid Profile-Total(T3 , T4 & TSH Ultra-Sensitive)",
                    "Complete Hemogram\n" +
                            "CRP (C Reactive Protein) Quantitative ,Serum\n" +
                            "Iron Studies\n" +
                            "Kidney Functin Test\n" +
                            "Vitamin D Total-25 Hydroxy\n" +
                            "Liver Function Test\n" +
                            "Lipid Profile",
                    "An allergy test helps identify specific allergens that trigger allergic reactions in an individual.\n"+
                            "Common allergens tested for include pollen, dust mites, pet dander, mold, certain foods (like nuts, eggs, shellfish), and various medications.",
                    "A heart health checkup is designed to assess the overall cardiovascular health of an individual and identify any risk factors for heart disease.",
                    "This package includes a test to measure the level of vitamin D in the blood.\n"+
                            "It helps in assessing the individual's vitamin D status, which is essential for bone health, immune function, and overall well-being.",
                    "This package comprises a panel of tests for sexually transmitted diseases (STDs) such as HIV, syphilis, gonorrhea, chlamydia, etc.\n"+
                            "It is recommended for individuals who are sexually active or have had unprotected sexual encounters to screen for any infections and ensure early detection and treatment.",
                    "This package is designed for cancer screening and may include various tests depending on the individual's age, gender, and risk factors.\n"+
                            "Common tests included in cancer screening packages are screenings for breast cancer, cervical cancer, prostate cancer, colon cancer, etc."

            };

    HashMap<String,String> item;
    ArrayList<HashMap<String, String>> list;
//    ArrayList list;
    SimpleAdapter sa;
    Button btnGoToCart;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        btnGoToCart =findViewById(R.id.buttonBAback);
        listView =findViewById(R.id.CartList);


        list = new ArrayList();
        for (int i=0; i<packages.length;i++){
            item = new HashMap<String,String>();
            item.put("line1",packages[i][0]);
            item.put("line2",packages[i][1]);
            item.put("line3",packages[i][2]);
            item.put("line4",packages[i][3]);
            item.put("line5", "Total Cost:" +packages[i][4]+"/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multi_line,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e,});
        ListView lst=findViewById(R.id.CartList);
        lst.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent it = new Intent(LabTestActivity.this,LabTestDetailActivity.class);
                it.putExtra("text1",packages[i][0]);
                it.putExtra("text2",package_details[i]);
                it.putExtra("text3",packages[i][4]);
                startActivity(it);
            }
        });

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                String otype = "lab"; // Assuming "lab" is the correct value for otype

                if (isCartEmpty(username, otype)) {
                    Toast.makeText(LabTestActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(LabTestActivity.this, LabCartActivity.class));
                }
            }
        });
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    private boolean isCartEmpty(String username, String otype) {
        Database database = new Database(getApplicationContext(), "SwasthayaSangam", null, 1);
        return database.isCartEmpty(username, otype);
    }

    // Override onQueryTextSubmit method (can be left empty)
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    // Implement onQueryTextChange method to filter the list
    @Override
    public boolean onQueryTextChange(String newText) {
        // Filter the list based on the search query
        filter(newText);
        return true;
    }

    private void filter(String query) {
        ArrayList<HashMap<String, String>> filteredList = new ArrayList<>();
        for (HashMap<String, String> item : list) {
            // Perform a case-insensitive search in multiple fields
            if (item.get("line1").toLowerCase().contains(query.toLowerCase()) ||
                    item.get("line2").toLowerCase().contains(query.toLowerCase()) ||
                    item.get("line3").toLowerCase().contains(query.toLowerCase()) ||
                    item.get("line4").toLowerCase().contains(query.toLowerCase()) ||
                    item.get("line5").toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        // Update the adapter with the filtered list
        sa = new SimpleAdapter(this, filteredList, R.layout.multi_line,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(sa);
    }


}