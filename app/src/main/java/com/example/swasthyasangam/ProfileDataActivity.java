package com.example.swasthyasangam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ProfileDataActivity extends AppCompatActivity {

    private ListView listView;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);

        listView = findViewById(R.id.ODListView);
        database = new Database(this, "SwasthayaSangam", null, 1); // Change with your database name

        // Fetch profile data from the database
        ArrayList<String> profileData = database.getProfileData();

        // Create custom adapter and set it to the ListView
        ProfileDataAdapter adapter = new ProfileDataAdapter(this, profileData);
        listView.setAdapter(adapter);
    }
}
