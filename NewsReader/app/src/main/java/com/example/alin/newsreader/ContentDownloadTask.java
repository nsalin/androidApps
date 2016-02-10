package com.example.alin.newsreader;

import android.os.AsyncTask;

/**
 * Created by Alin on 2/10/2016.
 */
public class ContentDownloadTask extends AsyncTask<String, Void, String> {
    public static String webPageContent;
    @Override
    protected String doInBackground(String... params) {

        return FactoryMethods.getContent(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        webPageContent = s;
    }
}
