package com.example.swasthyasangam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.ByteArrayOutputStream;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ProfileActivity extends AppCompatActivity {

    ImageView profilepic;
    TextView tv;
    CardView update , reportProblem, FAQ;
    SharedPreferences sharedPreferences;
    String username;
    Database database;
    private static final int PICK_IMAGE = 1;
    private static final int TAKE_PHOTO = 2;

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
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_notifications);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Handle bottom navigation item clicks
            if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_dashboard) {
                startActivity(new Intent(getApplicationContext(), OrderDetailsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_notifications) {
                // Already in cart activity
                return true;
            }
            return false;
        });

        initViews();

        // Load profile image from database
        loadProfileImage();

        // Click listener for profile picture
        profilepic.setOnClickListener(v -> {
            // Show dialog with options to choose from gallery, camera, or remove image
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            builder.setTitle("Choose an option")
                    .setItems(new CharSequence[]{"Take Photo", "Choose from Gallery", "Remove Profile Image"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    // Take Photo option selected
                                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(takePhotoIntent, TAKE_PHOTO);
                                    } else {
                                        Toast.makeText(ProfileActivity.this, "No camera app found", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 1:
                                    // Choose from Gallery option selected
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryIntent, PICK_IMAGE);
                                    break;
                                case 2:
                                    // Remove Profile Image option selected
                                    removeProfileImage();
                                    break;
                            }
                        }
                    });
            builder.show();
        });

        reportProblem = findViewById(R.id.ReportProblem);
        FAQ = findViewById(R.id.FAQ);

        if (reportProblem != null) {
            reportProblem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ProfileActivity.this, ReportProblemActivity.class));
                }
            });
        }

        if (FAQ != null) {
            FAQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ProfileActivity.this, FAQActivity.class));
                }
            });
        }

        // Click listener for update button
        update.setOnClickListener(v -> updateProfileImage());

        // Click listener for logout button
        Button exit = findViewById(R.id.buttonLogOut);
        exit.setOnClickListener(v -> logout());
    }

    // Initialize views and variables
    private void initViews() {
        profilepic = findViewById(R.id.ProfileImage);
        tv = findViewById(R.id.TvUsername);
        update = findViewById(R.id.UpdateProfile);
        sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        tv.setText("Hello," +"\uD83D\uDC4B "+ username);
        database = new Database(getApplicationContext(), "SwasthayaSangam", null, 1);
    }
    // Load profile image from database
    private void loadProfileImage() {
        byte[] imageByteArray = database.getProfileImage(username);
        if (imageByteArray != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            profilepic.setImageBitmap(bitmap);
        }
    }
    // Update profile image in the database
    private void updateProfileImage() {
        BitmapDrawable drawable = (BitmapDrawable) profilepic.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Compression format and quality
        byte[] byteArray = stream.toByteArray();
        boolean updated = database.addOrUpdateProfile(username, byteArray);

        if (updated) {
            Toast.makeText(ProfileActivity.this, "Profile image updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ProfileActivity.this, "Failed to update profile image", Toast.LENGTH_SHORT).show();
        }
    }
    // Handle the result of image selection from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE && data != null) {
                // Selected image from gallery
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    profilepic.setImageURI(imageUri);
                } else {
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == TAKE_PHOTO && data != null) {
                // Captured photo from camera
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    if (imageBitmap != null) {
                        profilepic.setImageBitmap(imageBitmap);
                    } else {
                        Toast.makeText(this, "Failed to capture photo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    // Logout user and clear SharedPreferences
    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Clear SharedPreferences and navigate to LoginActivity
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Dismiss the dialog if "No" is clicked
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void removeProfileImage() {
        boolean removed = database.addOrUpdateProfile(username, null); // Pass null to remove the image
        if (removed) {
            // Clear the profile image view or set a default image
            profilepic.setImageResource(R.drawable.profileimage); // Set default image
            Toast.makeText(ProfileActivity.this, "Profile image removed successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ProfileActivity.this, "Failed to remove profile image", Toast.LENGTH_SHORT).show();
        }
    }
}