package com.example.mferraco.popularmovies;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        GetMoviesTask getMoviesTask = new GetMoviesTask(this);
        getMoviesTask.delegate = this;
        getMoviesTask.execute("popular");
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
        GridView imageGridView = (GridView) findViewById(R.id.movie_grid_view);
        imageGridView.setAdapter(new ImageAdapter(this, movies));
    }
}
