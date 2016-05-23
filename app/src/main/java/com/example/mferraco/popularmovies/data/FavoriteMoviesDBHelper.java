package com.example.mferraco.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Helper methods extended from SQLiteOpenHelper to use in the provider for favorite movies
 */
public class FavoriteMoviesDBHelper extends SQLiteOpenHelper {

    private static final String TAG = FavoriteMoviesDBHelper.class.getSimpleName();

    // name & version
    private static final String DATABASE_NAME = "favoritemovies.db";
    private static final int DATABASE_VERSION = 1;

    public FavoriteMoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_MOVIES_TABLE =
                "CREATE TABLE" + FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_FAVORITE_MOVIES
                        + "(" + FavoriteMoviesContract.FavoriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FavoriteMoviesContract.FavoriteMoviesEntry.POSTER_PATH + "TEXT NOT NULL"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.ADULT + "BOOL NOT NULL"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.OVERVIEW + "TEXT NOT NULL"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.RELEASE_DATE + "TEXT  NOT NULL"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.ORIGINAL_TITLE + "TEXT NOT NULL"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.ORIGINAL_LANGUAGE + "TEXT NOT NULL"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.TITLE + "TEXT NOT NULL"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.BACKDROP_PATH + "TEXT NOT NULL"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.POPULARITY + "REAL"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.VOTE_COUNT + "INTEGER"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.VIDEO + "BOOL NOT NULL"
                        + FavoriteMoviesContract.FavoriteMoviesEntry.VOTE_AVERAGE + "REAL);";

        db.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ". OLD DATA WILL BE DESTROYED.");

        // drop the db table
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_FAVORITE_MOVIES);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                + FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_FAVORITE_MOVIES + "'");

        // re-create the db
        onCreate(db);
    }
}
