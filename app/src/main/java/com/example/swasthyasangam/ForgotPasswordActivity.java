package com.example.swasthyasangam;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText usernameEditText, emailEditText, newPasswordEditText, confirmNewPasswordEditText;
    Button resetPasswordButton;
    Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        usernameEditText = findViewById(R.id.editTextAppname);
        emailEditText = findViewById(R.id.editTextappaddress);
        newPasswordEditText = findViewById(R.id.editTextAppnumber);
        confirmNewPasswordEditText = findViewById(R.id.editTextAppfees);
        resetPasswordButton = findViewById(R.id.buttonBookAppointment);

        TextView tv = findViewById(R.id.textViewOldUser);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
            }
        });

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmNewPassword = confirmNewPasswordEditText.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmNewPassword)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isValidEmail(email)) {
                    // Hash the new password
                    String hashedPassword = hashPassword(newPassword);

                    // Create an instance of the Database class
                    Database database = new Database(ForgotPasswordActivity.this, "SwasthayaSangam", null, 1);

                    // Update the password
                    database.updatePassword(username, hashedPassword);

                    // Inform the user and navigate to the login activity
                    Toast.makeText(ForgotPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid username or email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Handle error appropriately
            return null;
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

}

