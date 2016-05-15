package com.example.mferraco.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mferraco on 4/2/16.
 *
 * This activity displays the details for a Movie
 */
public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceStates == null) {

            // Create MovieDetailsFragment and add it to the activity using a fragment transaction
            Bundle args = new Bundle();
            args.putParcelable(MoviesActivity.MOVIE_OBJECT_KEY, getIntent().getParcelableExtra(MoviesActivity.MOVIE_OBJECT_KEY));
            MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }
}
