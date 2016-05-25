package com.example.mferraco.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * The Contract to be used by {@link FavoriteMoviesProvider} for storing/retrieving favorited
 * movies from the device db
 */
public class FavoriteMoviesContract {

    private static final String TAG = FavoriteMoviesContract.class.getSimpleName();

    public static final String CONTENT_AUTHORITY = "com.example.mferraco.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class FavoriteMoviesEntry implements BaseColumns {
        // table name
        public static final String TABLE_FAVORITE_MOVIES = "favoriteMovies";

        // columns
        public static final String _ID = "id";
        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String TITLE = "title";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VIDEO = "video";
        public static final String VOTE_AVERAGE = "vote_average";

        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(TABLE_FAVORITE_MOVIES)
                .build();

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/"
                + CONTENT_AUTHORITY
                + "/"
                + TABLE_FAVORITE_MOVIES;

        // create cursor of base type item for single entries
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/"
                + CONTENT_AUTHORITY
                + "/"
                + TABLE_FAVORITE_MOVIES;

        // for building uris on insertion
        public static Uri buildFavoriteMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
