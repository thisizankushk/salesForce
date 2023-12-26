package com.example.salesforce.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salesforce.R;

public class WebViewActivity extends AppCompatActivity {

    WebView myWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        myWebView = findViewById(R.id.web_view);

        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.clearHistory();
        myWebView.loadUrl("http://unnatisales.akshapp.com/login/accountdeletion");
    }
}