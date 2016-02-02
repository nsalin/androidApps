package com.example.alin.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RelativeLayout gameRelativeLayout;
    Button goButton;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button playAgain;
    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    int score = 0;
    TextView resultTextView;
    TextView liveScore;
    TextView sumTextView;
    TextView timer;
    int numberOfQuestions = 0;
    int incorrectAnswer = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button) findViewById(R.id.startGame);
        sumTextView = (TextView) findViewById(R.id.ecuation);
        resultTextView = (TextView) findViewById(R.id.liveResult);
        liveScore = (TextView) findViewById(R.id.livescore);

        button1 = (Button) findViewById(R.id.textView);
        button2 = (Button) findViewById(R.id.textView2);
        button3 = (Button) findViewById(R.id.textView3);
        button4 = (Button) findViewById(R.id.textView4);
        timer = (TextView) findViewById(R.id.timer);
        playAgain = (Button) findViewById(R.id.playAgain);
        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);



    }
    public void choosedAnswer(View view){

        Log.i("Tag: ", view.getTag().toString() + " locationOfCorrectAnswer" + locationOfCorrectAnswer);
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Log.i("Correct: ", "correct");
            score ++;
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Wrong !");
        }
        numberOfQuestions++;
        liveScore.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generateQuestion();

    }

    public void generateQuestion(){
        int a,b;
        Random random = new Random();
        a = random.nextInt(21);
        b = random.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationOfCorrectAnswer = random.nextInt(4);
        answers.clear();

        for (int i = 0; i < 4;i++){
            if (i==locationOfCorrectAnswer){
                answers.add(a+b);
            } else {
                incorrectAnswer = random.nextInt(41);

                while (incorrectAnswer == a + b){
                    incorrectAnswer = random.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        button1.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));

    }

    public void startGame(View view){
        goButton.setVisibility(View.GONE);
        gameRelativeLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgain));
    }

    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;
        timer.setText("30s");
        liveScore.setText("0/0");
        resultTextView.setText("");
        playAgain.setVisibility(View.INVISIBLE);
        generateQuestion();

        new CountDownTimer(30100, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                playAgain.setVisibility(View.VISIBLE);
                timer.setText("0s");
                resultTextView.setText("Your Score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            }
        }.start();


    }

}
