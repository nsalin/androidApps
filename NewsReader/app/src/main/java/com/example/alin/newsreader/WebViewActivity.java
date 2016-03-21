package com.example.alin.newsreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        final ProgressDialog dialog = new ProgressDialog(this);


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
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url,Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                dialog.setMessage("Loading ...");
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

    }
}
