package com.example.mferraco.popularmovies.interfaces;

import com.example.mferraco.popularmovies.responseModels.Review;

/**
 * A callback interface that all activities containing this fragment must
 * implement. This mechanism allows activities to be notified of item
 * selections.
 */
public interface ReviewDetailsCallback {
    /**
     * Review details callback for when a review item has been selected.
     */
    void onItemSelected(Review review);
}
