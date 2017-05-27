package xyz.cybersapien.newsdroid

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity

/**
 * Created by ogcybersapien on 7/10/16.
 * Settings activity for the project
 */

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    class StoryPreferenceFragment : PreferenceFragment(), Preference.OnPreferenceChangeListener {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.settings_main)

            //Setting up Sections Preferences
            val sections = findPreference(getString(R.string.settings_section_key))
            bindPreferenceSummaryToValue(sections)

            //Setting up Number of Items Preferences
            val numberOfItems = findPreference(getString(R.string.settings_number_key))
            bindPreferenceSummaryToValue(numberOfItems)
        }

        override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
            val stringValue = newValue.toString()
            if (preference is ListPreference) {
                val sectionsPreference = preference

                val prefIndex = sectionsPreference.findIndexOfValue(stringValue)
                if (prefIndex >= 0) {
                    val labels = sectionsPreference.entries
                    preference.setSummary(labels[prefIndex])
                }
            } else {
                preference.summary = stringValue
            }
            return true
        }

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.onPreferenceChangeListener = this
            val preferences = PreferenceManager.getDefaultSharedPreferences(preference.context)

            val preferenceString = preferences.getString(preference.key, "")

            onPreferenceChange(preference, preferenceString)
        }
    }
}
