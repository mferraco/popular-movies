package com.example.mferraco.popularmovies;

import android.os.Bundle;

/**
 * Created by mferraco on 4/2/16.
 *
 * Loads the Settings Fragment
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

}
