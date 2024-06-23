package com.example.homerental;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.homerental.databinding.ActivitySignupBinding;

import java.util.Calendar;
import java.util.Locale;
import android.content.SharedPreferences;
import android.widget.Toast;


/** @noinspection ALL*/
public class SignupActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String IMAGE_URI_KEY = "imageUriKey";
    private static final String NAME_KEY = "nameKey";
    private static final String PHONE_NUMBER_KEY = "phoneNumberKey";
    private static final String EMAIL_KEY = "emailKey";
    private static final String DOB_KEY = "dobKey";
    private static final String SELECTED_LANGUAGE_KEY = "selectedLanguage";
    private Calendar calendar;
    private ActivitySignupBinding binding;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize view binding
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    // Create a cursor

         HomeDatabaseHelper  helper= new HomeDatabaseHelper(this);
        try {
            SQLiteDatabase db = helper.getReadableDatabase();

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


        // Initialize calendar instance
        calendar = Calendar.getInstance();

        // Disable the soft keyboard for editTextDOB
        binding.eTDob.setInputType(InputType.TYPE_NULL);

        binding.eTDob.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this,
                    (view, year1, month1, dayOfMonth1) ->
                            binding.eTDob.setText(dayOfMonth1 + "/" + (month1 + 1) + "/" + year1),
                    year, month, dayOfMonth);

            datePickerDialog.show();
        });

        // Set OnClickListener for the FloatingActionButton to pick images from the gallery
        binding.floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });


        // Restore saved state
        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable(IMAGE_URI_KEY);
            if (imageUri != null) {
                Glide.with(this)
                        .load(imageUri)
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_person_12)
                        .error(R.drawable.ic_baseline_error_outline_12)
                        .into(binding.imageViewPerson);
            }

            binding.eTUsername.setText(savedInstanceState.getString(NAME_KEY));
            binding.eTPhone.setText(savedInstanceState.getString(PHONE_NUMBER_KEY));
            binding.eTEmail.setText(savedInstanceState.getString(EMAIL_KEY));
            binding.eTDob.setText(savedInstanceState.getString(DOB_KEY));

        }

        // Set onClick listener for the submit button
        binding.signupBtn.setOnClickListener(view -> onSignupClick());
    }


    public void onCreateNewClick(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void setLanguage() {
        // Retrieve the selected language from preferences or use default
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedLanguage = preferences.getString(SELECTED_LANGUAGE_KEY, "am");

        // Set the language for the app
        Locale locale = new Locale(selectedLanguage);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();

        config.setLocale(locale);
        createConfigurationContext(config);

        // Update the app's configuration
        resources.updateConfiguration(config, dm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_amharic) {
            setLocale("am");
            return true;
        } else if (id == R.id.menu_english) {
            setLocale("en");
            return true;
        } else if (id == R.id.menu_Exit) {
            exitApp();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void setLocale(String lang) {
        // Save the selected language in SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_LANGUAGE_KEY, lang);
        editor.apply();

        // Set the language for the app
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();

        config.setLocale(locale);
        createConfigurationContext(config);

        // Update the app's configuration
        resources.updateConfiguration(config, dm);

        // Restart the activity to apply the new locale
        recreate();
    }


    private void exitApp() {
        finish();
        System.exit(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Log.d("MainActivity", "Selected Image URI: " + imageUri.toString());
            Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_person_12)
                    .error(R.drawable.ic_baseline_error_outline_12)
                    .into(binding.imageViewPerson);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the imageUri and form data
        if (imageUri != null) {
            outState.putParcelable(IMAGE_URI_KEY, imageUri);
        }
        outState.putString(NAME_KEY, binding.eTUsername.getText().toString().trim());
        outState.putString(PHONE_NUMBER_KEY, binding.eTPhone.getText().toString().trim());
        outState.putString(EMAIL_KEY, binding.eTEmail.getText().toString().trim());
        outState.putString(DOB_KEY, binding.eTDob.getText().toString().trim());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore the imageUri and form data
        imageUri = savedInstanceState.getParcelable(IMAGE_URI_KEY);
        if (imageUri != null) {
            Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_person_12)
                    .error(R.drawable.ic_baseline_error_outline_12)
                    .into(binding.imageViewPerson);
        }

        binding.eTUsername.setText(savedInstanceState.getString(NAME_KEY));
        binding.eTPhone.setText(savedInstanceState.getString(PHONE_NUMBER_KEY));
        binding.eTEmail.setText(savedInstanceState.getString(EMAIL_KEY));
        binding.eTDob.setText(savedInstanceState.getString(DOB_KEY));

    }

    public void onSignupClick() {
        String name = binding.eTUsername.getText().toString().trim();
        String phoneNumber = binding.eTPhone.getText().toString().trim();
        String email = binding.eTEmail.getText().toString().trim();
        String password = binding.eTPassword.getText().toString().trim();
        String dob = binding.eTDob.getText().toString().trim();

        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() || dob.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill in all required fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        HomeDatabaseHelper helper = new HomeDatabaseHelper(this);
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
           // register(db, R.drawable.ic_baseline_person_12, name, email, Integer.parseInt(phoneNumber), dob, password);
            ContentValues userValues = new ContentValues();
            userValues.put("IMAGE_RESOURCE_ID", R.drawable.ic_baseline_person_12);
            userValues.put("USERNAME", name);
            userValues.put("EMAIL", email);
            userValues.put("PHONE", phoneNumber);
            userValues.put("DOB", dob);
            userValues.put("PASSWORD", password);

            db.insert("Users", null, userValues);

            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, HomesActivity.class);
            startActivity(intent);
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

}
