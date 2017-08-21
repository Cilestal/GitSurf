package ua.dp.michaellang.gitsurf.view.gists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

/**
 * Date: 01.06.17
 *
 * @author Michael Lang
 */
public class GistsActivity extends ToolbarActivity {

    private static final String EXTRA_LOGIN
            = "ua.dp.michaellang.gitsurf.view.gists.login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String login = getIntent().getStringExtra(EXTRA_LOGIN);
        boolean showHomeButton = false;

        if (login == null) {
            showHomeButton = true;
            login = SPUtil.getAuthLogin(this);
        }

        setToolbar(login, showHomeButton);
        mNavigationView.setCheckedItem(R.id.navigation_my_gists);

        if (getContentFragment() == null) {
            Fragment fragment = GistsFragment.newInstance(login);
            setContent(fragment);
        }
    }

    private void setToolbar(String login, boolean showHomeButton) {
        if (showHomeButton) {
            setToolbarTitle(R.string.my_gists_title);
            showHomeButton();
        } else {
            String title = getString(R.string.user_gists_title, login);
            setToolbarTitle(title);
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, GistsActivity.class);
    }

    public static Intent newIntent(Context context, String login) {
        Intent intent = new Intent(context, GistsActivity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        return intent;
    }

}
