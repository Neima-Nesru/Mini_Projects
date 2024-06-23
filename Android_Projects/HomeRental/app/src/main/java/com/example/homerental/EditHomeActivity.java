package com.example.homerental;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditHomeActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etPrice, etLocation;

    private String homeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homes);

        etTitle = findViewById(R.id.eTTitle);
        etDescription = findViewById(R.id.eTDescription);
        etPrice = findViewById(R.id.eTPrice);
        etLocation = findViewById(R.id.eTLocation);

        // Retrieve homeId from intent extras
        homeId = getIntent().getStringExtra("homeId");

        // Load existing home details for editing
        loadHomeDetails();
    }

    private void loadHomeDetails() {
        // Query database to load existing home details
        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Assuming your table structure, modify as per your actual implementation
        Cursor cursor = db.query(
                "Homes",
                new String[]{"TITLE", "DESCRIPTION", "PRICE", "LOCATION"},
                "_id = ?",
                new String[]{homeId},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("PRICE"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("LOCATION"));

            // Populate EditText fields with existing data
            etTitle.setText(title);
            etDescription.setText(description);
            etPrice.setText(String.valueOf(price));
            etLocation.setText(location);

            cursor.close();
        }
    }

    private void saveHomeChanges() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        double price = Double.parseDouble(etPrice.getText().toString().trim());
        String location = etLocation.getText().toString().trim();

        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TITLE", title);
        values.put("DESCRIPTION", description);
        values.put("PRICE", price);
        values.put("LOCATION", location);

        int rowsUpdated = db.update("Homes", values, "_id = ?", new String[]{homeId});

        if (rowsUpdated > 0) {
            Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
            // Optionally, navigate back or perform any cleanup
            finish(); // Finish activity after saving changes
        } else {
            Toast.makeText(this, "Failed to save changes", Toast.LENGTH_SHORT).show();
        }
    }
}
