package com.example.alin.guestthecelebrity;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Alin on 1/17/2016.
 */
public class ContentDownloader extends AsyncTask<String, Integer, MapElement<String, String>> {
    private MainActivity activity;
    public ArrayList celebritiesName = new ArrayList();

    public ContentDownloader(MainActivity activity){
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Toast.makeText(activity, "Application loading...", Toast.LENGTH_LONG).show();
    }
    @Override
    protected MapElement<String, String> doInBackground(String... params) {
        try {

            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();
            return getImageAndNameFromPageURL(connection);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    // This is called from background thread but runs in UI
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        Toast.makeText(activity, "Application loading...", Toast.LENGTH_LONG).show();
    }

    // This runs in UI when background thread finishes
    //@Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(result);

        // Do things like hide the progress bar or change a TextView
    }
    private String getWebPageContent(HttpURLConnection connection){
        String content = "";
        try {
            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
//            int data = reader.read();
//
//            while (data != -1){
//                char currentCharacter = (char) data;
//                content+=currentCharacter;
//                data = reader.read();
//            }
            BufferedReader buffer = new BufferedReader(reader);

            String s = "";

            while ((s = buffer.readLine()) != null) {

                content += s;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private MapElement<String,String> getImageAndNameFromPageURL(HttpURLConnection connection){
        String pageContent = getWebPageContent(connection);
        MapElement<String,String> result = new MapElement<>();

        Pattern p = Pattern.compile("img src=\"(.*?)\"");
        Pattern p2 = Pattern.compile("alt=\"(.*?)\"");
        Matcher matcher = p.matcher(pageContent);
        Matcher matcher1 = p2.matcher(pageContent);

        while (matcher.find() & matcher1.find()){
            result.put(matcher.group(1), matcher1.group(1));
            celebritiesName.add(matcher1.group(1));
        }

        return result;
    }
}
