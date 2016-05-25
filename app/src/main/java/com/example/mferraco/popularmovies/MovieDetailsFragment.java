package com.example.mferraco.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mferraco.popularmovies.adapters.ReviewListAdapter;
import com.example.mferraco.popularmovies.adapters.TrailerListAdapter;
import com.example.mferraco.popularmovies.data.FavoriteMoviesContract;
import com.example.mferraco.popularmovies.requestTasks.AsyncGetReviewsResponse;
import com.example.mferraco.popularmovies.requestTasks.AsyncGetTrailersResponse;
import com.example.mferraco.popularmovies.requestTasks.GetReviewsTask;
import com.example.mferraco.popularmovies.requestTasks.GetTrailersTask;
import com.example.mferraco.popularmovies.responseModels.Movie;
import com.example.mferraco.popularmovies.responseModels.Review;
import com.example.mferraco.popularmovies.responseModels.Trailer;
import com.example.mferraco.popularmovies.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This Fragment displays the details for a particular movie.  These details could be on their
 * own screen (on smaller devices) or on half of the screen sext to the list of movies (on larger
 * devices such as tablets).
 */
public class MovieDetailsFragment extends Fragment implements AsyncGetTrailersResponse, AsyncGetReviewsResponse {

    private static final String TAG = MovieDetailsFragment.class.getSimpleName();

    private Movie mMovie;

    // UI Elements
    private TextView title;
    private ImageView thumbnail;
    private LinearLayout trailersLayout;
    private LinearLayout reviewsLayout;
    private ImageView favoriteButton;
    private ProgressBar trailersProgressBar;
    private ProgressBar reviewsProgressBar;

    public static MovieDetailsFragment newInstance(Bundle args) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Bundle args = getArguments();
        if (args != null) {
            trailersProgressBar = (ProgressBar) rootView.findViewById(R.id.trailers_progress_bar);
            reviewsProgressBar = (ProgressBar) rootView.findViewById(R.id.reviews_progress_bar);

            mMovie = args.getParcelable(MoviesActivity.MOVIE_OBJECT_KEY);

            title = (TextView) rootView.findViewById(R.id.movie_title_text);
            title.setText(mMovie.getOriginalTitle());

            thumbnail = (ImageView) rootView.findViewById(R.id.movie_thumbnail_image);
            String basePath = getString(R.string.image_base_path);
            String size = getString(R.string.image_size_w342);
            String posterPath = mMovie.getPosterPath();
            Picasso.with(getContext()).load(basePath + size + posterPath).into(thumbnail);


            String[] fieldTitles = getResources().getStringArray(R.array.movie_details_fields_title);

            // Release Date
            TextView releaseDateHeading = (TextView) rootView.findViewById(R.id.release_date_heading_text);
            releaseDateHeading.setText(fieldTitles[0]);
            TextView releaseDateValue = (TextView) rootView.findViewById(R.id.release_date_text);
            releaseDateValue.setText(mMovie.getReleaseDate());

            // Vote Average
            TextView voteAverageHeading = (TextView) rootView.findViewById(R.id.vote_average_heading_text);
            voteAverageHeading.setText(fieldTitles[1]);
            TextView voteAverageValue = (TextView) rootView.findViewById(R.id.vote_average_text);
            voteAverageValue.setText(String.valueOf(mMovie.getVoteAverage()));

            // Plot Synopsis (overview)
            TextView overviewHeading = (TextView) rootView.findViewById(R.id.overview_heading_text);
            overviewHeading.setText(fieldTitles[2]);
            TextView overviewValue = (TextView) rootView.findViewById(R.id.overview_text);
            overviewValue.setText(mMovie.getOverview());

            // Favorite Button
            favoriteButton = (ImageView) rootView.findViewById(R.id.favorite_btn);
            if (isAlreadyFavorited(mMovie)) {
                favoriteButton.setImageResource(R.drawable.ic_star_icon_filled);
            } else {
                favoriteButton.setImageResource(R.drawable.ic_star_icon_outline);
            }

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleFavoriteButtonTap();
                }
            });

            trailersLayout = (LinearLayout) rootView.findViewById(R.id.movie_trailers);
            // make the request for the trailers
            makeTrailersRequest();

            reviewsLayout = (LinearLayout) rootView.findViewById(R.id.movie_reviews);
            // make the request for the reviews
            makeReviewsRequest();
        }

        return rootView;
    }

    /**
     * Makes the request for the trailers for the movie that this detail fragment is showing
     * details for.  Will notify the user via a Toast if there is no network connection.
     */
    private void makeTrailersRequest() {
        if (AppUtils.isOnline(getContext())) {
            shouldShowTrailersProgressBar(true);

            GetTrailersTask getTrailersTask = new GetTrailersTask(getContext());
            getTrailersTask.delegate = this;
            Log.d(TAG, "EXECUTING TRAILERS API REQUEST");
            getTrailersTask.execute(mMovie.getId());
        } else {
            Toast.makeText(getContext(), getString(R.string.no_network_dialog_title), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Makes the request for the review for the movie that this detail fragment is showing
     * details for.  Will notify the user via a Toast if there is no network connection.
     */
    private void makeReviewsRequest() {
        if (AppUtils.isOnline(getContext())) {
            shouldShowReviewsProgressBar(true);

            GetReviewsTask getReviewsTask = new GetReviewsTask(getContext());
            getReviewsTask.delegate = this;
            Log.d(TAG, "EXECUTING REVIEWS API REQUEST");
            getReviewsTask.execute(mMovie.getId());
        } else {
            Toast.makeText(getContext(), getString(R.string.no_network_dialog_title), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processTrailersResponse(ArrayList<Trailer> trailers) {
        shouldShowTrailersProgressBar(false);

        TrailerListAdapter trailerListAdapter = new TrailerListAdapter(getContext(), trailers);

        // add the views from the adapter to the LinearLayout
        for (int i = 0; i < trailerListAdapter.getCount(); i++) {
            View viewToAdd = trailerListAdapter.getView(i, null, null);
            trailersLayout.addView(viewToAdd);
        }
    }

    @Override
    public void processReviewsResponse(ArrayList<Review> reviews) {
        shouldShowReviewsProgressBar(false);

        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(getContext(), reviews);

        // add the views from the adapter to the LinearLayout
        for (int i = 0; i < reviewListAdapter.getCount(); i++) {
            View viewToAdd = reviewListAdapter.getView(i, null, null);
            reviewsLayout.addView(viewToAdd);
        }
    }

    /**
     * Contains logic for handling the tap of the favorite movie button
     *
     * @return True if the db action was successful, false otherwise
     */
    private void handleFavoriteButtonTap() {
        boolean alreadyFavorited = isAlreadyFavorited(mMovie);

        if (alreadyFavorited) {
            // delete from db
            if (removeFromFavorites(mMovie)) {
                favoriteButton.setImageResource(R.drawable.ic_star_icon_outline);
            }
        } else {
            // insert into db
            if (addToFavorites(mMovie)) {
                favoriteButton.setImageResource(R.drawable.ic_star_icon_filled);
            }
        }
    }

    /**
     * Adds a movie to the favorites table in the DB
     *
     * @param movie The movie to add to the favorites
     * @return True if the movie was added successfully, false otherwise
     */
    private boolean addToFavorites(Movie movie) {
        ContentResolver cr = getContext().getContentResolver();

        ContentValues cvs = new ContentValues();

        Uri newUri = cr.insert(FavoriteMoviesContract.FavoriteMoviesEntry.buildFavoriteMoviesUri(movie.getId()), getContentValuesForMovie(movie));

        if (newUri != null) {
            return true;
        }

        return false;
    }

    /**
     * Removes a movies from the favorites table in the DB
     *
     * @param movie The movie to remove
     * @return True if the movie was successfuly removes, false otherwise
     */
    private boolean removeFromFavorites(Movie movie) {
        ContentResolver cr = getContext().getContentResolver();

        int numDeleted = cr.delete(
                FavoriteMoviesContract.FavoriteMoviesEntry.buildFavoriteMoviesUri(movie.getId()),
                null,
                null);

        if (numDeleted > 0) {
            return true;
        }

        return false;
    }

    /**
     * Creates ContentValues for a movie
     *
     * @param movie The movie to create ContentValues for
     * @return The ContentValues for the movie
     */
    private ContentValues getContentValuesForMovie(Movie movie) {
        ContentValues movieValues = new ContentValues();

        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry._ID, movie.getId());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.POSTER_PATH, movie.getPosterPath());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.ADULT, movie.isAdult());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.OVERVIEW, movie.getOverview());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.RELEASE_DATE, movie.getReleaseDate());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.ORIGINAL_TITLE, movie.getOriginalTitle());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.TITLE, movie.getTitle());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.BACKDROP_PATH, movie.getBackdropPath());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.POPULARITY, movie.getPopularity());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.VOTE_COUNT, movie.getVoteCount());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.VIDEO, movie.isVideo());
        movieValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.VOTE_AVERAGE, movie.getVoteAverage());

        return movieValues;
    }

    /**
     * Determines whether the movie is previously favorites
     *
     * @param movie The movie to check if favorited
     * @return True if the movie is favorited already, false otherwise
     */
    private boolean isAlreadyFavorited(Movie movie) {
        ContentResolver cr = getContext().getContentResolver();

        Cursor cursor = cr.query(
                FavoriteMoviesContract.FavoriteMoviesEntry.buildFavoriteMoviesUri(mMovie.getId()),
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        }

        return false;
    }

    private void shouldShowTrailersProgressBar(boolean shouldShow) {
        if (shouldShow) {
            trailersProgressBar.setVisibility(View.VISIBLE);
            trailersLayout.setVisibility(View.GONE);
        } else {
            trailersProgressBar.setVisibility(View.GONE);
            trailersLayout.setVisibility(View.VISIBLE);
        }
    }

    private void shouldShowReviewsProgressBar(boolean shouldShow) {
        if (shouldShow) {
            reviewsProgressBar.setVisibility(View.VISIBLE);
            reviewsLayout.setVisibility(View.GONE);
        } else {
            reviewsProgressBar.setVisibility(View.GONE);
            reviewsLayout.setVisibility(View.VISIBLE);
        }
    }

}
