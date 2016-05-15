package com.example.mferraco.popularmovies;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mferraco.popularmovies.adapters.ImageAdapter;
import com.example.mferraco.popularmovies.interfaces.DetailsCallback;
import com.example.mferraco.popularmovies.requestTasks.AsyncGetMoviesResponse;
import com.example.mferraco.popularmovies.requestTasks.GetMoviesTask;
import com.example.mferraco.popularmovies.responseModels.Movie;
import com.example.mferraco.popularmovies.utils.AppUtils;

import java.util.ArrayList;


/**
 * This fragment will control the grid view of movies.
 */
public class    MoviesFragment extends android.support.v4.app.Fragment implements AsyncGetMoviesResponse {

    private static final String TAG = MoviesFragment.class.getSimpleName();

    // Keys for storing instance state
    private static final String CURRENT_SORT_ORDER_KEY = "mCurrentSortOrder";
    private static final String FIRST_REQUEST_KEY = "mFirstRequest";
    private static final String MOVIES_KEY = "mMovies";

    private GridView mImageGridView;

    private String mCurrentSortOrder;

    private boolean mFirstRequest = true;

    private ArrayList<Movie> mMovies;

    private boolean isTabletLayout;

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance(Bundle args) {
        MoviesFragment fragment = new MoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_list, container, false);

        Bundle args = getArguments();
        if (args != null) {
            isTabletLayout = args.getBoolean(MoviesActivity.IS_TABLET_LAYOUT_TAG);
        }

        if (savedInstanceState != null) {
            mCurrentSortOrder = savedInstanceState.getString(CURRENT_SORT_ORDER_KEY, getString(R.string.settings_sort_order_default));
            mFirstRequest = savedInstanceState.getBoolean(FIRST_REQUEST_KEY, true);
            mMovies = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
        } else {
            mCurrentSortOrder = getString(R.string.settings_sort_order_default);
        }

        mImageGridView = (GridView) rootView.findViewById(R.id.movie_grid_view);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (isTabletLayout) {
                mImageGridView.setNumColumns(3);
            } else {
                mImageGridView.setNumColumns(4);
            }
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String sortOrderPreference = sharedPref.getString(getString(R.string.settings_sort_order_key), getString(R.string.settings_sort_order_default));

        if (!(mCurrentSortOrder.equalsIgnoreCase(sortOrderPreference)) || mFirstRequest) {
            // always make the request the first time the activity loads
            // after that only make the request if the sort order changes
            mFirstRequest = false;
            mCurrentSortOrder = sortOrderPreference;

            if (AppUtils.isOnline(getContext())) {
                GetMoviesTask getMoviesTask = new GetMoviesTask(getContext());
                getMoviesTask.delegate = this;
                Log.d(TAG, "EXECUTING API REQUEST");
                getMoviesTask.execute(mCurrentSortOrder);
            } else {
                Toast.makeText(getContext(), getString(R.string.no_network_dialog_title), Toast.LENGTH_LONG).show();
            }
        } else {
            if (mMovies != null) {
                processFinish(mMovies);
            }
        }
    }

    /* AsyncGetMoviesResponse Interface */

    @Override
    public void processFinish(ArrayList<Movie> movies) {
        mMovies = movies;

        // set up image adapter here
        mImageGridView.setAdapter(new ImageAdapter(getContext(), movies));

        mImageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((DetailsCallback) getActivity()).onItemSelected(mMovies.get(position));
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_SORT_ORDER_KEY, mCurrentSortOrder);
        outState.putBoolean(FIRST_REQUEST_KEY, mFirstRequest);
        outState.putParcelableArrayList(MOVIES_KEY, mMovies);
    }
}
