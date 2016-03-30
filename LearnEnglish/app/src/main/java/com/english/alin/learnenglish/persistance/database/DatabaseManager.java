package com.english.alin.learnenglish.persistance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alin on 3/20/2016.
 */
import java.util.ArrayList;

import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.*;
import static com.english.alin.learnenglish.persistance.database.DatabaseConstants.QUESTIONS_ID;

public class DatabaseManager extends SQLiteOpenHelper{


    SQLiteDatabase sqlDb = this.getWritableDatabase();

    public DatabaseManager(Context context){
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("App-onCreateDB: ", "onCreate");

        db.execSQL(createReadingTable);
        db.execSQL(createQuizTable);
        db.execSQL(createQuestionsTable);
        db.execSQL(createAnswersTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ANSWERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + QUSTIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + READING_TABLE);
        onCreate(db);
    }

    public void insertIntoReadingTable(String readingText){
        ContentValues contentValues = new ContentValues();
        contentValues.put(READING, readingText);
        sqlDb.insert(READING_TABLE, null, contentValues);
    }

    public void insertIntoQuizTable(String date, String quizTitle, Integer readingId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(QUIZ_TITLE, quizTitle);
        contentValues.put(READING_ID, readingId);
        sqlDb.insert(QUIZ_TABLE, null, contentValues);
    }

    public void insertIntoQuestions(Integer quizId, String question){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUIZ_ID, quizId);
        contentValues.put(QUESTION, question);
        sqlDb.insert(QUSTIONS_TABLE, null, contentValues);
    }

    public void insertIntoAnswers(String answer, CorrectAnswer correctAnswer, Integer questionId, Integer sequencenumber){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ANSWERS, answer);
        contentValues.put(CORRECT_ANSWER, correctAnswer.correct_answer_value());
        contentValues.put(QUESTIONS_ID, questionId);
        contentValues.put(SEQUENCE_NUMBER, sequencenumber);
        sqlDb.insert(ANSWERS_TABLE, null, contentValues);
    }

    public int getMaxId(Tables table, PrimaryKeyColumns column){
        int maxId = 0;
        Cursor cursor = sqlDb.rawQuery("SELECT MAX(" + column + ") as max FROM " + table, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            if (cursor.getString(cursor.getColumnIndex("max")) != null) {
                maxId = cursor.getInt(cursor.getColumnIndex("max"));
            }
            cursor.moveToNext();
        }
        cursor.close();
        return maxId;
    }

    public String getLastReadingText(){
        String lastInsertedText = null;
        int maxIdFromReadingText = getMaxId(Tables.reading, PrimaryKeyColumns.READING_ID);
        Cursor cursor = sqlDb.rawQuery("SELECT " + READING + " FROM " + READING_TABLE + " WHERE " + READING_ID + "=" + maxIdFromReadingText, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            lastInsertedText = cursor.getString(cursor.getColumnIndex(READING));
            cursor.moveToNext();
        }
        cursor.close();
        return lastInsertedText;
    }

    public String getLastDate(){
        String lastInsertedText = null;
        int maxIdFromReadingText = getMaxId(Tables.reading, PrimaryKeyColumns.READING_ID);
        Cursor cursor = sqlDb.rawQuery("SELECT " + DATE + " FROM " + QUIZ_TABLE + " WHERE " + READING_ID + "=" + maxIdFromReadingText, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            lastInsertedText = cursor.getString(cursor.getColumnIndex(DATE));
            cursor.moveToNext();
        }
        cursor.close();
        return lastInsertedText;
    }

    public int getNumberOfQuestionByReadinId(int readingId){
        int numberOfQuestions = 0;
        Cursor cursor = sqlDb.rawQuery("SELECT COUNT(*) as count FROM "+ QUSTIONS_TABLE + " WHERE " + READING_ID + "=" + readingId, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            numberOfQuestions = cursor.getInt(cursor.getColumnIndex("count"));
            cursor.moveToNext();
        }
        cursor.close();
        return numberOfQuestions;
    }

    public ArrayList<String> getQuestionsByQuizReadingId(int readingId){
        ArrayList<String> questions = new ArrayList<>();
        Cursor cursor = sqlDb.rawQuery("select qs.question from questions qs join quiz qz on qs.quiz_id = qz.quiz_id join reading rd on qz.reading_id = rd.reading_id where rd.reading_id = " + readingId, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
             questions.add(cursor.getString(cursor.getColumnIndex("question")));
        }
        cursor.close();
        return questions;
    }

    public ArrayList<Integer> getQuestionsIDsByQuizReadingId(int readingId){
        ArrayList<Integer> questions = new ArrayList<>();
        Cursor cursor = sqlDb.rawQuery("select qs.questions_id from questions qs join quiz qz on qs.quiz_id = qz.quiz_id join reading rd on qz.reading_id = rd.reading_id where rd.reading_id = " + readingId, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            questions.add(cursor.getInt(cursor.getColumnIndex("questions_id")));
        }
        cursor.close();
        return questions;
    }

    public ArrayList<String> getAnswersByQuestionId(int questionId){
        ArrayList<String> answers = new ArrayList<>();
        Cursor cursor = sqlDb.rawQuery("select answer_text from answers where questions_id = " + questionId, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            answers.add(cursor.getString(cursor.getColumnIndex("answer_text")));
        }
        cursor.close();
        return answers;
    }

    public int getCorrectNoAnswerByQuestionId(int questionId){
        int correctAnswer = 0;
        Cursor cursor = sqlDb.rawQuery("select sequenceNo from answers where questions_id = " + questionId + " and isCorrect = 1", null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            correctAnswer = cursor.getInt(cursor.getColumnIndex("count"));
            cursor.moveToNext();
        }
        cursor.close();
        return correctAnswer;
    }

    public ArrayList<Integer> getCorrectAnswersByReadingId(int readingid){
        ArrayList<Integer> answersID = new ArrayList<>();
        Cursor cursor = sqlDb.rawQuery("select aw.sequenceNo from answers aw join questions qs on aw.questions_id = qs.questions_id join quiz qz on qs.quiz_id = qz.quiz_id join reading rd on qz.reading_id = rd.reading_id  where aw.isCorrect = 1 and rd.reading_id = " + readingid, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            answersID.add(cursor.getInt(cursor.getColumnIndex("sequenceNo")));
        }
        cursor.close();
        return answersID;
    }
}
