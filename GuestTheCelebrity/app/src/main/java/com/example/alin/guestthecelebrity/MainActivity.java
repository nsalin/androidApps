package com.example.alin.guestthecelebrity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button button2;
    Button button3;
    Button button4;
    ImageView celebrityPhoto;
    MapElement<String, String> imagesAndNames = new MapElement<>();
    int currentCeleb = 0;
    ArrayList celebritiesNames = new ArrayList();
//    ImageDownloader imageDownloader = new ImageDownloader();
    int correctAnswerButton = 0;
    String celebrityName = "";
    ArrayList<String> answers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        celebrityPhoto = (ImageView) findViewById(R.id.imageView);

        ContentDownloader contentDownloader = new ContentDownloader(this);
        try {
            imagesAndNames = contentDownloader.execute("http://www.posh24.com/celebrities").get();
            celebritiesNames = contentDownloader.celebritiesName;

            getCelebrityDetails();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void getCelebrityDetails(){
        Random random = new Random();
        currentCeleb = random.nextInt(imagesAndNames.size());
        Bitmap image = null;
        try {
            image = new ImageDownloader().execute(imagesAndNames.getEntry(currentCeleb).getKey()).get();

            while (image == null){
                currentCeleb = random.nextInt(imagesAndNames.size());
                image = new ImageDownloader().execute(imagesAndNames.getEntry(currentCeleb).getKey()).get();
            }

            celebrityName = (String) celebritiesNames.get(currentCeleb);
            celebrityPhoto.setImageBitmap(image);
            setButtonName();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void guessTheCelebrity(View view){
        Log.i("Current tag: ", view.getTag().toString());
        Log.i("Current button: ", Integer.toString(correctAnswerButton));

        if (view.getTag().toString().equals(Integer.toString(correctAnswerButton))){
            Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Wrong",Toast.LENGTH_SHORT).show();
        }

        getCelebrityDetails();
    }

    private void setButtonName(){
        Random random = new Random();
        int randomIndex;
        correctAnswerButton = random.nextInt(4);

//        String buttonName = (String) celebritiesNames.get(randomIndex);
        answers.clear();
        for (int i = 0; i < 4;i++){
            if (i==correctAnswerButton){
                answers.add(celebrityName);
            } else {
                randomIndex = random.nextInt(celebritiesNames.size());
                String buttonName = (String) celebritiesNames.get(randomIndex);

                while (buttonName.equals(celebrityName)){
                    randomIndex = random.nextInt(celebritiesNames.size());
                    buttonName = (String) celebritiesNames.get(randomIndex);
                }
                answers.add(buttonName);
            }
        }

        button.setText(answers.get(0));
        button2.setText(answers.get(1));
        button3.setText(answers.get(2));
        button4.setText(answers.get(3));

    }



}
