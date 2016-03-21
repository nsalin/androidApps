package com.english.alin.learnenglish.persistance;

import com.english.alin.learnenglish.MainActivity;
import com.english.alin.learnenglish.persistance.database.DatabaseManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Alin on 3/20/2016.
 */
public class AdapterDBJSON {
    final static String DATE = "date";
    final static String READING_TEXT = "reading";
    final static String NUMBER_OF_QUESTIONS = "numberOfQuestions";
    final static String QUESTIONS = "questions";
    final static String TITLE = "title";
    final static String ANSWERS = "answers";
    final static String CORRECT = "correct";
    final static String NUMBER_OF_ANSWERS = "numberOfAnswers";

    DownloadTask downloadTask;
    JSONObject requestedJSON;

    String date;
    public AdapterDBJSON(){
        downloadTask = new DownloadTask();
    }

    public List<String> getQuestions(){
        List<String> questions = new ArrayList<>();
        String title, readingText;
        try {
            requestedJSON = downloadTask.execute().get();
            date = requestedJSON.getString(DATE);
            readingText = requestedJSON.getString(READING_TEXT);
            Integer numberOfQuestions = requestedJSON.getInt(NUMBER_OF_QUESTIONS);
            JSONArray questionsArray = requestedJSON.getJSONArray(QUESTIONS);
            JSONObject currentQuestion;
            MainActivity.databaseManager.insertIntoQustionsDetails(date, readingText, numberOfQuestions);
            int maxId = MainActivity.databaseManager.getMaxIdQuestionDetails();
            int answersId = MainActivity.databaseManager.getMaxId("questions");


            for (int i = 0; i < numberOfQuestions; i++) {
                currentQuestion = questionsArray.getJSONObject(i);
                title = currentQuestion.getString(TITLE);
                int correctAnswer = currentQuestion.getInt(CORRECT);
                int numberOfAnswers = currentQuestion.getInt(NUMBER_OF_ANSWERS);
                System.out.println(i + "\t" + title);
                MainActivity.databaseManager.insertIntoQuestions(title, numberOfAnswers, maxId, answersId, correctAnswer);
                getAnswers(requestedJSON, i, answersId, numberOfAnswers);
//                QuizTable quiztable = new QuizTable(date, readingText, numberOfQuestions, correctAnswer, answersId, maxId);
//                quiztable.save();
            }
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private List<String> getAnswers(JSONObject questions, int index, int answerId, int numberOfAnswers){
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
