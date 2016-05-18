package com.example.mferraco.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mferraco.popularmovies.responseModels.Review;

/**
 * This Fragment displays the details for a particular review.  These details could be on their
 * own screen (on smaller devices) or on half of the screen next to the list of movies (on larger
 * devices such as tablets).
 */
public class ReviewDetailsFragment extends Fragment {

    private static final String TAG = ReviewDetailsFragment.class.getSimpleName();

    public static final String REVIEW_OBJECT_KEY = "reviewObjectKey";

    private Review mReview;

    // UI fields
    TextView authorTextView;
    TextView contentTextView;

    public static ReviewDetailsFragment newInstance(Bundle args) {
        ReviewDetailsFragment fragment = new ReviewDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review_details, container, false);

        Bundle args = getArguments();

        if (args != null) {
            // set up the view here with the review data...
            mReview = (Review) args.getParcelable(REVIEW_OBJECT_KEY);

            authorTextView = (TextView) rootView.findViewById(R.id.review_author_textview);
            contentTextView = (TextView) rootView.findViewById(R.id.review_content_textview);

            authorTextView.setText(mReview.getAuthor());
            contentTextView.setText(mReview.getContent());
        }

        return rootView;
    }
}
