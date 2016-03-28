package com.english.alin.learnenglish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.english.alin.learnenglish.R;
import com.english.alin.learnenglish.activities.fragments.PageAdapter;
import com.english.alin.learnenglish.persistance.database.DatabaseManager;

public class QuizActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        TextView view = (TextView) findViewById(R.id.reading);
        view.setText(databaseManager.getLastReadingText());

    }

    public void startTest(View view){
        Intent intent = new Intent(this,QuizActivity_Quiz.class);
        startActivity(intent);
    }

}
