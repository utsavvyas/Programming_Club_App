package programmingclub.daiict.classes.tech_news_classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import programmingclub.daiict.R;

/**
 * Created by omkar13 on 1/7/2016.
    This activity is created to show the website whose link has been clicked in the list view of the various fragments linke software, open source , etc.
 */


public class WebViewActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);
        WebView w = (WebView) findViewById(R.id.webView1);

        Intent i=getIntent();
        String url = i.getStringExtra("url");

        w.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }});

        WebSettings ws = w.getSettings();
        ws.setJavaScriptEnabled(true);
        w.loadUrl(url);

    }
}