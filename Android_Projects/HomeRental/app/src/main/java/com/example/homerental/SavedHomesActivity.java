package com.example.homerental;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SavedHomesActivity extends AppCompatActivity {

    private ImageView homeImage;
    private TextView homeTitle, homePrice, checkinDate, checkoutDate;
    private Button deleteButton;

    private int bookingId;
    private int homeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_homes);

        homeImage = findViewById(R.id.homeImage);
        homeTitle = findViewById(R.id.homeTitle);
        homePrice = findViewById(R.id.homePrice);
        checkinDate = findViewById(R.id.checkinDate);
        checkoutDate = findViewById(R.id.checkoutDate);
        deleteButton = findViewById(R.id.deleteButton);

        bookingId = getIntent().getIntExtra("BOOKING_ID", -1);

        if (bookingId != -1) {
            loadBookingDetails();
        } else {
            Toast.makeText(this, "Booking not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBooking();
            }
        });
    }

    private void loadBookingDetails() {
        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor bookingCursor = db.query(
                "Booking",
                null,
                "_id = ?",
                new String[]{String.valueOf(bookingId)},
                null,
                null,
                null
        );

        if (bookingCursor != null && bookingCursor.moveToFirst()) {
            homeId = bookingCursor.getInt(bookingCursor.getColumnIndexOrThrow("HOMEID"));
            long checkin = bookingCursor.getLong(bookingCursor.getColumnIndexOrThrow("CHECKIN"));
            long checkout = bookingCursor.getLong(bookingCursor.getColumnIndexOrThrow("CHECKOUT"));

            checkinDate.setText("Check-in Date: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(checkin * 1000)));
            checkoutDate.setText("Check-out Date: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(checkout * 1000)));

            bookingCursor.close();
            loadHomeDetails();
        }
    }

    private void loadHomeDetails() {
        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor homeCursor = db.query(
                "Homes",
                null,
                "_id = ?",
                new String[]{String.valueOf(homeId)},
                null,
                null,
                null
        );

        if (homeCursor != null && homeCursor.moveToFirst()) {
            String title = homeCursor.getString(homeCursor.getColumnIndexOrThrow("TITLE"));
            String price = homeCursor.getString(homeCursor.getColumnIndexOrThrow("PRICE"));
            String imageUri = homeCursor.getString(homeCursor.getColumnIndexOrThrow("IMAGE_RESOURCE_ID"));

            homeTitle.setText(title);
            homePrice.setText("Price: " + price);
            homeImage.setImageURI(Uri.parse(imageUri));

            homeCursor.close();
        }
    }

    private void deleteBooking() {
        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = db.delete("Booking", "_id = ?", new String[]{String.valueOf(bookingId)});
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Booking deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error deleting booking", Toast.LENGTH_SHORT).show();
        }
    }
}
