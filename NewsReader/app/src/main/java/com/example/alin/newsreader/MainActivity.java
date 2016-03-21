package com.example.alin.newsreader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> newsListArray = new ArrayList<>();
    public static ArrayAdapter<String> newsListAdapter;
    final static String allNewsURL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    boolean loadingMore = false;
    WebDownloadTask webDownloadTask;
    ListView listView;
    Intent intent;
    boolean offline = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        intent = new Intent(this, WebViewActivity.class);
        final DatabaseManager databaseManager = new DatabaseManager(this);
        if (FactoryMethods.isOnline(this)) {

            newsListArray.clear();
            webDownloadTask = new WebDownloadTask(allNewsURL);
            webDownloadTask.setCallback(new AsyncResponse<String>() {
                @Override
                public void onResponse(Map<String, String> response) {
                    Log.i("STEP", "setCallback");
                    for (LinkedHashMap.Entry entry : WebDownloadTask.articles.entrySet()) {
                        newsListArray.add(entry.getKey().toString());
                    }
                    newsListAdapter.notifyDataSetChanged();
                    Log.i("STEP", "newsArralist size" + newsListArray.size());
                    Log.i("STEP", "notified");
                }

            });

            webDownloadTask.execute(allNewsURL);
            Log.i("STEP", "webDownloadTask.execute" + allNewsURL);

        } else {

            newsListArray = databaseManager.getAllArticles();
//            newsListAdapter.notifyDataSetChanged();
            offline = true;
        }



        newsListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newsListArray);
        listView.setAdapter(newsListAdapter);
        Log.i("STEP", "adapted");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleName, articleURL;
                Integer articleID;

//                if (offline){
////                    articleName = databaseManager
//                }
                articleName =  (new ArrayList<>(WebDownloadTask.articles.keySet()).get(position));
                articleURL =  (new ArrayList<>(WebDownloadTask.articles.values()).get(position));
                articleID = WebDownloadTask.articlesIDS.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("articleName", articleName);
                bundle.putString("articleURL", articleURL);
                bundle.putInt("articleID", articleID);



                System.out.println(articleName + " " + articleURL);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int lastElementInScreen = firstVisibleItem + visibleItemCount;
//                if ((lastElementInScreen == totalItemCount) && !(loadingMore)) {
//                    Thread thread = new Thread(null, loadMoreListItems);
//                    thread.start();
//                }
//            }
//        });
//    }
//
//    private Runnable loadMoreListItems = new Runnable() {
//        @Override
//        public void run() {
//            loadingMore = true;
//            //newsListArray = new ArrayList<>();
//
//            runOnUiThread(returnResponse);
//        }
//    };
//
//    private Runnable returnResponse = new Runnable() {
//        @Override
//        public void run() {
//            loadMoreArticles();
//        }
//    };
//
//
//    private void loadMoreArticles(){
//        webDownloadTask = new WebDownloadTask(allNewsURL);
//        webDownloadTask.setCallback(new AsyncResponse<String>() {
//            @Override
//            public void onResponse(Map<String, String> response) {
//                Log.i("STEP", "setCallback");
//                for (LinkedHashMap.Entry entry : WebDownloadTask.articles.entrySet()) {
//                    newsListArray.add(entry.getKey().toString());
//                }
//                newsListAdapter.notifyDataSetChanged();
//                Log.i("STEP", "newsArralist size" + newsListArray.size());
//                Log.i("STEP", "notified");
//            }
//
//        });
//        loadingMore = false;
//    }
    }
}


