package com.english.alin.learnenglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.english.alin.learnenglish.activities.ImageActivity;
import com.english.alin.learnenglish.activities.ListenActivity;
import com.english.alin.learnenglish.activities.QuizActivity;
import com.english.alin.learnenglish.persistance.DownloadTask;
import com.english.alin.learnenglish.persistance.database.DatabaseManager;
import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {
    public static DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
        }
        Stetho.initializeWithDefaults(this);

        databaseManager = new DatabaseManager(getApplicationContext());
        DownloadTask task = new DownloadTask();
        task.execute();
    }

    public void startListen(View id){
        Intent listenIntent = new Intent(this,ListenActivity.class);
        startActivity(listenIntent);
    }
    public void startImages(View id){
        Intent imagesIntent = new Intent(this, ImageActivity.class);
        startActivity(imagesIntent);
    }

    public void startQuiz(View id){
        Intent quizIntent = new Intent(this, QuizActivity.class);
        startActivity(quizIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
