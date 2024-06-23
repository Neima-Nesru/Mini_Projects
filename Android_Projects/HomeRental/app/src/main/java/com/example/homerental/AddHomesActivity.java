package com.example.homerental;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homerental.databinding.ActivityAddHomesBinding;

public class AddHomesActivity extends AppCompatActivity {
    private ActivityAddHomesBinding binding;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddHomesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.iVImage.setOnClickListener(v -> openGallery());
        binding.publishBtn.setOnClickListener(v -> confirmSave());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> imageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            imageUri = result.getData().getData();
                            if (imageUri != null) {
                                binding.iVImage.setImageURI(imageUri);
                            }
                        }
                    });

    private void confirmSave() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm)
                .setMessage(R.string.confirmation)
                .setPositiveButton(R.string.yes, (dialog, which) -> saveData())
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void saveData() {
        String title = binding.eTTitle.getText().toString();
        String description = binding.eTDescription.getText().toString();
        String location = binding.eTLocation.getText().toString();
        String price = binding.eTPrice.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) ||
                TextUtils.isEmpty(location) || TextUtils.isEmpty(price) || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", title);
        values.put("DESCRIPTION", description);
        values.put("LOCATION", location);
        values.put("PRICE", Double.parseDouble(price));
        values.put("IMAGE_RESOURCE_ID", imageUri.toString());
        values.put("USERID", 1); // Assuming a static user ID for simplicity

        long newRowId = db.insert("Homes", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Home added successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomesActivity.class));
        } else {
            Toast.makeText(this, "Failed to add home", Toast.LENGTH_SHORT).show();
        }
    }
}
