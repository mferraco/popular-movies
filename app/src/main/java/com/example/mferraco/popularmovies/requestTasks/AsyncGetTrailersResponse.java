package com.example.mferraco.popularmovies.requestTasks;

import com.example.mferraco.popularmovies.responseModels.Trailer;

import java.util.ArrayList;

/**
 * Any class that wishes to retrieve trailers from the API must implement this interface
 */
public interface AsyncGetTrailersResponse {

    /**
     * Contains any actions that must take place based on the response from the GET trailers request
     *
     * @param trailers the response of trails from the API
     */
    void processTrailersResponse(ArrayList<Trailer> trailers);

}
