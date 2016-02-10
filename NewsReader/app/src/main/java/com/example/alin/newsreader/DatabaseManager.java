package com.example.alin.newsreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Alin on 2/10/2016.
 */
public class DatabaseManager extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "HACKER_NEWS";
    final static String TABLE_NAME = "articles";
    final static String ARTICLE_ID = "article_id";
    final static String TITLE = "title";
    final static String ARTICLE_ADDRESS = "article_address";
    final static String CONTENT_PAGE = "content";
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

    public DatabaseManager(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "" +
                "( id INTEGER PRIMARY KEY," +
                "" + ARTICLE_ID + " INTEGER," +
                "" + TITLE + " VARCHAR," +
                "" + ARTICLE_ADDRESS + " VARCHAR," +
                "" + CONTENT_PAGE + " VARCHAR )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF EXISTS articles");
        onCreate(db);
    }

    public boolean insertArticle(Integer articleID,String title, String url, String content){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ARTICLE_ID, articleID);
        contentValues.put(TITLE, title);
        contentValues.put(ARTICLE_ADDRESS, url);
        contentValues.put(CONTENT_PAGE, content);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertContentById(Integer articleID, String content){
            sqLiteDatabase.execSQL("UPDATE "+TABLE_NAME+" SET "+ CONTENT_PAGE + "="+content+" WHERE "+ARTICLE_ID+"= "+articleID+";");
        return true;
    }

    public Integer deleteArticle(Integer articleID){
        return sqLiteDatabase.delete(TABLE_NAME, ARTICLE_ID + "= ?", new String[] {Integer.toString(articleID)});
    }

    public ArrayList<String> getAllArticles(){
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(TITLE)));
            res.moveToNext();
        }
        return arrayList;
    }

}
