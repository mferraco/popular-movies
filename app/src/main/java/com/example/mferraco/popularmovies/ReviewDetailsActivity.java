package com.example.mferraco.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * The activity for the screen which shows the full movie review and details about the review
 */
public class ReviewDetailsActivity extends AppCompatActivity {

    private static final String TAG = ReviewDetailsActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        if (savedInstanceState == null) {
            // Create ReviewDetailsFragment and add it to the activity using a fragment transaction
            Bundle args = new Bundle();
            args.putParcelable(ReviewDetailsFragment.REVIEW_OBJECT_KEY, getIntent().getParcelableExtra(ReviewDetailsFragment.REVIEW_OBJECT_KEY));
            ReviewDetailsFragment fragment = ReviewDetailsFragment.newInstance(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.review_detail_container, fragment)
                    .commit();
        }

    }
}
