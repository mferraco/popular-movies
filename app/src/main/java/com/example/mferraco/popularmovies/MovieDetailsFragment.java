package com.example.mferraco.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * own screen (on smaller devices) or on half of the screen snext to the list of movies (on larger
 * devices such as tablets).
 */
public class MovieDetailsFragment extends android.support.v4.app.Fragment implements AsyncGetTrailersResponse, AsyncGetReviewsResponse {

    private static String TAG = MovieDetailsFragment.class.getSimpleName();

    private Movie mMovie;

    // UI Elements
    private TextView title;
    private ImageView thumbnail;

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
        }

        // make the request for the trailers
        makeTrailersRequest();

        // make the request for the reviews
        makeReviewsRequest();

        return rootView;
    }

    /**
     * Makes the request for the trailers for the movie that this detail fragment is showing
     * details for.  Will notify the user via a Toast if there is no network connection.
     */
    private void makeTrailersRequest() {
        if (AppUtils.isOnline(getContext())) {
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
        // display the trailers here...
        Log.d(TAG, String.valueOf(trailers.size()));
    }

    @Override
    public void processReviewsResponse(ArrayList<Review> reviews) {
        // display the reviews here...
        Log.d(TAG, String.valueOf(reviews.size()));
    }
}
