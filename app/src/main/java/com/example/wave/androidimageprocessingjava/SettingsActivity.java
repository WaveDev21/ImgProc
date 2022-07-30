package com.example.wave.androidimageprocessingjava;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.wave.androidimageprocessingjava.DBConnection.SettingsDBHelper;

/**
 * Created by root on 09.12.16.
 */

public class SettingsActivity extends PreferenceActivity {

    public static String currentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        this.initSettings();
    }

    @SuppressWarnings("deprecation")
    private void initSettings(){

        addPreferencesFromResource(R.xml.general_prefs_activity);

        final Preference okPref = findPreference("settings_ok");
        if(okPref != null){
            okPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    finish();
                    return false;
                }
            });
        }

        final ListPreference modePref = (ListPreference) findPreference("settings_current_mode");
        if(modePref != null){

            final SettingsDBHelper dbHelper = new SettingsDBHelper(this);
            SettingsActivity.currentMode = dbHelper.getSetting("mode");

            if(SettingsActivity.currentMode.equals("")){
                SettingsActivity.currentMode = modePref.getValue();
            }

            setModePreferenceSummary(modePref, SettingsActivity.currentMode);
            modePref.setValue(SettingsActivity.currentMode);

            modePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    SettingsActivity.currentMode = (String) newValue;
                    if(!dbHelper.getSetting("mode").equals("")){
                        dbHelper.updateSetting("mode", SettingsActivity.currentMode);
                    }else{
                        dbHelper.insertSetting("mode", SettingsActivity.currentMode);
                    }

                    setModePreferenceSummary(modePref, SettingsActivity.currentMode);
                    modePref.setValue(SettingsActivity.currentMode);
                    return false;
                }
            });

        }

        final Preference aboutPref = findPreference("settings_about");
        final AboutDialog aboutDialog = new AboutDialog(this);
        aboutDialog.setTitle("About this app");
        if(aboutPref != null){
            aboutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    aboutDialog.show();
                    return false;
                }
            });
        }

    }

    private void setModePreferenceSummary(Preference _preference, String _locationString) {

        switch (_locationString) {
            case "PRO":
                _preference.setSummary(getResources().getString(R.string.mode_pro));
                break;
            case "AUTO":
                _preference.setSummary(getResources().getString(R.string.mode_auto));
                break;
        }
    }


}
