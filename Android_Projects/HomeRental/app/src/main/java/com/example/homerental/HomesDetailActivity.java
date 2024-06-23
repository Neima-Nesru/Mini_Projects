package com.example.homerental;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homerental.databinding.ActivityHomesDetailBinding;



public class HomesDetailActivity extends AppCompatActivity {
    private ActivityHomesDetailBinding binding;
    private String homeId; // Assuming you have the home ID here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityHomesDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        homeId = getIntent().getStringExtra("homeId");
// Registering the fragment's root view for context menu
        registerForContextMenu(binding.fGDetail);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.edit) {

            // Handle edit action
            editHome();
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

    private void editHome() {
        // Implement edit functionality here
        Intent intent = new Intent(this, EditHomeActivity.class);
        intent.putExtra("homeId", homeId);
        startActivity(intent);
    }

    private void confirmDeleteHome() {
        // Implement delete confirmation dialog
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setMessage("Are you sure you want to delete this home?");
         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 deleteHome();
             }
         });
         builder.setNegativeButton("No", null);
         builder.show();

        // For simplicity, directly call deleteHome
        deleteHome();
    }
    private void deleteHome() {
        confirmDeleteHome();
        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(this);
        dbHelper.deleteHome(homeId); // Implement this method in your database helper class

        // Example: After deletion, navigate back or perform any other action
        finish(); // Finish the activity after deletion for simplicity
    }

}