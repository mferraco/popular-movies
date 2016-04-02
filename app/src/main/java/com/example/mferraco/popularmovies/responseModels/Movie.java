package com.example.mferraco.popularmovies.responseModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mferraco on 4/1/16
 * <p/>
 * This object represents a movie json object in the response from TheMovieDB API.
 */
public class Movie {

    private static final String TAG = Movie.class.getSimpleName();

    private String posterPath;

    private boolean adult;

    private String overview;

    private String releaseDate;

    private ArrayList<Integer> genreIds;

    private int id;

    private String originalTitle;

    private String originalLanguage;

    private String title;

    private String backdropPath;

    private double popularity;

    private int voteCount;

    private boolean video;

    private double voteAverage;

    public static Movie fromJson(JSONObject movieDataJson) {
        Movie movie = new Movie();

        movie.setPosterPath(movieDataJson.optString("poster_path"));
        movie.setAdult(movieDataJson.optBoolean("adult"));
        movie.setOverview(movieDataJson.optString("overview"));
        movie.setReleaseDate(movieDataJson.optString("release_date"));
        movie.setId(movieDataJson.optInt("id"));
        movie.setOriginalTitle(movieDataJson.optString("original_title"));
        movie.setOriginalLanguage(movieDataJson.optString("original_language"));
        movie.setTitle(movieDataJson.optString("title"));
        movie.setBackdropPath(movieDataJson.optString("backdrop_path"));
        movie.setPopularity(movieDataJson.optDouble("popularity"));
        movie.setVoteCount(movieDataJson.optInt("vote_count"));
        movie.setVideo(movieDataJson.optBoolean("video"));
        movie.setVoteAverage(movieDataJson.optDouble("vote_average"));

        // add genre ids from list
        ArrayList<Integer> genreIds = new ArrayList<>();
        JSONArray genreIdsJsonArray = movieDataJson.optJSONArray("genre_ids");
        if (genreIdsJsonArray != null) {
            for (int i = 0; i < genreIdsJsonArray.length(); i++) {
                try {
                    genreIds.add(genreIdsJsonArray.getInt(i));
                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e);
                    e.printStackTrace();
                }

            }
        }
        movie.setGenreIds(genreIds);

        return movie;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
