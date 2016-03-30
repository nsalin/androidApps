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
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Quiz");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        final Intent intent = getIntent();
        databaseManager = new DatabaseManager(getApplicationContext());
        radioIds = new ArrayList<>();
        radiogGroupIds = new ArrayList<>();

        Log.i("RadioQ-Create", "activity started");
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
                    Log.i("RadioQ-extT", "questions.size " + numberOfRadioGroupsNeeded + " maxReadingText " + maxIdReadingText);
                    for (int i = 0; i < numberOfRadioGroupsNeeded; i++) {
                        questionText = new TextView(getApplicationContext());
                        questionText.setText(questions.get(i));
                        Log.i("RadioQ-Qes", questions.get(i));
                        questionText.setTextColor(Color.BLACK);
                        questionText.setTextSize(15);
                        Log.i("RadioQ-Text", "done");
                        linearLayout.addView(questionText);

                        radioGroup = new RadioGroup(getApplicationContext());
                        radioGroup.setOrientation(RadioGroup.VERTICAL);
//                        radioGroup.setId(i);
//                        radiogGroupIds.add(radioGroup.getId());
                        Log.i("RadioQ-Group", "done");
                        ArrayList<String> answers = databaseManager.getAnswersByQuestionId(questionsId.get(i));
                        int tagValue = -1;
                        for (int j = 0; j < answers.size(); j++) {
                            radioButton = new RadioButton(getApplicationContext());
                            radioGroup.addView(radioButton);
                            radioButton.setText(answers.get(j));
                            tagValue = j;
                            radioButton.setTag(tagValue);
                            int id = i * j;
//                            radioButton.setId(id);
//                            radioIds.add(id);

                        }
                        Log.i("RadioQ-Group", "done");
                        linearLayout.addView(radioGroup);
                        radioGroup.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                int count = radioGroup.getChildCount();
                                for (int i = 0; i < count; i++) {
                                    View view = radioGroup.getChildAt(i);
                                    if (view instanceof RadioButton) {
                                        String getRTag = String.valueOf(view.getTag());
                                        if (!getRTag.equals(String.valueOf(correctAnswer.get(linearLayout.indexOfChild(radioGroup) + 1)))) {
                                            view.setVisibility(View.INVISIBLE);
                                            Log.i("RadioButton", "tapped");
                                        } else {
                                            Log.i("RadioButton", "tag=" + getRTag + " compareTo=" + correctAnswer.get(linearLayout.indexOfChild(radioGroup) + 1));
                                        }
                                    }

                                }
                            }
                        });
                        Log.i("RadioQ-Lay", "done");
                    }

//                    Thread.sleep(1000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();

    }
    public void submitAnswers(View view){
    }

}
