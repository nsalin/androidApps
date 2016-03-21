package com.english.alin.learnenglish.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.english.alin.learnenglish.R;
import com.english.alin.learnenglish.persistance.DownloadTask;

public class QuizActivity extends AppCompatActivity {
    RadioButton button;
    RadioButton button2;
    RadioButton button3;
    RadioButton button4;
    RadioButton button5;
    RadioButton button6;
    RadioButton button7;

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
        TextView text = (TextView) findViewById(R.id.textView5);
        text.setTextColor(Color.parseColor("#2957d8"));

        button = (RadioButton) findViewById(R.id.radioButton);
        button2 = (RadioButton) findViewById(R.id.radioButton2);
        button3 = (RadioButton) findViewById(R.id.radioButton3);
        button4 = (RadioButton) findViewById(R.id.radioButton4);
        button5 = (RadioButton) findViewById(R.id.radioButton5);
        button6 = (RadioButton) findViewById(R.id.radioButton6);
        button7 = (RadioButton) findViewById(R.id.radioButton7);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute();

    }
    public void startListenActivity(View view){
        Intent startListen = new Intent(this, ListenActivity.class);
        startActivity(startListen);
    }

    private void hideRadioButton(View view){
        view.setVisibility(View.INVISIBLE);
    }

    public void correctAnswer(View view){
        int viewTag = Integer.parseInt(view.getTag().toString());
        System.out.println(viewTag);
//        boolean visi = viewTag.equals("3") || viewTag.equals("7");
//        System.out.println("ce val are" + visi);
        if (!(viewTag == 3 || viewTag == 7)){
            hideRadioButton(view);
        } else {
            if (viewTag == 3){
                button.setEnabled(false);
                button2.setEnabled(false);
            } else {
                if (viewTag == 7){
                    button4.setEnabled(false);
                    button5.setEnabled(false);
                    button6.setEnabled(false);
                }
            }
        }
    }

}
