package yaksok.dodream.com.yaksok_refactoring.view.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

import yaksok.dodream.com.yaksok_refactoring.R;

public class SettingPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener{

    SharedPreferences prefs;

    ListPreference soundPreference;
    ListPreference keywordSoundPreference;
    PreferenceScreen myPageScreen,developerScreen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_preference);
        myPageScreen = (PreferenceScreen)findPreference("myPage");
        developerScreen = (PreferenceScreen)findPreference("developer");
        

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        developerScreen.setOnPreferenceClickListener(this);
        myPageScreen.setOnPreferenceClickListener(this);


        //prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("developer")) {
            startActivity(new Intent(preference.getContext(), Developer.class));
            return true;
        }
        if (preference.getKey().equals("myPage")){
            startActivity(new Intent(preference.getContext(), MyPage.class));
            return true;
        }
        return true;
    }
}
