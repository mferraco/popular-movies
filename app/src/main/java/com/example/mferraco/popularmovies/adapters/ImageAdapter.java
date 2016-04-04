package com.example.mferraco.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.mferraco.popularmovies.MovieDetailsActivity;
import com.example.mferraco.popularmovies.R;
import com.example.mferraco.popularmovies.responseModels.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mferraco on 4/2/16.
 * <p/>
 * This is the custom adapter class which populates the grid view with movie images
 */

public class ImageAdapter extends ArrayAdapter<Movie> {

    // image URL constants
    private static final String imageBaseUrl = "http://image.tmdb.org/t/p/";
    private static final String imageSize = "w185";

    public static final String MOVIE_OBJECT_KEY = "movieObjectKey";

    private Context mContext;

    private List<Movie> mMovies;

    public ImageAdapter(Context context, List<Movie> objects) {
        super(context, R.layout.activity_movies, objects);
        mContext = context;
        mMovies = objects;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Movie getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mMovies.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) { // if this IS NOT a recycled view
            imageView = new ImageView(mContext);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setAdjustViewBounds(true);
        } else { // if this IS a recycled view
            imageView = (ImageView) convertView;
        }

        String posterPath = mMovies.get(position).getPosterPath();

        Picasso.with(mContext).load(imageBaseUrl + imageSize + posterPath).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieDetailsIntent = new Intent(mContext, MovieDetailsActivity.class);
                movieDetailsIntent.putExtra(MOVIE_OBJECT_KEY, mMovies.get(position));
                mContext.startActivity(movieDetailsIntent);
            }
        });

        return imageView;
    }
}
