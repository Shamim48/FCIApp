package com.fci.shamim.fci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AcademicResultView extends AppCompatActivity {
    WebView webViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_result_view);

        webViewId = findViewById(R.id.webViewId);

        WebSettings webSettings = webViewId.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webViewId.setWebViewClient(new WebViewClient());

        webViewId.loadUrl("http://fci.gov.bd/result.php");
    }

    @Override
    public void onBackPressed() {
        if (webViewId.canGoBack()){
            webViewId.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
