package com.example.alin.memorableplaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
static  ArrayList<String> elements;
 static ArrayAdapter<String> adapter;

    static ArrayList<LatLng> locations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView choices = (ListView) findViewById(R.id.listView);
        final Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        elements = new ArrayList<>();
        locations = new ArrayList<>();

        elements.add("Add new place ...");
        locations.add(new LatLng(0,0));

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, elements);
        choices.setAdapter(adapter);
        choices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Pressed: ", elements.get(position));
                intent.putExtra("locationInfo", position);
                startActivity(intent);
            }
        });

    }
}
