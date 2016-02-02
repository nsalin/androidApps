package com.example.alin.guestthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alin on 1/18/2016.
 */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.connect();
            InputStream stream = httpURLConnection.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(stream);

            return image;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
