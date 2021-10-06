package com.example.nasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class NasaActivity extends AppCompatActivity {

    // View attributes
    ImageView imageView;
    TextView textView;
    Button saveBtn;

    // Nasa object to hold content
    Nasa nasa = new Nasa();

    // Helper to perform functions on database
    DatabaseHelper db = new DatabaseHelper(this);

    final String API_KEY = "NpTIu5mqBJhrE8Rcv1OgJnvv9qS5yRyEetxBrZtU";
    final String URL = "https://api.nasa.gov/planetary/apod?api_key=" + API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa);

        // Make RestAPI call
        NasaAsyncTask nasaAsyncTask = new NasaAsyncTask(this);
        nasaAsyncTask.execute(URL);

        // set attributes
        textView = findViewById(R.id.imageTitle);
        imageView = findViewById(R.id.image);
        saveBtn = findViewById(R.id.saveBtn);
    }

    public void saveImage(View view) {

        // set nasa object with title from textView
        nasa.setTitle(textView.getText().toString());

        // convert bitmap to btye array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable(); // get drawable
        Bitmap bitmap = bitmapDrawable.getBitmap(); // get bitmap from drawable
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // compress bitmap into png format into stream

        // set nasa object with image from imageView
        nasa.setImage(stream.toByteArray());

//         add nasa entry into database
        db.addEntry(nasa);

        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}