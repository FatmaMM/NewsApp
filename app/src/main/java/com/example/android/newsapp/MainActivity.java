package com.example.android.newsapp;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final String GUARDIANAPIS_REQUEST_URL = "https://content.guardianapis.com/search?api-key=a6e1ce43-2d1b-44f7-8f58-37542d499bf5";
    public static final String LOG_TAG = MainActivity.class.getName();
    private NewsAdapter adapter;
    private static int LOADER_ID = 1;
    @BindView(R.id.list)
    ListView listView;

    ProgressDialog progDailog;
    private String mSearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST:Main activity onCreate() is called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new NewsAdapter(this, new ArrayList<News>());

        progDailog = new ProgressDialog(MainActivity.this);
        Log.i(LOG_TAG, "TEST:InitLoader()...");
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
            //      mEmptyStateTextView.setVisibility(View.GONE);

        } else {
            progDailog.hide();
        }
        listView.setAdapter(adapter);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
        Uri baseUri = Uri.parse(GUARDIANAPIS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        if (mSearchQuery != null)
            uriBuilder.appendQueryParameter("q", mSearchQuery);
        return new NewsLoader(MainActivity.this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> data) {

        progDailog.hide();
        // Clear the adapter of previous books data
        adapter.clear();
        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.

        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        } else {
            TextView text = new TextView(this);
            text.setText("No results found ");
            listView.setEmptyView(text);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {
        Log.i(LOG_TAG, "TEST:calling onLoaderReset()...");
        // Loader reset, so we can clear out our existing data.
        adapter.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchQuery = query;
                getLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                MainActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
