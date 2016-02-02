package com.example.alin.weatherapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String apiKeyAndParameter = "&appid=73df3377fafcd26523adc1b79f5f90f0";
    private static final String apiLink = "http://api.openweathermap.org/data/2.5/weather?q=";

    Button button;
    EditText cityText;
    WeatherTask weatherTask;
    TextView humidity;
    TextView pressure;
    TextView description;
    TextView temperature;
    TextView windspeed;
    TableLayout information;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        button = (Button) findViewById(R.id.button);
        cityText = (EditText) findViewById(R.id.editText);
        pressure = (TextView) findViewById(R.id.pressure);
        description = (TextView) findViewById(R.id.description);
        temperature = (TextView) findViewById(R.id.temperature);
        windspeed = (TextView) findViewById(R.id.windspeed);
        humidity = (TextView) findViewById(R.id.humidity);
        information = (TableLayout) findViewById(R.id.tableView);

    }

    public void getWeather(View view){
        information.setVisibility(View.VISIBLE);
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(cityText.getWindowToken(), 0);
        try {
            weatherTask = new WeatherTask(this);
            String location = URLEncoder.encode(String.valueOf(cityText.getText()), "UTF-8");
            JSONObject weatherJson = weatherTask.execute(apiLink + location +  apiKeyAndParameter).get();
            String temperatureValue = weatherTask.getCelsius(Float.valueOf(weatherJson.getJSONObject("main").getString("temp")));

            humidity.setText(weatherJson.getJSONObject("main").getString("humidity") + " %");
            pressure.setText(weatherJson.getJSONObject("main").getString("pressure") + " mb");
            temperature.setText(temperatureValue + " C");
            windspeed.setText(weatherJson.getJSONObject("wind").getString("speed") + " km/hr");
            description.setText(weatherTask.getElementValueFromJSONArray(weatherJson, "weather", "description"));
        } catch (InterruptedException | ExecutionException | JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Couldn't find the city", Toast.LENGTH_LONG).show();
        }
    }

}
