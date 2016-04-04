package com.example.mferraco.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mferraco.popularmovies.adapters.ImageAdapter;
import com.example.mferraco.popularmovies.responseModels.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by mferraco on 4/2/16.
 *
 * This activity displays the details for a Movie
 */
public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    private Movie mMovie;

    // UI Elements
    private TextView title;
    private ImageView thumbnail;

    @Override
    public void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.movie_details);

        mMovie = getIntent().getParcelableExtra(ImageAdapter.MOVIE_OBJECT_KEY);

        title = (TextView) findViewById(R.id.movie_title_text);
        title.setText(mMovie.getOriginalTitle());

        thumbnail = (ImageView) findViewById(R.id.movie_thumbnail_image);
        String basePath = this.getString(R.string.image_base_path);
        String size = this.getString(R.string.image_size_w342);
        String posterPath = mMovie.getPosterPath();
        Picasso.with(this).load(basePath + size + posterPath).into(thumbnail);


        String[] fieldTitles = getResources().getStringArray(R.array.movie_details_fields_title);

        // Release Date
        TextView releaseDateHeading = (TextView) findViewById(R.id.release_date_heading_text);
        releaseDateHeading.setText(fieldTitles[0]);
        TextView releaseDateValue = (TextView) findViewById(R.id.release_date_text);
        releaseDateValue.setText(mMovie.getReleaseDate());

        // Vote Average
        TextView voteAverageHeading = (TextView) findViewById(R.id.vote_average_heading_text);
        voteAverageHeading.setText(fieldTitles[1]);
        TextView voteAverageValue = (TextView) findViewById(R.id.vote_average_text);
        voteAverageValue.setText(String.valueOf(mMovie.getVoteAverage()));

        // Plot Synopsis (overview)
        TextView overviewHeading = (TextView) findViewById(R.id.overview_heading_text);
        overviewHeading.setText(fieldTitles[2]);
        TextView overviewValue = (TextView) findViewById(R.id.overview_text);
        overviewValue.setText(mMovie.getOverview());
    }
}
