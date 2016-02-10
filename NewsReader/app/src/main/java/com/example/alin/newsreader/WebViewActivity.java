package com.example.alin.newsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = (WebView) findViewById(R.id.webView);
        ContentDownloadTask task = new ContentDownloadTask();
        DatabaseManager dbm = new DatabaseManager(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String articleName = bundle.getString("articleName");
        String articleURL = bundle.getString("articleURL");
        Integer articleID = bundle.getInt("articleID");
        task.execute(articleURL);
        String content = ContentDownloadTask.webPageContent;
        dbm.insertArticle(articleID, articleName, articleURL, content);




        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(articleName);
        }
        System.out.println(articleName + " " + articleURL);

        webView.loadUrl(articleURL);

    }
}
