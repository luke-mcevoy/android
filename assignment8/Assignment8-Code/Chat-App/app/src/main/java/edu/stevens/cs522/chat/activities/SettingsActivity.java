package edu.stevens.cs522.chat.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import edu.stevens.cs522.chat.R;

/**
 * Created by dduggan.
 */

public class SettingsActivity extends AppCompatActivity {

    /*
     * See Settings for saving and restoring settings
     *
     */

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Use a non-standard file name for default preferences
            // PreferenceManager prefMgr = getPreferenceManager();
            // prefMgr.setSharedPreferencesName(Settings.SETTINGS);

        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings, rootKey);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the messages content.
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }


}