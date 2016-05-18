package com.example.mferraco.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mferraco.popularmovies.interfaces.DetailsCallback;
import com.example.mferraco.popularmovies.interfaces.ReviewDetailsCallback;
import com.example.mferraco.popularmovies.responseModels.Movie;
import com.example.mferraco.popularmovies.responseModels.Review;

public class MoviesActivity extends AppCompatActivity implements DetailsCallback, ReviewDetailsCallback {

    private static final String TAG = MoviesActivity.class.getSimpleName();

    public static final String MOVIE_OBJECT_KEY = "movieObjectKey";

    private static final String MOVIE_DETAILS_FRAGMENT_TAG = "movieDetailsTag";

    public static final String IS_TABLET_LAYOUT_TAG = "isTabletLayout";

    private boolean isTabletLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if (findViewById(R.id.movie_detail_container) != null) {
            isTabletLayout = true;
        } else {
            isTabletLayout = false;
        }

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putBoolean(IS_TABLET_LAYOUT_TAG, isTabletLayout);

            // add the list fragment to the layout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_list_container, MoviesFragment.newInstance(args), MOVIE_DETAILS_FRAGMENT_TAG)
                    .commit();
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

    @Override
    public void onItemSelected(Movie movie) {
        if (isTabletLayout) {
            // load the movie data into the fragment on this screen
            Bundle args = new Bundle();
            args.putParcelable(MOVIE_OBJECT_KEY, movie);

            MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        } else {
            // moves to the movie details screen
            Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
            movieDetailsIntent.putExtra(MOVIE_OBJECT_KEY, movie);
            this.startActivity(movieDetailsIntent);
        }
    }

    @Override
    public void onItemSelected(Review review) {
        if (isTabletLayout) {
            Bundle args = new Bundle();
            args.putParcelable(ReviewDetailsFragment.REVIEW_OBJECT_KEY, review);

            ReviewDetailsFragment fragment = ReviewDetailsFragment.newInstance(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }
}
