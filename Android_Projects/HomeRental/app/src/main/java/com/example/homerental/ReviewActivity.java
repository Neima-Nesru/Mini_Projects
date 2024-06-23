package com.example.homerental;

import static android.content.Intent.getIntent;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homerental.HomeDatabaseHelper;
import com.example.homerental.R;

public class ReviewActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText etComment;
    private Button btnSubmit;
    private String homeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ratingBar = findViewById(R.id.ratingBar);
        etComment = findViewById(R.id.etComment);
        btnSubmit = findViewById(R.id.btnSubmit);

        homeId = getIntent().getStringExtra("homeId");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReview();
            }
        });
    }

    private void saveReview() {
        int rate = (int) ratingBar.getRating();
        String comment = etComment.getText().toString();
        int homeIdInt = Integer.parseInt(homeId);
        int userId = getUserId();

        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues reviewValues = new ContentValues();
        reviewValues.put("RATE", rate);
        reviewValues.put("COMMENT", comment);
        reviewValues.put("HOMEID", homeIdInt);
        reviewValues.put("USERID", userId);

        db.insert("Reviews", null, reviewValues);

        Toast.makeText(this, "Review saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private int getUserId() {
        SharedPreferences prefs = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        return prefs.getInt("USER_ID", -1);
    }
}
