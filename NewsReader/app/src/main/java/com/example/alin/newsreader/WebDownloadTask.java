package com.example.alin.newsreader;

import android.os.AsyncTask;
import android.util.Log;

import com.example.alin.newsreader.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Alin on 2/6/2016.
 */
public class WebDownloadTask extends AsyncTask<String, Void, LinkedHashMap<String, String>> {
    final static String newsItem = "https://hacker-news.firebaseio.com/v0/item/";
    final static String jsonExtension = ".json";
    final static String hackerNewsDefaultArticleURL = "https://news.ycombinator.com/item?id=";
    public static Map<String, String> articles = new HashMap<>();
    public static ArrayList<Integer> articlesIDS = new ArrayList<>();
    int startIndex = 0;
    int maxIndex = 0;
    private String url;
    private AsyncResponse<String> callback;

    public WebDownloadTask(String url) {
        this.url = url;
    }

    public void setCallback(AsyncResponse<String> callback) {
        this.callback = callback;
    }

    @Override
    protected LinkedHashMap<String, String> doInBackground(String... params) {

        Log.i("STEP", "DOIN BACKGROUND P");
        loadItems(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(LinkedHashMap<String, String> stringMap) {
        Log.i("STEP", "OnPostExecute");
        super.onPostExecute(stringMap);
        if (callback != null) {
            callback.onResponse(stringMap);
        } else {
            Log.w(WebDownloadTask.class.getSimpleName(), "The response was ignored");
        }
    }


    private Map loadItems(String params) {
        Log.i("STEP", "loadItem");
        class DownloadJSONObject extends AsyncTask<String, Void, JSONObject> {

            @Override
            protected JSONObject doInBackground(String... params) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(FactoryMethods.getContent(params[0]));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonObject;
            }
        }

        try {
            String articleIDS = FactoryMethods.getContent(params);
            ArrayList<Integer> jsonData = convertJSONArrayToArrayList(articleIDS);
            int jsonDataSize = jsonData.size();
            maxIndex += 10;

            if (maxIndex <= jsonDataSize) {
                for (int i = 0; i < jsonData.subList(startIndex, maxIndex).size(); i++) {
                    JSONObject jsonObject = new DownloadJSONObject().doInBackground(newsItem
                            + jsonData.get(i).toString() + jsonExtension);

                    String title = jsonObject.getString("title");
                    String articleURL = title.startsWith("Ask HN") ? hackerNewsDefaultArticleURL + i : jsonObject.getString("url");
                    articles.put(title, articleURL);
                    articlesIDS.add(jsonData.get(i));

                    System.out.println("ArticlesData -> " + title + " " + articleURL);
                }
                startIndex += maxIndex;

            }
            return articles;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Integer> convertJSONArrayToArrayList(String content) {
        Log.i("STEP", "convertJSONArrayToArrayList");
        ArrayList<Integer> listdata = new ArrayList<>();
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(content);
            if (jArray != null) {
                for (int i = 0; i < jArray.length(); i++) {
                    listdata.add((Integer) jArray.get(i));
                }
            }
            return listdata;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}

