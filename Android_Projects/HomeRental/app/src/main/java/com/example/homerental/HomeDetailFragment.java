package com.example.homerental;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class HomeDetailFragment extends Fragment {

    private static final String ARG_HOME_ID = "home_id";

    private TextView tVHomeTitle, tVLocation, tVHomePrice, tVDesc, tVReviews, tVLeaveReview;
    private ImageView iVHomeImageDesc;
    private String homeId;
    private Button bookBtn;

    private Calendar checkinDate;
    private Calendar checkoutDate;

    public HomeDetailFragment() {
        // Required empty public constructor
    }

    public static HomeDetailFragment newInstance(String homeId) {
        HomeDetailFragment fragment = new HomeDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HOME_ID, homeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            homeId = getArguments().getString(ARG_HOME_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_detail, container, false);

        tVHomeTitle = view.findViewById(R.id.tVHomeTitle);
        tVLocation = view.findViewById(R.id.tVLocation);
        tVHomePrice = view.findViewById(R.id.tVHomePrice);
        tVDesc = view.findViewById(R.id.tVDesc);
        tVReviews = view.findViewById(R.id.tVReviews);
        tVLeaveReview = view.findViewById(R.id.tVLeaveReview);
        iVHomeImageDesc = view.findViewById(R.id.iVHomeImageDesc);
        bookBtn = view.findViewById(R.id.bookBtn);

        loadHomeDetails();

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckinDatePicker();
            }
        });

        tVLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewActivity.class);
                intent.putExtra("homeId", homeId);
                startActivity(intent);
            }
        });
        return view;
    }

    private void loadHomeDetails() {
        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "Homes",
                null,
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
            String imageUri = cursor.getString(cursor.getColumnIndexOrThrow("IMAGE_RESOURCE_ID"));

            tVHomeTitle.setText(title);
            tVDesc.setText(description);
            tVHomePrice.setText(String.valueOf(price));
            tVLocation.setText(location);
            iVHomeImageDesc.setImageURI(Uri.parse(imageUri));

            cursor.close();
        }
    }

    private int getUserId() {
        SharedPreferences prefs = getContext().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        return prefs.getInt("USER_ID", -1); // Assuming you store the user ID in shared preferences
    }

    private void showCheckinDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                checkinDate = Calendar.getInstance();
                checkinDate.set(year, monthOfYear, dayOfMonth);
                showCheckoutDatePicker();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Select Check-in Date");
        datePickerDialog.show();
    }

    private void showCheckoutDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                checkoutDate = Calendar.getInstance();
                checkoutDate.set(year, monthOfYear, dayOfMonth);
                saveBooking();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Select Check-out Date");
        datePickerDialog.show();
    }

    private void saveBooking() {
        int checkin = (int) (checkinDate.getTimeInMillis() / 1000);
        int checkout = (int) (checkoutDate.getTimeInMillis() / 1000);
        int userId = getUserId();
        int homeIdInt = Integer.parseInt(homeId);

        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

//        book(db, checkin, checkout, homeIdInt, userId);
        ContentValues bookingValues = new ContentValues();
        bookingValues.put("CHECKIN", checkin);
        bookingValues.put("CHECKOUT", checkout);
        bookingValues.put("HOMEID", homeIdInt);
        bookingValues.put("USERID", userId);

        db.insert("Booking", null, bookingValues);

        Toast.makeText(getContext(), "Booking successful!", Toast.LENGTH_SHORT).show();
    }

    // Context menu creation
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    // Context menu item selection
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.edit) {

            // Handle edit action
            editHomeDetails();
            return true;
        }
        else if(id == R.id.delete){
            // Handle delete action
            deleteHome();
            return true;
        }
           else{
                return super.onContextItemSelected(item);
        }
    }

    private void editHomeDetails() {
        // Implement edit functionality here
        Toast.makeText(getContext(), "Edit clicked", Toast.LENGTH_SHORT).show();
    }

    private void deleteHome() {
        // Implement delete functionality here
        Toast.makeText(getContext(), "Delete clicked", Toast.LENGTH_SHORT).show();
    }




}
