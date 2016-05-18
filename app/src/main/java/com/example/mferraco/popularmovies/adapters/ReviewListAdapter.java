package com.example.mferraco.popularmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mferraco.popularmovies.R;
import com.example.mferraco.popularmovies.ReviewDetailsActivity;
import com.example.mferraco.popularmovies.ReviewDetailsFragment;
import com.example.mferraco.popularmovies.responseModels.Review;

import java.util.List;

/**
 * This is the custom adapter class which populates a linear layout on the movie details page with
 * with reviews.
 */
public class ReviewListAdapter extends ArrayAdapter<Review> {

    private Context mContext;

    private List<Review> mReviews;

    public ReviewListAdapter(Context context, List<Review> reviews) {
        super(context, R.layout.fragment_movie_details, reviews);

        mContext = context;
        mReviews = reviews;
    }

    @Override
    public int getCount() {
        return mReviews.size();
    }

    @Override
    public Review getItem(int position) {
        return mReviews.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        TrailerListItemViewHolder holder;

        if (listItem == null) { // not recycling a view
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            listItem = inflater.inflate(R.layout.review_list_item, parent, false);

            holder = new TrailerListItemViewHolder();
            holder.icon = (ImageView) listItem.findViewById(R.id.pencil_icon_imageview);
            holder.review = (TextView) listItem.findViewById(R.id.review_textview);
            listItem.setTag(holder);
        } else { // recycling a view
            holder = (TrailerListItemViewHolder) listItem.getTag();
        }

        // set data on list item view holder
        holder.review.setText(mReviews.get(position).getContent());

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewDetailsIntent = new Intent(mContext, ReviewDetailsActivity.class);
                reviewDetailsIntent.putExtra(ReviewDetailsFragment.REVIEW_OBJECT_KEY, mReviews.get(position));
                mContext.startActivity(reviewDetailsIntent);
            }
        });

        return listItem;
    }

    /**
     * A ViewHolder for the list items in the list of reviews
     */
    static class TrailerListItemViewHolder {
        ImageView icon;
        TextView review;
    }
}
