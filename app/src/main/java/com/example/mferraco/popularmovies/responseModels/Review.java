package com.example.mferraco.popularmovies.responseModels;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * This object represents a review json object in the response from TheMovieDB API.
 */
public class Review implements Parcelable {

    private static final String TAG = Review.class.getSimpleName();

    private String id;
    private String author;
    private String content;
    private String url;

    public Review() {
        // No-op
    }

    public static Review fromJson(JSONObject reviewDataJson) {
        Review review = new Review();

        review.setId(reviewDataJson.optString("id"));
        review.setAuthor(reviewDataJson.optString("author"));
        review.setContent(reviewDataJson.optString("content"));
        review.setUrl(reviewDataJson.optString("url"));

        return review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /* Parcelable Interface */

    public Review(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {

        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
