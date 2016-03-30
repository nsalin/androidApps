package com.english.alin.learnenglish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.english.alin.learnenglish.R;
import com.english.alin.learnenglish.persistance.database.DatabaseManager;

import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.PrimaryKeyColumns.READING_ID;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.Tables.reading;

public class QuizActivity extends AppCompatActivity {
    DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Reading");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        databaseManager = new DatabaseManager(getApplicationContext());
        TextView view = (TextView) findViewById(R.id.reading);
        view.setText(databaseManager.getLastReadingText());

    }

    public void startTest(View view){
        Intent intent = new Intent(this,QuizActivity_Quiz.class);
        intent.putExtra("maxIdReadingText", databaseManager.getMaxId(reading, READING_ID));
        startActivity(intent);
    }

}
