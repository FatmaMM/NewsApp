package com.example.android.newsapp;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by lenovo on 23/06/2017.
 */

public class NewsLoader  extends AsyncTaskLoader<List<News>> {
    /** Tag for log messages */

    private static final String LOG_TAG = NewsLoader.class.getName();
    /** Query URL */
    private String mUrl;

    public NewsLoader(Context context,String url) {
        super(context);
        mUrl=url;
    }


    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"TEST: onStartLoading() called...");
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<News> listOfNews = null;

        listOfNews = QueryUtils.fetchData(mUrl);
        return listOfNews;
    }
}
