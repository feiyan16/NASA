package com.example.nasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class DatabaseActivity extends AppCompatActivity {

    // view attributes
    RecyclerView nasaList;

    // RecyclerView adapter and layout
    RecyclerAdapter recyclerAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;

    ArrayList<Nasa> nasaImages = new ArrayList<>();

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        nasaList = findViewById(R.id.nasaList);

        nasaImages = databaseHelper.readEntries();

        // set up Recycler View
        recyclerAdapter = new RecyclerAdapter(nasaImages);
        layoutManager = new LinearLayoutManager(this.getApplicationContext());
        nasaList.setLayoutManager(layoutManager);
        nasaList.setAdapter(recyclerAdapter);
    }
}