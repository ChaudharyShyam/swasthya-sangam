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

public class MedicineActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private String[][] packages =
            {
                    {"Uprise-D3 1000IU Capsule","","","","50"},
                    {"HealthVit Chromium Picolinate 200mcg Capsule","","","","50"},
                    {"Vutamine B Complex Capsule","","","","50"},
                    {"Inlife Vitamin E wheat Germ Oil Capsule","","","","50"},
                    {"Dolo 650 Tablet","","","","50"},
                    {"Crocin 650 Advance Tablet","","","","50"},
                    {"Strepsiles Medicated Lozenges for Sore Throat","","","","50"},
                    {"Tata Img Calcium + Vitamin D3","","","","50"},
                    {"Feronia -XT Tablet","","","","50"},
                    {"Ibuprofen (200mg Tablet)","","","","50"},
                    {"Cetrizine (10mg Tablet)","","","","50"}
            };
    private String[] package_details=
            {
                    "Bulding and keeping the bones & teeth strong\n"+
                            "Reducing fatigue /stress and muscular pains\n"+
                            "Boosting immunity and increasing resistance against infection ",
                    "Chromium is an essential trace minetral that plays an important role in helping insulin regulate blood glucose.",
                    "provide relife from vitamin B deficiencies\n"+
                            "helps in formation of red blood cells\n"+
                            "Maintaini healthy nervous system",
                    "It permotes healthy as well as skin benefit.\n"+
                            "It helps reduce skin blemish an pigmentation.\n"+
                            "It act as safegaurd the skin from the harsh UVA and UVB sun rays.",
                    "Dolo 650 tablet helps relieve pain and fever by blocking the release of cetain chemical messengers resposible for fever aand pain.",
                    "helps relives fever and bring down a high temperature\n"+
                            "Suitable for people with a heart codition or high blood pressure",
                    "Relives the symptoms of a vacterial throat infection and soothes the recovery process\n"+
                            "Provides a warm and comforting feeling during sore throat",
                    "Reduce the risk of calcium deficiency ,Rickets and Osteoporosis\n"+
                            "Promotes mobility and flexibility of joints",
                    "Helps to reduce the iron deficiency due to chronic blood loss or low intake of iron",
                    " Ibuprofen is a nonsteroidal anti-inflammatory drug (NSAID) that works by reducing hormones that cause inflammation and pain in the body\n"+
                            "Typically taken every 4 to 6 hours as needed for pain relief. Do not exceed the recommended dosage or duration of use.",
                    "Cetrizine is an antihistamine medication that works by blocking the effects of histamine, a natural substance in the body that causes allergy symptoms.\n"+
                            "Typically taken once daily. May cause drowsiness, so use caution when driving or operating machinery."
            };

    HashMap<String,String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    ListView lst;
    Button btnGoToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        lst = findViewById(R.id.MedicineList);
        btnGoToCart = findViewById(R.id.MGotocart);

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                String otype = "Medicine"; // Assuming "lab" is the correct value for otype
                if (isCartEmpty(username, otype)) {
                    Toast.makeText(MedicineActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MedicineActivity.this, MedicineCartActivity.class));
                }
            }
        });

        list = new ArrayList<>();
        for(int i = 0; i < packages.length; i++) {
            item = new HashMap<>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total Cost:" + packages[i][4] + "/-");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list, R.layout.multi_line,
                new String[] {"line1", "line2", "line3", "line4", "line5"},
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent it = new Intent(MedicineActivity.this, MedicineDetailsActivity.class);
                it.putExtra("text1", packages[i][0]);
                it.putExtra("text2", package_details[i]);
                it.putExtra("text3", packages[i][4]);
                startActivity(it);
            }
        });

        // Find the SearchView
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
        sa = new SimpleAdapter(this, filteredList, R.layout.multi_line,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);
    }
}