package com.english.alin.learnenglish.persistance.database;

/**
 * Created by alinnitu on 3/24/2016.
 */
public class DatabaseConstants {
    final static String DATABASE_NAME = "learnenglish_v2";
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

    final static String createReadingTable = "CREATE TABLE " + READING_TABLE + "" +
            "( " + READING_ID + " INTEGER PRIMARY KEY, " +
            "" + READING + " VARCHAR )";

    final static String createQuizTable = "CREATE TABLE " + QUIZ_TABLE + "" +
            "( " + QUIZ_ID +" INTEGER PRIMARY KEY," +
            "" + DATE + " VARCHAR," +
            "" + QUIZ_TITLE + " VARCHAR," +
            "" + READING_ID + " VARCHAR, " +
            "FOREIGN KEY(" + READING_ID + ") REFERENCES " + READING_TABLE + "(" + READING_ID + "))";

    final static String createQuestionsTable = "CREATE TABLE " + QUSTIONS_TABLE + "" +
            "( " + QUESTIONS_ID + " INTEGER PRIMARY KEY," +
            "" + QUIZ_ID + " INTEGER," +
            "" + QUESTION + " VARCHAR, " +
            "FOREIGN KEY(" + QUIZ_ID + ") REFERENCES "+ QUIZ_TABLE +"(" + QUIZ_ID + "))";

   final static String createAnswersTable = "CREATE TABLE " + ANSWERS_TABLE + "" +
           "( " + ANSWERS_ID + " INTEGER PRIMARY KEY, " +
           "" + ANSWERS + " VARCHAR," +
            "" + CORRECT_ANSWER + " INTEGER, " +
            "" + QUESTIONS_ID + " INTEGER, " +
            "" + SEQUENCE_NUMBER + " INTEGER, " +
            "FOREIGN KEY(" + QUESTIONS_ID + ") REFERENCES " + QUSTIONS_TABLE + "(" + QUESTIONS_ID + "))";

    public enum CorrectAnswer {
        TRUE(1),
        FALSE(0);

        private int correct_answer_value;
        CorrectAnswer(int correct_answer_value){
            this.correct_answer_value = correct_answer_value;
        }
        public int correct_answer_value(){
            return correct_answer_value;
        }
    }

    public enum PrimaryKeyColumns{
        READING_ID, QUIZ_ID, QUESTIONS_ID, ANSWERS_ID
    }

    public enum Tables{
        reading, quiz, answers, questions
    }

}
