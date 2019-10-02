package com.fci.shamim.fci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Location extends AppCompatActivity {
    WebView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        location = findViewById(R.id.locationId);

        WebSettings webSettings = location.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        location.setWebViewClient(new WebViewClient());

        location.loadUrl("https://goo.gl/maps/v2wuxhUiAehR1aA86");
    }

    @Override
    public void onBackPressed() {
        if (location.canGoBack()){
            location.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
