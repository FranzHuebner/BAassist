package edu.ba.baassist;

import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WikiActivity extends BaseActivity {

    private WebView webView;
    private String WikiURL = "https://raw.githubusercontent.com/FranzHuebner/BAassist/master/ba_wiki_host";
    char test = 'a';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        LayoutInflater inflater = getLayoutInflater();
        getWindow().addContentView(inflater.inflate(R.layout.activity_navigation, null), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(checkConnection(WikiURL)) {
            webView = (WebView) findViewById(R.id.webView1);
            webView.getSettings().setJavaScriptEnabled(false);
            webView.loadUrl(WikiURL);
        }else{
//            Toast toast = Toast.makeText(this, '4', Toast.LENGTH_SHORT);
//            toast.show();
            webView = (WebView) findViewById(R.id.webView1);
            webView.getSettings().setJavaScriptEnabled(false);
            webView.loadUrl("https://www.google.de/");
        }

    }

    public static boolean checkConnection(String urlString) {
        InputStream stream = null;
        try {
            URL url = new URL(urlString);
            URLConnection con = url.openConnection();

            stream = con.getInputStream();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                }
        }


    }
}

