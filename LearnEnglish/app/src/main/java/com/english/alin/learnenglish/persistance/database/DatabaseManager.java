package com.english.alin.learnenglish.persistance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.english.alin.learnenglish.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Alin on 3/20/2016.
 */
public class DatabaseManager extends SQLiteOpenHelper{
    final static String DATABASE_NAME = "learnenglish";
    final static String QUSTIONS_TABLE = "questions";
    final static String QUSTIONS_TABLE_DETAILS = "questionsDetails";
    final static String ANSWERS_TABLE = "answers";
    final static String DATE = "date";
    final static String READING = "reading";
    final static String NUMBER_OF_QUESTIONS = "numberOfQuestions";
    final static String QUESTIONS = "questions";
    final static String QUESTIONS_ID = "questions_id";
    final static String TITLE = "title";
    final static String NUMBER_OF_ANSWERS = "numberOfAnswers";
    final static String ANSWERS = "answers";
    final static String ANSWERS_ID = "answers_id";
    final static String CORRECT_ANSWER = "correct";

    SQLiteDatabase sqlDb = this.getWritableDatabase();

    public DatabaseManager(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + QUSTIONS_TABLE_DETAILS + "" +
                "( " + QUESTIONS_ID +" INTEGER PRIMARY KEY," +
                "" + DATE + " VARCHAR," +
                "" + READING + " VARCHAR," +
                "" + NUMBER_OF_QUESTIONS + " INTEGER )");

        db.execSQL("CREATE TABLE " + QUSTIONS_TABLE + "" +
                "( id INTEGER PRIMARY KEY," +
                "" + TITLE + " VARCHAR," +
                "" + NUMBER_OF_ANSWERS + " INTEGER," +
                "" + QUESTIONS_ID + " INTEGER," +
                "" + ANSWERS_ID + "INTEGER, " +
                "" + CORRECT_ANSWER + " INTEGER," +
                "FOREIGN KEY("+QUESTIONS_ID+") REFERENCES "+QUSTIONS_TABLE_DETAILS+"("+QUESTIONS_ID+"))");

        db.execSQL("CREATE TABLE " + ANSWERS_TABLE + "" +
                "( id INTEGER PRIMARY KEY," +
                "" + ANSWERS + " VARCHAR," +
                "" + ANSWERS_ID + " INTEGER, " +
                "FOREIGN KEY(" + ANSWERS_ID + ") REFERENCES " + QUSTIONS_TABLE + "(" + ANSWERS_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF EXISTS " + ANSWERS_TABLE);
        db.execSQL("DROP IF EXISTS " + QUSTIONS_TABLE);
        db.execSQL("DROP IF EXISTS " + QUSTIONS_TABLE_DETAILS);
        onCreate(db);
    }

    public void insertIntoQustionsDetails(String date, String reading, Integer numberOfQuestions){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(READING, reading);
        contentValues.put(NUMBER_OF_QUESTIONS, numberOfQuestions);
        sqlDb.insert(QUSTIONS_TABLE_DETAILS, null, contentValues);
    }

    public void insertIntoQuestions(String title, Integer numberOfAnswers,Integer questionsId, Integer answerId, Integer correctAnswer){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(NUMBER_OF_ANSWERS, numberOfAnswers);
        contentValues.put(QUESTIONS_ID, questionsId);
        contentValues.put(ANSWERS_ID, answerId);
        contentValues.put(CORRECT_ANSWER, correctAnswer);
        sqlDb.insert(QUSTIONS_TABLE, null, contentValues);
    }

    public void insertIntoAnswers(String answer, Integer answerId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ANSWERS, answer);
        contentValues.put(ANSWERS_ID, answerId);
        sqlDb.insert(ANSWERS_TABLE, null, contentValues);
    }

    public int getMaxId(String table){
        int maxId = 0;
        Cursor cursor = sqlDb.rawQuery("SELECT MAX(id) as max FROM " + table, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            if (cursor.getString(cursor.getColumnIndex("max")) != null) {
                maxId = cursor.getInt(cursor.getColumnIndex("max"));
            }
        }
        cursor.close();
        return maxId;
    }

    public int getMaxIdQuestionDetails(){
        int maxId = 0;
        Cursor cursor = sqlDb.rawQuery("SELECT MAX(" + QUESTIONS_ID + ") as max FROM " + QUSTIONS_TABLE_DETAILS, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            if (cursor.getString(cursor.getColumnIndex("max")) != null) {
                maxId = cursor.getInt(cursor.getColumnIndex("max"));
            }
        }
        cursor.close();
        return maxId;
    }


}