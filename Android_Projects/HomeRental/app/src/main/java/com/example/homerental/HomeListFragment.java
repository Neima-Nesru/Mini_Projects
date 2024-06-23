package com.example.homerental;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeListFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomesAdapter homeAdapter;
    private List<Home> homeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        homeList = new ArrayList<>();
        loadHomes();

        homeAdapter = new HomesAdapter(getContext(), homeList);
        recyclerView.setAdapter(homeAdapter);

        return view;
    }

    private void loadHomes() {
        HomeDatabaseHelper dbHelper = new HomeDatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "Homes",
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("PRICE"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("LOCATION"));
            String imageUri = cursor.getString(cursor.getColumnIndexOrThrow("IMAGE_RESOURCE_ID"));

            homeList.add(new Home(id, title, description, price, location, imageUri));
        }
        cursor.close();
    }
}
