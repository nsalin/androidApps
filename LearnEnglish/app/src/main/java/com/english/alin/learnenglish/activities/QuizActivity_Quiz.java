package com.english.alin.learnenglish.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.english.alin.learnenglish.R;

public class QuizActivity_Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_activity__quiz);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait...", "Creating quiz...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RadioGroup.LayoutParams rprms;
                    RadioGroup group = new RadioGroup(getApplicationContext());
                    group.setOrientation(RadioGroup.HORIZONTAL);
                    for(int i=0;i<3;i++){

                        RadioButton radioButton = new RadioButton(getApplicationContext());
                        radioButton.setText("new"+i);
                        radioButton.setId(i);
                        rprms= new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        group.addView(radioButton, rprms);
                    }
                //TODO remove after implemeting entire logic http://www.androidhub4you.com/2013/04/dynamic-radio-button-demo-in-android.html
                    Thread.sleep(1000);
                }catch (Exception ex){

                }
                progressDialog.dismiss();
            }
        }).start();


    }
}
