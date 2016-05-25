package com.example.mferraco.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * This is the ContentProvider for movies that have been favorited in the app.
 */
public class FavoriteMoviesProvider extends ContentProvider {

    private static final String TAG = FavoriteMoviesProvider.class.getSimpleName();

    private static final UriMatcher uriMathcher = buildUriMatcher();

    private FavoriteMoviesDBHelper helper;

    /* Codes for the URI Matcher */
    private static final int FAVORITE_MOVIE = 100;
    private static final int ALL_FAVORITE_MOVIES = 200;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavoriteMoviesContract.CONTENT_AUTHORITY;

        // add a code for each type of URI
        matcher.addURI(authority, FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_FAVORITE_MOVIES + "/", ALL_FAVORITE_MOVIES);
        matcher.addURI(authority, FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_FAVORITE_MOVIES + "/#", FAVORITE_MOVIE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        helper = new FavoriteMoviesDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int match = uriMathcher.match(uri);

        Cursor cursor;

        switch (match) {
            case ALL_FAVORITE_MOVIES:
                cursor = helper.getReadableDatabase().query(
                        FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_FAVORITE_MOVIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return cursor;
            case FAVORITE_MOVIE:
                cursor = helper.getReadableDatabase().query(
                        FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_FAVORITE_MOVIES,
                        projection,
                        FavoriteMoviesContract.FavoriteMoviesEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);

                return cursor;
            default:
                // default assumes bad uri
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = uriMathcher.match(uri);

        switch (match) {
            case ALL_FAVORITE_MOVIES:
                return FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_DIR_TYPE;
            case FAVORITE_MOVIE:
                return FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_ITEM_TYPE;
            default:
                // default assumes bad uri
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = helper.getWritableDatabase();
        final int match = uriMathcher.match(uri);

        Uri returnUri;

        switch (match) {
            case FAVORITE_MOVIE:
                long _id = db.insert(
                        FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_FAVORITE_MOVIES,
                        null,
                        values);

                // insert unless it is already contained in the db
                if (_id > 0) {
                    returnUri =
                            FavoriteMoviesContract.FavoriteMoviesEntry.buildFavoriteMoviesUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }

                break;
            default:
                // default assumes bad uri
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = helper.getWritableDatabase();
        final int match = uriMathcher.match(uri);

        int numDeleted;

        switch (match) {
            case FAVORITE_MOVIE:
                numDeleted = db.delete(
                        FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_FAVORITE_MOVIES,
                        FavoriteMoviesContract.FavoriteMoviesEntry._ID + " = ? ",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});

                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_FAVORITE_MOVIES + "'");

                break;
            default:
                // default assumes bad uri
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // should not be updating the records
        return 0;
    }
}
