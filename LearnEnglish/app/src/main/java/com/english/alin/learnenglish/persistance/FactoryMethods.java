package com.english.alin.learnenglish.persistance;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.english.alin.learnenglish.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Alin on 2/10/2016.
 */
public class FactoryMethods {

    public static boolean isOnline(MainActivity mainActivity) {
        ConnectivityManager cm =
                (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public static String streamToString(InputStream is) throws IOException {
        Log.i("STEP", "streamToString");
        StringBuilder content =  new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null){
            content.append(line);
        }
        return content.toString();
    }

    public static String getContent(String param) {
        Log.i("STEP", "GetContent");
        HttpURLConnection connection = null;
        String content = "";
        try {
            URL url = new URL(param);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream is = connection.getInputStream();
            content = FactoryMethods.streamToString(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return content;
    }

    public static String formatDate(Date date){
        return new SimpleDateFormat("DD-MM-YYYY").format(date);
    }

}
