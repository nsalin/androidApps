//package com.example.alin.phrasesapp;
//
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import java.io.File;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AnotherWay extends AppCompatActivity {
//    List<String> soundsName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Field[] name = R.raw.class.getFields();
//        soundsName = new ArrayList<>();
//        for (int i = 0; i<name.length;i++){
//            Log.i("Raw/SongName: ", name[i].getName());
//            soundsName.add(name[i].getName());
//        }
//    }
//
//    public void playPhrase(View view){
//        int button = Integer.parseInt(view.getTag().toString());
//        MediaPlayer mediaPlayer = null;
//        System.out.println(soundsName.size());
//        for (int i=0; i < soundsName.size();i++){
//            String soundName = "android.resource://" + getPackageName() + "/raw/" + soundsName.get(button-1);
//            System.out.println(button + soundName);
//            Uri soundUri = Uri.parse(soundName);
//            mediaPlayer = MediaPlayer.create(this, soundUri);
//        }
//        if (mediaPlayer!=null) mediaPlayer.start();
//
//    }
//}
