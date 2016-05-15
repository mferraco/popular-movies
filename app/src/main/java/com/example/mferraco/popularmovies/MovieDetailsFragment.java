package com.example.mferraco.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mferraco.popularmovies.responseModels.Movie;
import com.squareup.picasso.Picasso;

/**
 * This Fragment displays the details for a particular movie.  These details could be on their
 * own screen (on smaller devices) or on half of the screen snext to the list of movies (on larger
 * devices such as tablets).
 */
public class MovieDetailsFragment extends android.support.v4.app.Fragment {

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

        return rootView;

    }
}
