package com.example.mferraco.popularmovies.requestTasks;

import com.example.mferraco.popularmovies.responseModels.Movie;

import java.util.ArrayList;

/**
 * Created by mferraco on 4/2/16.
 *
 * This is an interface that the class that wants to retrieve the data must implement
 */
public interface AsyncGetMoviesResponse {
    void processFinish(ArrayList<Movie> movies);
}
