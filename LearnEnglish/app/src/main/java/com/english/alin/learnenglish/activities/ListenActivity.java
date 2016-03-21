package com.english.alin.learnenglish.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.english.alin.learnenglish.R;

public class ListenActivity extends AppCompatActivity {
    MediaPlayer audioManager;
    ImageView speaker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
         speaker = (ImageView) findViewById(R.id.speaker);

    }

    public void playLesson(View id){
         if (audioManager == null){
             audioManager = MediaPlayer.create(this, R.raw.listen);
             audioManager.start();
         }else {
             if (audioManager.isPlaying()){
                 audioManager.pause();
                 speaker.setImageResource(R.drawable.volume_icon2_pause);
             } else {
                 audioManager.start();
                 speaker.setImageResource(R.drawable.volume_icon2);
             }

         }



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (audioManager != null) audioManager.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (audioManager != null ) audioManager.stop();
    }

}
