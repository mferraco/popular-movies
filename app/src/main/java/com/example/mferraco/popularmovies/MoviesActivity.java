package com.example.mferraco.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.mferraco.popularmovies.adapters.ImageAdapter;
import com.example.mferraco.popularmovies.requestTasks.AsyncGetMoviesResponse;
import com.example.mferraco.popularmovies.requestTasks.GetMoviesTask;
import com.example.mferraco.popularmovies.responseModels.Movie;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity implements AsyncGetMoviesResponse {

    private static final String TAG = MoviesActivity.class.getSimpleName();

    private static final String CURRENT_SORT_ORDER_KEY = "mCurrentSortOrder";
    private static final String FIRST_REQUEST_KEY = "mFirstRequest";

    private GridView mImageGridView;

    private String mCurrentSortOrder;

    private boolean mFirstRequest = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if (savedInstanceState != null) {
            mCurrentSortOrder = savedInstanceState.getString(CURRENT_SORT_ORDER_KEY, getString(R.string.settings_sort_order_default));
        } else {
            mCurrentSortOrder = getString(R.string.settings_sort_order_default);
        }

        mImageGridView = (GridView) findViewById(R.id.movie_grid_view);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mImageGridView.setNumColumns(4);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrderPreference = sharedPref.getString(getString(R.string.settings_sort_order_key), getString(R.string.settings_sort_order_default));

        if (!(mCurrentSortOrder.equalsIgnoreCase(sortOrderPreference)) || mFirstRequest) {
            // always make the request the first time the activity loads
            // after that only make the request if the sort order changes
            mFirstRequest = false;
            mCurrentSortOrder = sortOrderPreference;

            GetMoviesTask getMoviesTask = new GetMoviesTask(this);
            getMoviesTask.delegate = this;
            getMoviesTask.execute(mCurrentSortOrder);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /* AsyncGetMoviesResponse Interface */

    @Override
    public void processFinish(ArrayList<Movie> movies) {
        // set up image adapter here
        mImageGridView.setAdapter(new ImageAdapter(this, movies));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putSerializable(CURRENT_SORT_ORDER_KEY, mCurrentSortOrder);
    }
}
