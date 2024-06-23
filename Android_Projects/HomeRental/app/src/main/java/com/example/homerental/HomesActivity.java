package com.example.homerental;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeListFragment homeListFragment = new HomeListFragment();
        fragmentTransaction.add(R.id.fragmentContainer, homeListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addHomeMenu) {
            Intent intent = new Intent(this, AddHomesActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.menu_Exit) {
            finish();
            System.exit(0);
            return true;
        }
//        else if (id == R.id.yourProfileMenu) {
//            Intent intent = new Intent(this, ProfileActivity.class);
//            startActivity(intent);
//            return true;
//        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
