package com.example.nasa;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class NasaAsyncTask extends AsyncTask<String, Void, NasaData> {

    NasaActivity nasaActivity;

    // constructor
    public NasaAsyncTask(NasaActivity nasaActivity) {
        this.nasaActivity = nasaActivity;
    }

    @Override
    protected NasaData doInBackground(String... strings) {

        NasaData nasaData = new NasaData();

        String urlString = strings[0];

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int responseCode = 0;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {

            // get response stream from url connection
            InputStream responseStream = null;
            try {
                responseStream = httpURLConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // scan through response stream
            Scanner scanner = new Scanner(responseStream);
            String jsonResponse = "";
            while(scanner.hasNextLine()) {
                jsonResponse += scanner.nextLine();
            }
            // parse json for title and image url
            JSONObject jsonObject = JSONUtil.parseObj(jsonResponse);
            nasaData.title = jsonObject.getStr("title");
            String imageUrl = jsonObject.getStr("url");

            // use image url to get image
            try {
                url = new URL(imageUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream imageStream = null;
            try {
                imageStream = httpURLConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            nasaData.bitmap = BitmapFactory.decodeStream(imageStream);

            scanner.close();
        }

        // pass nasaData into onPostExecute
        return nasaData;
    }

    @Override
    protected void onPostExecute(NasaData nasaData) {
        nasaActivity.textView.setText(nasaData.title);
        nasaActivity.imageView.setImageBitmap(nasaData.bitmap);
    }
}
