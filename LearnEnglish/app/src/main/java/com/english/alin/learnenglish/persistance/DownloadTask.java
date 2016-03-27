package com.english.alin.learnenglish.persistance;

import android.os.AsyncTask;
import android.util.Log;

import com.english.alin.learnenglish.persistance.database.DatabaseConstants.CorrectAnswer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.english.alin.learnenglish.MainActivity.databaseManager;
import static com.english.alin.learnenglish.persistance.FactoryMethods.getContent;
import static com.english.alin.learnenglish.persistance.JSON_URL.REQUEST_URL;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.CorrectAnswer.*;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.CorrectAnswer.FALSE;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.PrimaryKeyColumns.QUESTIONS_ID;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.PrimaryKeyColumns.QUIZ_ID;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.PrimaryKeyColumns.READING_ID;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.Tables.questions;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.Tables.quiz;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.Tables.reading;

/**
 * Created by Alin on 3/19/2016.
 */
public class DownloadTask extends AsyncTask<String, Void, JSONObject> {
    final static String DATE = "date";
    final static String QUIZ_TITLE = "quizTitle";
    final static String READING_TEXT = "reading";
    final static String QUESTIONS = "questions";
    final static String QUESTION = "question";
    final static String ANSWERS = "answers";
    final static String ANSWER = "answer";
    final static String IS_CORRECT = "isCorrect";
    String date;


    @Override
    protected JSONObject doInBackground(String[] params) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getContent(REQUEST_URL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fillDatabase(jsonObject);
        return jsonObject;
    }

    private List<String> fillDatabase(JSONObject requestedJSON){
        List<String> questionsList = new ArrayList<>();
        String title, readingText,quizTitle;
        try {
            Log.i("App-Adapter: ", "getQuestions");
            date = requestedJSON.getString(DATE);
            readingText = requestedJSON.getString(READING_TEXT);
            quizTitle = requestedJSON.getString(QUIZ_TITLE);
            databaseManager.insertIntoReadingTable(readingText);

            JSONArray questionsArray = requestedJSON.getJSONArray(QUESTIONS);
            int numberOfQuestions = questionsArray.length();
            JSONObject currentQuestion;

            int maxIdReading = databaseManager.getMaxId(reading, READING_ID);
            databaseManager.insertIntoQuizTable(date, quizTitle, maxIdReading);
            int maxId = databaseManager.getMaxId(quiz, QUIZ_ID);


            for (int i = 0; i < numberOfQuestions; i++) {
                Log.i("App-getQuestions", "am intrat in for");
                currentQuestion = questionsArray.getJSONObject(i);
                title = currentQuestion.getString(QUESTION);

                System.out.println(i + "\t" + title);
                databaseManager.insertIntoQuestions(maxId, title);
                Log.i("App-Download task", "am bagat in db");
                int currentQuestionId = databaseManager.getMaxId(questions, QUESTIONS_ID);
                fillAnswersTable(currentQuestion, currentQuestionId);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questionsList;
    }

    private void fillAnswersTable(JSONObject currentQuestion, int questionId){
        Log.i("App-Adapter: ", "getAnswers");

        try {
            int numberOfAnswers = currentQuestion.getJSONArray(ANSWERS).length();
            JSONArray questionsArray = currentQuestion.getJSONArray(ANSWERS);
            for (int i = 0; i < numberOfAnswers ; i++) {
                JSONObject answersArrayObject = questionsArray.getJSONObject(i);
                String answer = answersArrayObject.getString(ANSWER);
                CorrectAnswer correctAnswer = answersArrayObject.getString(IS_CORRECT).equals("true")? TRUE : FALSE;
                databaseManager.insertIntoAnswers(answer,correctAnswer, questionId, i+1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
