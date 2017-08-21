package ua.dp.michaellang.gitsurf.view.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import ua.dp.michaellang.gitsurf.R;

/**
 * Date: 15.07.2017
 *
 * @author Michael Lang
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }
}
