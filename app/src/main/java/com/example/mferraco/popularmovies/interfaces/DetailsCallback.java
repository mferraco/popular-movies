package com.example.mferraco.popularmovies.interfaces;


import com.example.mferraco.popularmovies.responseModels.Movie;

/**
 * A callback interface that all activities containing this fragment must
 * implement. This mechanism allows activities to be notified of item
 * selections.
 */
public interface DetailsCallback {
    /**
     * DetailFragmentCallback for when an item has been selected.
     */
    void onItemSelected(Movie movie);
}

