package com.example.mferraco.popularmovies.responseModels;

import org.json.JSONObject;

/**
 * This object represents a review json object in the response from TheMovieDB API.
 */
public class Review {

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
}
