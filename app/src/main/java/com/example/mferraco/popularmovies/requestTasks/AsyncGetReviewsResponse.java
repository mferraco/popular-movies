package com.example.mferraco.popularmovies.requestTasks;

import com.example.mferraco.popularmovies.responseModels.Review;

import java.util.ArrayList;

/**
 * Any class that wishes to retrieve reviews from the API must implement this interface
 */
public interface AsyncGetReviewsResponse {

    /**
     * Contains any actions that must take place based on the response from the GET reviews request
     *
     * @param reviews the response of reviews from the API
     */
    void processReviewsResponse(ArrayList<Review> reviews);

}
