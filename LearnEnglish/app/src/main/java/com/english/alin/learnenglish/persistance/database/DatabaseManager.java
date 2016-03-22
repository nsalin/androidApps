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
public class DatabaseManager extends SQLiteOpenHelper{
    final static String DATABASE_NAME = "learnenglish";
    final static String READING_TABLE = "reading";
    final static String READING_ID = "reading_id";
    final static String READING = "reading";
    final static String QUIZ_TABLE = "quiz";
    final static String QUIZ_ID = "quiz_id";
    final static String DATE = "created_date";
    final static String QUIZ_TITLE = "quizTitle";
    final static String QUSTIONS_TABLE = "questions";
    final static String QUESTIONS_ID = "questions_id";
    final static String QUESTION = "question";
    final static String ANSWERS_TABLE = "answers";
    final static String ANSWERS = "answer_text";
    final static String ANSWERS_ID = "answers_id";
    final static String CORRECT_ANSWER = "isCorrect";
    final static String SEQUENCE_NUMBER = "sequenceNo";

    SQLiteDatabase sqlDb = this.getWritableDatabase();

    public DatabaseManager(Context context){
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("App-onCreateDB: ", "onCreate");
        String createReadingTable = "CREATE TABLE " + READING_TABLE + "" +
                "( " + READING_ID + " INTEGER PRIMARY KEY, " +
                "" + READING + " VARCHAR )";
        String createQuizTable = "CREATE TABLE " + QUIZ_TABLE + "" +
                "( " + QUIZ_ID +" INTEGER PRIMARY KEY," +
                "" + DATE + " VARCHAR," +
                "" + READING_ID + " VARCHAR, " +
                "FOREIGN KEY(" + READING_ID + ") REFERENCES " + READING_TABLE + "(" + READING_ID + "))";
        String createQuestionsTable = "CREATE TABLE " + QUSTIONS_TABLE + "" +
                "( id INTEGER PRIMARY KEY," +
                "" + QUESTIONS_ID + " INTEGER," +
                "" + QUIZ_ID + " INTEGER," +
                "" + QUESTION + " INTEGER, " +
                "FOREIGN KEY(" + QUIZ_ID + ") REFERENCES "+ QUIZ_TABLE +"(" + QUIZ_ID + "))";

        String createAnswersTable = "CREATE TABLE " + ANSWERS_TABLE + "" +
                "( id INTEGER PRIMARY KEY," +
                "" + ANSWERS + " VARCHAR," +
                "" + ANSWERS_ID + " INTEGER, " +
                "" + CORRECT_ANSWER + "INTEGER, " +
                "" + QUESTIONS_ID + "INTEGER, " +
                "" + SEQUENCE_NUMBER + "INTEGER, " +
                "FOREIGN KEY(" + QUESTIONS_ID + ") REFERENCES " + QUSTIONS_TABLE + "(" + QUESTIONS_ID + "))";

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
        onCreate(db);
    }

    public void insertIntoQustionsDetails(String date, String reading, Integer numberOfQuestions){
        Log.i("App-DB: ", "insertIntoQustionsDetails");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(READING, reading);
        contentValues.put(NUMBER_OF_QUESTIONS, numberOfQuestions);
        sqlDb.insert(QUIZ_TABLE, null, contentValues);
    }

    public void insertIntoQuestions(String title, Integer numberOfAnswers,Integer questionsId, Integer answerId, Integer correctAnswer){
        Log.i("App-DB: ", "insertIntoQuestions");

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(NUMBER_OF_ANSWERS, numberOfAnswers);
        contentValues.put(QUESTIONS_ID, questionsId);
        contentValues.put(ANSWERS_ID, answerId);
        contentValues.put(CORRECT_ANSWER, correctAnswer);
        sqlDb.insert(QUSTIONS_TABLE, null, contentValues);
    }

    public void insertIntoAnswers(String answer, Integer answerId){
        Log.i("App-DB: ", "insertIntoAnswers");

        ContentValues contentValues = new ContentValues();
        contentValues.put(ANSWERS, answer);
        contentValues.put(ANSWERS_ID, answerId);
        sqlDb.insert(ANSWERS_TABLE, null, contentValues);
    }

    public int getMaxId(String table){
        Log.i("App-DB: ", "getMaxId");

        int maxId = 0;
        Cursor cursor = sqlDb.rawQuery("SELECT MAX(id) as max FROM " + table, null);
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

    public int getMaxIdQuestionDetails(){
        Log.i("App-DB: ", "getMaxIdQuestionDetails");

        int maxId = 0;
        Cursor cursor = sqlDb.rawQuery("SELECT MAX(" + QUESTIONS_ID + ") as max FROM " + QUIZ_TABLE, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            if (cursor.getString(cursor.getColumnIndex("max")) != null) {
                maxId = cursor.getInt(cursor.getColumnIndex("max"));
            }
            cursor.moveToNext();
        }
        cursor.close();
        Log.i("App-DB: ", "getMaxIdQuestionDetails end" + maxId);
        return maxId;
    }


}
