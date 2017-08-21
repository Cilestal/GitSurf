package ua.dp.michaellang.gitsurf.view.repos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

/**
 * Date: 12.04.17
 *
 * @author Michael Lang
 */
public class RepositoryListActivity extends ToolbarActivity {
    private static final String EXTRA_LOGIN = "ua.dp.michaellang.gitsurf.view.repos.extra_login";
    private static final String EXTRA_IS_ORG = "ua.dp.michaellang.gitsurf.view.repos.extra_is_org";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String login = getIntent().getStringExtra(EXTRA_LOGIN);
        boolean isOrg = getIntent().getBooleanExtra(EXTRA_IS_ORG, false);

        if(login == null){
            login = SPUtil.getAuthLogin(this);
            mNavigationView.setCheckedItem(R.id.navigation_my_repo);
            showHomeButton();
        } else {
            String barTitle = getString(R.string.repos_list_title, login);
            setToolbarTitle(barTitle);
        }

        if(getContentFragment() == null) {
            RepositoryListFragment fragment = RepositoryListFragment.newInstance(login, isOrg);
            setContent(fragment);
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RepositoryListActivity.class);
    }

    public static Intent newIntent(Context context, String login, boolean isOrg) {
        Intent intent = new Intent(context, RepositoryListActivity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        intent.putExtra(EXTRA_IS_ORG, isOrg);

        return intent;
    }
}
