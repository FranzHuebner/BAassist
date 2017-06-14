package edu.ba.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import edu.ba.baassist.R;

/**
 * Created by richa on 14.06.2017.
 */

public class WikiFragment extends Fragment {

    public WebView mWebView;
    String WikiURL = "https://raw.githubusercontent.com/FranzHuebner/BAassist/master/ba_wiki_host";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_wiki, container, false);


        mWebView = (WebView) rootView.findViewById(R.id.webView1);
        mWebView.loadUrl(WikiURL);


        return rootView;
    }
}
