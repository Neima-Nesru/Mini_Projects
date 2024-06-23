package com.example.homerental;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tVProfileUsername, tVEditProfile, checkInTextView, checkOutTextView;
    private ImageView iVProfile, iVHomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        tVProfileUsername = findViewById(R.id.tVProfileUsername);
        tVEditProfile = findViewById(R.id.tVEditProfile);
        checkInTextView = findViewById(R.id.checkInTextView);
        checkOutTextView = findViewById(R.id.checkOutTextView);
        iVProfile = findViewById(R.id.iVProfile);
        iVHomes = findViewById(R.id.iVHomes);

        // Fetch and display user information
        displayUserInfo();
    }

    private void displayUserInfo() {
        // Get user ID from shared preferences
        int userId = getUserId();

        // Fetch user information from the database
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Users", null, "USER_ID = ?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("USERNAME"));
            @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));


            // Fetch other user details as required

            // Set user information to views
            tVProfileUsername.setText(username);
            iVProfile.setImageResource(image);
            // You can set other user details to other views similarly
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private int getUserId() {
        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        return prefs.getInt("USER_ID", -1);
    }

    private SQLiteDatabase getReadableDatabase() {
        // Replace with your actual method to get the readable database
        return null;
    }
}
