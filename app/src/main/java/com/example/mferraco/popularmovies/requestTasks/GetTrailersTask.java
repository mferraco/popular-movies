package com.example.mferraco.popularmovies.requestTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mferraco.popularmovies.R;
import com.example.mferraco.popularmovies.responseModels.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * This task fetches movie trailers from the API based on the movie id provided to it
 */
public class GetTrailersTask extends AsyncTask<Integer, Integer, JSONObject> {

    private static final String TAG = GetTrailersTask.class.getSimpleName();

    private Context mContext;

    public AsyncGetTrailersResponse delegate;

    public GetTrailersTask (Context context){
        mContext = context;
    }

    @Override
    protected JSONObject doInBackground(Integer... params) {

        // if there's no parameters just return null, we need a the search type here
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            String baseUri = "https://api.themoviedb.org/3/movie/";
            Integer movieId = params[0];
            String apiKey = mContext.getString(R.string.movie_api_key);

            URL url = new URL(baseUri + movieId + "/videos?api_key=" + apiKey);

            // Create the request to TheMovieDB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            try {
                return new JSONObject(buffer.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            Log.e(TAG, "Error: " + e);
            e.printStackTrace();
            return null;
        } finally {
            // close all connections

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        ArrayList<Trailer> trailers = new ArrayList<>();

        JSONArray arrayOfTrailers = result.optJSONArray("results");

        for (int i = 0; i < arrayOfTrailers.length(); i++) {
            try {
                Trailer newTrailer = Trailer.fromJson(arrayOfTrailers.getJSONObject(i));
                trailers.add(newTrailer);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (delegate != null) {
            // send the data back to the delegate for processing
            delegate.processTrailersResponse(trailers);
        }
    }
}
