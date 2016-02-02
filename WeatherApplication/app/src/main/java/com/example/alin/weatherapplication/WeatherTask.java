package com.example.alin.weatherapplication;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alin on 1/21/2016.
 */
public class WeatherTask extends AsyncTask<String, Void, JSONObject> {
    MainActivity mainActivity;

    public WeatherTask (MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line, content = "";
            while ((line = bufferedReader.readLine()) != null){
                content += line;
            }
            JSONObject jsonObject = new JSONObject(content);

            return jsonObject;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Toast.makeText(mainActivity.getApplicationContext(), "Couldn't find the city", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    protected String getElementValueFromJSONArray(JSONObject weatherJson, String arrayName, String element){
        JSONArray weather;
        JSONObject jsonObject = null;
        try {
            weather = weatherJson.getJSONArray(arrayName);
            for (int i = 0; i < weather.length(); i++){
                jsonObject = weather.getJSONObject(i);
            }
            if (jsonObject != null){
                return  jsonObject.getString(element);
            } else {
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mainActivity.getApplicationContext(), "Couldn't find the city", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    protected String getCelsius(float farad){
        return String.valueOf(Math.round(farad - 273.15f));
    }
}
