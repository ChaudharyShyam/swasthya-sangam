package com.example.swasthyasangam;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class UserDataActivity extends AppCompatActivity {

    private ListView listView;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        listView = findViewById(R.id.ODListView);
        database = new Database(this, "SwasthayaSangam", null, 1); // Change with your database name

        // Fetch user data from the database
        ArrayList<String> userData = database.getUserData();

        // Create custom adapter and set it to the ListView
        UserListAdapter adapter = new UserListAdapter(this, userData);
        listView.setAdapter(adapter);
    }
}
