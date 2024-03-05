package com.example.swasthyasangam;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
public class RegisterActivity extends AppCompatActivity {
    EditText edUsername, edEmail, edPassword, edConfirm;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edUsername = findViewById(R.id.editTextAppname);
        edEmail = findViewById(R.id.editTextappaddress);
        edPassword = findViewById(R.id.editTextAppnumber);
        edConfirm = findViewById(R.id.editTextAppfees);
        btn = findViewById(R.id.buttonBookAppointment);
        tv = findViewById(R.id.textViewOldUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String confirm = edConfirm.getText().toString();
                Database database = new Database(getApplicationContext(), "SwasthayaSangam", null, 1);

                if (username.length() == 0 || email.length() == 0 || password.length() == 0 || confirm.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill All the details", Toast.LENGTH_SHORT).show();
                } else {
                    // Add email validation using regex
                    if (!isValidEmail(email)) {
                        Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    } else if (password.compareTo(confirm) == 0) {
                        if (isValid(password)) {
                            registerUser(database, username, email, password);
                        } else {
                            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters,containing letters,digits,Uppercase and special symbols", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void registerUser(Database database, String username, String email, String password) {
        // Check if username or email already exist in the database
        if (isUsernameExists(database, username)) {
            Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmailExists(database, email)) {
            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hash the password
        String hashedPassword = hashPassword(password);

        database.register(username, email, hashedPassword);
        Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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
    private boolean isUsernameExists(Database database, String username) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    private boolean isEmailExists(Database database, String email) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public static boolean isValid(String pass) {
        int f1 = 0, f2 = 0, f3 = 0, f4 = 0; // Added f4 for uppercase letter check
        if (pass.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < pass.length(); p++) {
                if (Character.isLetter(pass.charAt(p))) {
                    f1 = 1;
                    if (Character.isUpperCase(pass.charAt(p))) { // Check for uppercase letter
                        f4 = 1;
                    }
                }
            }
            for (int r = 0; r < pass.length(); r++) {
                if (Character.isDigit(pass.charAt(r))) {
                    f2 = 1;
                }
            }
            for (int s = 0; s < pass.length(); s++) {
                char c = pass.charAt(s);
                if (c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;
                }
            }
            return f1 == 1 && f2 == 1 && f3 == 1 && f4 == 1; // Added f4 check
        }
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}