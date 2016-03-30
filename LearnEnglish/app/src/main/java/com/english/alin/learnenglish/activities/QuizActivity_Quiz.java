package com.english.alin.learnenglish.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.english.alin.learnenglish.R;
import com.english.alin.learnenglish.persistance.database.DatabaseManager;

import java.util.ArrayList;

public class QuizActivity_Quiz extends AppCompatActivity {
    DatabaseManager databaseManager;
    RadioButton radioButton;
    RadioGroup radioGroup;
    TextView questionText;
    ArrayList<Integer> radioIds;
    ArrayList<Integer> radiogGroupIds;
    ArrayList<Integer> correctAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_activity__quiz);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quiz");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        final Intent intent = getIntent();
        databaseManager = new DatabaseManager(getApplicationContext());
        radioIds = new ArrayList<>();
        radiogGroupIds = new ArrayList<>();

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait...", "Creating quiz...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutQuiz);
                    int maxIdReadingText = intent.getIntExtra("maxIdReadingText", 1);
                    ArrayList<String> questions = databaseManager.getQuestionsByQuizReadingId(maxIdReadingText);
                    ArrayList<Integer> questionsId = databaseManager.getQuestionsIDsByQuizReadingId(maxIdReadingText);
                    correctAnswer = databaseManager.getCorrectAnswersByReadingId(maxIdReadingText);

                    int numberOfRadioGroupsNeeded = questions.size();
                    for (int i = 0; i < numberOfRadioGroupsNeeded; i++) {
                        questionText = new TextView(getApplicationContext());
                        questionText.setText(questions.get(i));
                        questionText.setTextColor(Color.BLACK);
                        questionText.setTextSize(15);
                        linearLayout.addView(questionText);

                        radioGroup = new RadioGroup(getApplicationContext());
                        radioGroup.setOrientation(RadioGroup.VERTICAL);
                        radioGroup.setId(i);
                        Log.i("RadioQ-Group", "done");
                        ArrayList<String> answers = databaseManager.getAnswersByQuestionId(questionsId.get(i));
                        for (int j = 0; j < answers.size(); j++) {
                            radioButton = new RadioButton(getApplicationContext());
                            radioGroup.addView(radioButton);
                            radioButton.setText(answers.get(j));
                            radioButton.setTag(j);
                            radioButton.setId(j);

                        }
                        linearLayout.addView(radioGroup);
                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                int count = group.getChildCount();
                                View view = group.getChildAt(checkedId);
                                if (view instanceof RadioButton) {
                                    RadioButton radioButton = (RadioButton) view;
                                    String getRTag = String.valueOf(view.getTag());

                                    if (!getRTag.equals(String.valueOf(correctAnswer.get(group.getId())))) {
                                        radioButton.setVisibility(View.INVISIBLE);

                                    } else {
                                        for (int i = 0; i < count; i++) {
                                            group.getChildAt(i).setEnabled(false);
                                        }
                                    }
                                }

                            }
                        });
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }

}
