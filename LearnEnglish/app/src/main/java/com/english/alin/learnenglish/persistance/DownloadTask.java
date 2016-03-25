package com.english.alin.learnenglish.persistance;

import android.os.AsyncTask;
import android.util.Log;

import com.english.alin.learnenglish.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.english.alin.learnenglish.persistance.FactoryMethods.getContent;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.PrimaryKeyColumns.QUESTIONS_ID;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.PrimaryKeyColumns.QUIZ_ID;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.PrimaryKeyColumns.READIND_ID;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.Tables.QUESTIONS_TABLE;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.Tables.QUIZ_TABLE;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.Tables.READING_TABLE;

/**
 * Created by Alin on 3/19/2016.
 */
public class DownloadTask extends AsyncTask<String, Void, JSONObject> {
    final static String DATE = "date";
    final static String READING_TEXT = "reading";
    final static String NUMBER_OF_QUESTIONS = "numberOfQuestions";
    final static String QUESTIONS = "questions";
    final static String TITLE = "title";
    final static String ANSWERS = "answers";
    final static String CORRECT = "correct";
    final static String NUMBER_OF_ANSWERS = "numberOfAnswers";
    String date;

    private final static String REQUEST_URL = "http://demo3296892.mockable.io/quiz";
    public static JSONObject retrived;
    @Override
    protected JSONObject doInBackground(String[] params) {
        System.out.println("m-am executat");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getContent(REQUEST_URL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getQuestions(jsonObject);
        return jsonObject;
    }

    public List<String> getQuestions(JSONObject requestedJSON){
        List<String> questions = new ArrayList<>();
        String title, readingText;
        try {
            Log.i("App-Adapter: ", "getQuestions");
            date = requestedJSON.getString(DATE);
            readingText = requestedJSON.getString(READING_TEXT);
            Integer numberOfQuestions = requestedJSON.getInt(NUMBER_OF_QUESTIONS);
            JSONArray questionsArray = requestedJSON.getJSONArray(QUESTIONS);
            JSONObject currentQuestion;
            int maxIdReading = MainActivity.databaseManager.getMaxId(READING_TABLE, READIND_ID);
            MainActivity.databaseManager.insertIntoQuizTable(date, maxIdReading);
            int maxId = MainActivity.databaseManager.getMaxId(QUIZ_TABLE, QUIZ_ID);
            int maxAnswers = 0;


            for (int i = 0; i < numberOfQuestions; i++) {
                Log.i("App-getQuestions", "am intrat in for");
                currentQuestion = questionsArray.getJSONObject(i);
                title = currentQuestion.getString(TITLE);
                int correctAnswer = currentQuestion.getInt(CORRECT);
                int numberOfAnswers = currentQuestion.getInt(NUMBER_OF_ANSWERS);
                System.out.println(i + "\t" + title);
                MainActivity.databaseManager.insertIntoQuestions(maxId, q);
                Log.i("App-Download task", "am bagat in db");

                getAnswers(requestedJSON, i, answersId, numberOfAnswers);
                answersId += 1;
//                QuizTable quiztable = new QuizTable(date, readingText, numberOfQuestions, correctAnswer, answersId, maxId);
//                quiztable.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private List<String> getAnswers(JSONObject questions, int index, int answerId, int numberOfAnswers){
        Log.i("App-Adapter: ", "getAnswers");

        ArrayList<String> answers = new ArrayList<>();
        try {
            JSONArray questionsArray = questions.getJSONArray(QUESTIONS);
            JSONObject answersArrayObject = questionsArray.getJSONObject(index);
            JSONArray answersArray = answersArrayObject.getJSONArray(ANSWERS);
            for (int i = 0; i < numberOfAnswers ; i++) {
                answers.add(answersArray.getString(i));
                System.out.println(answersArray.getString(i));
                MainActivity.databaseManager.insertIntoAnswers(answersArray.getString(i), answerId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return answers;
    }
}
