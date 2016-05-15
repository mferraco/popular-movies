package com.example.mferraco.popularmovies.requestTasks;

import com.example.mferraco.popularmovies.responseModels.Movie;

import java.util.ArrayList;

/**
 * Any class that wishes to retrieve movies from the API must implement this interface
 */
public interface AsyncGetMoviesResponse {

    /**
     * Contains any actions that must take place based on the response from the API
     *
     * @param movies the response of movies from the API
     */
    void processMovieResponse(ArrayList<Movie> movies);
}
