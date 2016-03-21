package com.english.alin.learnenglish.persistance;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import static com.english.alin.learnenglish.persistance.FactoryMethods.getContent;

/**
 * Created by Alin on 3/19/2016.
 */
public class DownloadTask extends AsyncTask<String, Void, JSONObject> {
    private final static String REQUEST_URL = "http://demo3296892.mockable.io/quiz";
    @Override
    protected JSONObject doInBackground(String[] params) {
        System.out.println("m-am executat");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getContent(REQUEST_URL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        return jsonObject;
    }
}
