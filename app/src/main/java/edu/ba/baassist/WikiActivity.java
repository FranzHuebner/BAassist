package edu.ba.baassist;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WikiActivity extends Activity {

    private WebView webView;
    String WikiURL = "http://raw.githubusercontent.com/FranzHuebner/BAassist/master/ba_wiki_host";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        boolean reachWiki=false;
        try {
            reachWiki = connAdapter.isReachable(WikiURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
      
        if(reachWiki){
            webView = (WebView) findViewById(R.id.webView1);
            webView.getSettings().setJavaScriptEnabled(false);
            webView.loadUrl(WikiURL);
        }else{
//            Toast toast = Toast.makeText(this, '4', Toast.LENGTH_SHORT);
//            toast.show();
            webView = (WebView) findViewById(R.id.webView1);
            webView.getSettings().setJavaScriptEnabled(false);
            webView.loadUrl("http://www.google.de/");
       }

    }



}

