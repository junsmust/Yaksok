package yaksok.dodream.com.yaksok_refactoring.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

import yaksok.dodream.com.yaksok_refactoring.R;

public class SettingPreferenceFragment extends PreferenceFragment {

    SharedPreferences prefs;

    ListPreference soundPreference;
    ListPreference keywordSoundPreference;
    PreferenceScreen myPageScreen,developerScreen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_preference);
        myPageScreen = (PreferenceScreen)findPreference("keyword_screen");
        developerScreen = (PreferenceScreen)findPreference("developer");
        

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


        //prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }
}
