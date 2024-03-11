package com.example.swasthyasangam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReportProblemActivity extends AppCompatActivity {
    EditText nameEditText, emailEditText, problemEditText;
    Button submitButton;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        problemEditText = findViewById(R.id.problem);
        submitButton = findViewById(R.id.submit);
        database = new Database(this, "SwasthayaSangam", null, 1);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from input fields
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String problem = problemEditText.getText().toString().trim();

                // Retrieve username from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");

                // Insert data into the database
                boolean isInserted = database.insertData(username, name, email, problem);

                // Check if data insertion was successful
                if (isInserted) {
                    // Data inserted successfully
                    Toast.makeText(ReportProblemActivity.this, "Problem reported successfully", Toast.LENGTH_SHORT).show();

                    // Clear input fields
                    nameEditText.setText("");
                    emailEditText.setText("");
                    problemEditText.setText("");

                    // Start ProfileActivity
                    startActivity(new Intent(ReportProblemActivity.this, ProfileActivity.class));
                } else {
                    // Failed to insert data
                    Toast.makeText(ReportProblemActivity.this, "Failed to report problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
