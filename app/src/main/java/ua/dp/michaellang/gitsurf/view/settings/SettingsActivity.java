package ua.dp.michaellang.gitsurf.view.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

/**
 * Date: 15.07.2017
 *
 * @author Michael Lang
 */
public class SettingsActivity extends ToolbarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();
        mNavigationView.setCheckedItem(R.id.navigation_settings);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(R.id.base_content, SettingsFragment.newInstance())
                .commit();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, SettingsActivity.class);
    }
}
