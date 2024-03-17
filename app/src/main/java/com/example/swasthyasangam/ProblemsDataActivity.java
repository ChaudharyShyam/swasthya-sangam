package com.example.swasthyasangam;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ProblemsDataActivity extends AppCompatActivity {

    private ListView listView;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems_data);

        listView = findViewById(R.id.ODListView);
        database = new Database(this, "SwasthayaSangam", null, 1); // Change with your database name

        // Fetch problems data from the database
        ArrayList<String> problemsData = database.getProblemsData();

        // Create custom adapter and set it to the ListView
        ProblemsDataAdapter adapter = new ProblemsDataAdapter(this, problemsData);
        listView.setAdapter(adapter);
    }
}

