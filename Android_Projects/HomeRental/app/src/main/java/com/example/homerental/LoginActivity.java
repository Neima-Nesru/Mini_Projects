// LoginActivity.java
package com.example.homerental;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homerental.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private HomeDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize view binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize database helper
        dbHelper = new HomeDatabaseHelper(this);
    }

    public void onLoginClick(View view) {
        // Collect form data
        String name = binding.eTUsernamelg.getText().toString().trim();
        String password = binding.eTPasswordlg.getText().toString().trim();

        // Validate required fields
        if (name.isEmpty() || password.isEmpty()) {
            // At least one required field is empty
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return; // Exit method without proceeding further
        }

        // Check user credentials
        if (dbHelper.checkUserCredentials(name, password)) {
            // Credentials are correct, redirect to HomeActivity
            Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomesActivity.class);
            startActivity(intent);

        } else {
            // Credentials are incorrect
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    public void onLoginHere(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
