package ua.dp.michaellang.gitsurf.view.starred;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

/**
 * Date: 14.06.17
 *
 * @author Michael Lang
 */
public class StarredActivity extends ToolbarActivity {
    private static final String EXTRA_LOGIN = "ua.dp.michaellang.gitsurf.view.repos.extra_login";

    private String mLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initExtras();

        if (mLogin == null) {
            mLogin = SPUtil.getAuthLogin(this);
            mNavigationView.setCheckedItem(R.id.navigation_my_starred);
            showHomeButton();
        } else {
            String barTitle = getString(R.string.starred_list_title, mLogin);
            setToolbarTitle(barTitle);
        }

        if (getContentFragment() == null) {
            StarredFragment fragment = StarredFragment.newInstance(mLogin);
            setContent(fragment);
        }
    }

    private void initExtras(){
        mLogin = getIntent().getStringExtra(EXTRA_LOGIN);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, StarredActivity.class);
    }

    public static Intent newIntent(Context context, String login) {
        Intent intent = new Intent(context, StarredActivity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        return intent;
    }
}
