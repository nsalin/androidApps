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
        Log.i("App-DB: ", "insertIntoReadingTable");
        ContentValues contentValues = new ContentValues();
        contentValues.put(READING, readingText);
        sqlDb.insert(READING_TABLE, null, contentValues);
    }

    public void insertIntoQuizTable(String date, Integer readingId){
        Log.i("App-DB: ", "insertIntoQustionsDetails");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(READING_ID, readingId);
        sqlDb.insert(QUIZ_TABLE, null, contentValues);
    }

    public void insertIntoQuestions(Integer quizId, String question){
        Log.i("App-DB: ", "insertIntoQuestions");

        ContentValues contentValues = new ContentValues();
        contentValues.put(QUIZ_ID, quizId);
        contentValues.put(QUESTION, question);
        sqlDb.insert(QUSTIONS_TABLE, null, contentValues);
    }

    public void insertIntoAnswers(String answer, CorrectAnswer correctAnswer, Integer questionId, Integer sequencenumber){
        Log.i("App-DB: ", "insertIntoAnswers");

        ContentValues contentValues = new ContentValues();
        contentValues.put(ANSWERS, answer);
        contentValues.put(CORRECT_ANSWER, correctAnswer.correct_answer_value());
        contentValues.put(QUESTIONS_ID, questionId);
        contentValues.put(SEQUENCE_NUMBER, sequencenumber);
        sqlDb.insert(ANSWERS_TABLE, null, contentValues);
    }

    public int getMaxId(Tables table, PrimaryKeyColumns column){
        Log.i("App-DB: ", "getMaxId");

        int maxId = 0;
        Cursor cursor = sqlDb.rawQuery("SELECT MAX(" + column + ") as max FROM " + table, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            if (cursor.getString(cursor.getColumnIndex("max")) != null) {
                maxId = cursor.getInt(cursor.getColumnIndex("max"));
            }
            cursor.moveToNext();
        }
        cursor.close();
        Log.i("App-DB: ", "getMaxId end" + maxId);
        return maxId;
    }


}
