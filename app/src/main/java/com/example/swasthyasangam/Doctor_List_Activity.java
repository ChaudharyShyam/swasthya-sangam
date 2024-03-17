package com.example.swasthyasangam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
public class Doctor_List_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private String[][] doctor_details1 =
            {
                    {"Dr. Liam Anderson", "Hospital Address : Pune", "Exp : 5yrs" , "Mobile No. : 7894561230" , "500"},
                    {"Dr. Mia Brown", "Hospital Address : Nashik", "Exp : 3yrs" , "Mobile No. : 7894561230" , "300"},
                    {"Dr. William Morgan", "Hospital Address : Mumbai", "Exp : 2yrs" , "Mobile No. : 7894561230" , "700"},
                    {"Dr. Lucas Dixon", "Hospital Address : Kolhapur", "Exp : 4yrs" , "Mobile No. : 7894561230" , "600"},
                    {"Dr. Oliver Foster", "Hospital Address : Pune", "Exp : 8yrs" , "Mobile No. : 7894561230" , "350"},
                    {"Dr. Noah Ramirez", "Hospital Address : Thane", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Brijesh Patil", "Hospital Address : Nagpur", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Ethan Carter", "Hospital Address : Bhiwandi", "Exp : 15yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Alexander Hayes", "Hospital Address : Jalgoan", "Exp : 15yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. James Bennett", "Hospital Address : Amravati", "Exp : 15yrs" , "Mobile No. : 7894561230" , "800"}
            };
    private String[][] doctor_details2 =
            {
                    {"Dr. Henry Sullivan", "Hospital Address : Amravati", "Exp : 5yrs" , "Mobile No. : 7894561230" , "500"},
                    {"Dr. Samuel Mitchell", "Hospital Address : Jalgoan", "Exp : 3yrs" , "Mobile No. : 7894561230" , "300"},
                    {"Dr. Andrew Bryant", "Hospital Address : Mumbai", "Exp : 2yrs" , "Mobile No. : 7894561230" , "700"},
                    {"Dr. Benjamin Powell", "Hospital Address : Kolhapur", "Exp : 4yrs" , "Mobile No. : 7894561230" , "600"},
                    {"Dr. Joseph Harrison", "Hospital Address : Pune", "Exp : 8yrs" , "Mobile No. : 7894561230" , "350"},
                    {"Dr. David Turner", "Hospital Address : Bhiwandi", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Michael Reynolds", "Hospital Address : Pune", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Jacob Ward", "Hospital Address : Nagpur", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Matthew Peterson", "Hospital Address : Jalgoan", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Daniel Cooper", "Hospital Address : Amravati", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"}
            };
    private String[][] doctor_details3 =
            {
                    {"Dr. Benjamin Powell", "Hospital Address : Amravati", "Exp : 5yrs" , "Mobile No. : 7894561230" , "500"},
                    {"Dr. Samuel Mitchell", "Hospital Address : Jalgoan", "Exp : 3yrs" , "Mobile No. : 7894561230" , "300"},
                    {"Dr. Andrew Bryant", "Hospital Address : Mumbai", "Exp : 2yrs" , "Mobile No. : 7894561230" , "700"},
                    {"Dr. William Morgan", "Hospital Address : Kolhapur", "Exp : 4yrs" , "Mobile No. : 7894561230" , "600"},
                    {"Dr. Joseph Harrison", "Hospital Address : Pune", "Exp : 8yrs" , "Mobile No. : 7894561230" , "350"},
                    {"Dr. David Turner", "Hospital Address : Bhiwandi", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Michael Reynolds", "Hospital Address : Pune", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Jacob Ward", "Hospital Address : Nagpur", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Matthew Peterson", "Hospital Address : Jalgoan", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Daniel Cooper", "Hospital Address : Amravati", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"}
            };
    private String[][] doctor_details4 =
            {
                    {"Dr. Benjamin Powell", "Hospital Address : Amravati", "Exp : 5yrs" , "Mobile No. : 7894561230" , "500"},
                    {"Dr. Samuel Mitchell", "Hospital Address : Jalgoan", "Exp : 3yrs" , "Mobile No. : 7894561230" , "300"},
                    {"Dr. Andrew Bryant", "Hospital Address : Mumbai", "Exp : 2yrs" , "Mobile No. : 7894561230" , "700"},
                    {"Dr. William Morgan", "Hospital Address : Kolhapur", "Exp : 4yrs" , "Mobile No. : 7894561230" , "600"},
                    {"Dr. Joseph Harrison", "Hospital Address : Pune", "Exp : 8yrs" , "Mobile No. : 7894561230" , "350"},
                    {"Dr. David Turner", "Hospital Address : Bhiwandi", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Michael Reynolds", "Hospital Address : Pune", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Jacob Ward", "Hospital Address : Nagpur", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Matthew Peterson", "Hospital Address : Jalgoan", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Daniel Cooper", "Hospital Address : Amravati", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"}
            };
    private String[][] doctor_details5 =
            {
                    {"Dr. Benjamin Powell", "Hospital Address : Amravati", "Exp : 5yrs" , "Mobile No. : 7894561230" , "500"},
                    {"Dr. Samuel Mitchell", "Hospital Address : Jalgoan", "Exp : 3yrs" , "Mobile No. : 7894561230" , "300"},
                    {"Dr. Andrew Bryant", "Hospital Address : Mumbai", "Exp : 2yrs" , "Mobile No. : 7894561230" , "700"},
                    {"Dr. William Morgan", "Hospital Address : Kolhapur", "Exp : 4yrs" , "Mobile No. : 7894561230" , "600"},
                    {"Dr. Joseph Harrison", "Hospital Address : Pune", "Exp : 8yrs" , "Mobile No. : 7894561230" , "350"},
                    {"Dr. David Turner", "Hospital Address : Bhiwandi", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Michael Reynolds", "Hospital Address : Pune", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Jacob Ward", "Hospital Address : Nagpur", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Matthew Peterson", "Hospital Address : Jalgoan", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"},
                    {"Dr. Daniel Cooper", "Hospital Address : Amravati", "Exp : 8yrs" , "Mobile No. : 7894561230" , "800"}
            };

    private int[] doctor_images = {R.drawable.doctor1, R.drawable.doctor2, R.drawable.doctor3, R.drawable.doctor4, R.drawable.doctor5, R.drawable.doctor,R.drawable.doctor1, R.drawable.doctor2,R.drawable.doctor3,R.drawable.doctor5};

    TextView tv;
    Button btn;
    String[][] doctor_details = {};
    HashMap<String,String> item;
    ArrayList<HashMap<String, String>> list;

    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        tv = findViewById(R.id.titledoctor);

        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        if(title.compareTo("Family Physician")==0)
            doctor_details = doctor_details1;
        else if(title.compareTo("Dietician")==0)
            doctor_details = doctor_details2;
        else if(title.compareTo("Dentist")==0)
            doctor_details = doctor_details3;
        else if(title.compareTo("Surgeon")==0)
            doctor_details = doctor_details4;
        else
            doctor_details = doctor_details5;

        list = new ArrayList<>();
        for (int i=0; i<doctor_details.length; i++) {
            item = new HashMap<>();
            item.put("line1",doctor_details[i][0]);
            item.put("line2",doctor_details[i][1]);
            item.put("line3",doctor_details[i][2]);
            item.put("line4",doctor_details[i][3]);
            item.put("line5","Cons Fees :"+doctor_details[i][4]+"/-");
            item.put("image", Integer.toString(doctor_images[i])); // Add the image resource ID to the HashMap
            list.add(item);
        }

        sa = new SimpleAdapter(this, list, R.layout.list_item_doctor, new String[]{"line1", "line2", "line3", "line4", "line5", "image"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e, R.id.doctor_image});

        ListView lst = findViewById(R.id.DoctorListView);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                HashMap<String, String> selectedItem = (HashMap<String, String>) parent.getItemAtPosition(i);
                String doctorName = selectedItem.get("line1");
                String hospitalAddress = selectedItem.get("line2");
                String mobileNumber = selectedItem.get("line4");
                String consultationFees = selectedItem.get("line5").replace("Cons Fees :", " ").replace("/-", " ");

                Intent it = new Intent(Doctor_List_Activity.this, BookAppointmentActivity.class);
                it.putExtra("title", title);
                it.putExtra("doctor_name", doctorName);
                it.putExtra("hospital_address", hospitalAddress);
                it.putExtra("mobile_number", mobileNumber);
                it.putExtra("consultation_fees", consultationFees);
                startActivity(it);
            }
        });

        // Find the SearchView
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
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
        sa = new SimpleAdapter(this, filteredList, R.layout.list_item_doctor, new String[]{"line1", "line2", "line3", "line4", "line5", "image"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e, R.id.doctor_image});
        ListView lst = findViewById(R.id.DoctorListView);
        lst.setAdapter(sa);
    }
}