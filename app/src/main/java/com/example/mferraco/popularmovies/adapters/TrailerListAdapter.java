package com.example.mferraco.popularmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mferraco.popularmovies.R;
import com.example.mferraco.popularmovies.responseModels.Trailer;

import java.util.List;

/**
 * This is the custom adapter class which populates the list view on the movie details page with
 * with trailers
 */
public class TrailerListAdapter extends ArrayAdapter<Trailer> {

    private Context mContext;

    private List<Trailer> mTrailers;

    public TrailerListAdapter(Context context, List<Trailer> objects) {
        super(context, R.layout.fragment_movie_details, objects);

        mContext = context;
        mTrailers = objects;
    }

    @Override
    public int getCount() {
        return mTrailers.size();
    }

    @Override
    public Trailer getItem(int position) {
        return mTrailers.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        TrailerListItemViewHolder holder;

        if (listItem == null) { // not recycling a view
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            listItem = inflater.inflate(R.layout.trailer_list_item, parent, false);

            holder = new TrailerListItemViewHolder();
            holder.icon = (ImageView) listItem.findViewById(R.id.youtube_icon_imageview);
            holder.title = (TextView) listItem.findViewById(R.id.trailer_title_textview);
            listItem.setTag(holder);
        } else { // recycling a view
            holder = (TrailerListItemViewHolder) listItem.getTag();
        }

        // set data on list item view holder
        holder.title.setText(mTrailers.get(position).getName());

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open youtube intent here
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + mTrailers.get(position).getKey()));
                mContext.startActivity(intent);
            }
        });

        return listItem;
    }

    /**
     * A ViewHolder for the list items in the list of trailers
     */
    static class TrailerListItemViewHolder {
        ImageView icon;
        TextView title;
    }
}
