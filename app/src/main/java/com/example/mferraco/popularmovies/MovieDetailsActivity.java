package com.example.mferraco.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mferraco.popularmovies.adapters.ImageAdapter;
import com.example.mferraco.popularmovies.responseModels.Movie;

/**
 * Created by mferraco on 4/2/16.
 *
 * This activity displays the details for a Movie
 */
public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    private Movie mMovie;

    @Override
    public void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);

        mMovie = getIntent().getParcelableExtra(ImageAdapter.MOVIE_OBJECT_KEY);

        Log.d(TAG, "MOVIEEEE");
        Log.d(TAG, mMovie.getTitle());
    }
}
