package ua.dp.michaellang.gitsurf.view.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import ua.dp.michaellang.gitsurf.Constants.UserListGroup;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

/**
 * Date: 17.03.17
 *
 * @author Michael Lang
 */
public class UserListActivity extends ToolbarActivity {

    private static final String EXTRA_LOGIN = "ua.dp.michaellang.gitsurf.view.users.extra_login";
    private static final String EXTRA_GROUP = "ua.dp.michaellang.gitsurf.view.users.extra_group";
    private static final String EXTRA_REPO = "ua.dp.michaellang.gitsurf.view.users.extra_repo";

    private String mLogin;
    private String mRepoName;
    private int mGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setToolbar();

        if (getContentFragment() == null) {
            UsersListFragment fragment = UsersListFragment.newInstance(mLogin, mRepoName, mGroup);
            setContent(fragment);
        }
    }

    protected void setToolbar() {
        @StringRes int resId = -1;

        switch (mGroup) {
            case UserListGroup.FOLLOWERS:
                resId = R.string.followers_title;
                break;
            case UserListGroup.FOLLOWING:
                resId = R.string.following_title;
                break;
            case UserListGroup.ORGANIZATIONS:
                resId = R.string.orgs_title;
                break;
            case UserListGroup.MEMBERS:
                resId = R.string.members_title;
                break;
            case UserListGroup.STARGAZERS:
                resId = R.string.stargazers;
                break;
            case UserListGroup.WATCHERS:
                resId = R.string.watchers;
                break;
            case UserListGroup.CONTRIBUTORS:
                resId = R.string.contributors;
                break;
            case UserListGroup.COLLABORATORS:
                resId = R.string.collaborators;
                break;
            default:
                break;
        }

        if (resId > 0) {
            setToolbarTitle(resId);
        }
    }

    private void initData() {
        Intent intent = getIntent();
        mLogin = intent.getStringExtra(EXTRA_LOGIN);
        mGroup = intent.getIntExtra(EXTRA_GROUP, -1);
        mRepoName = intent.getStringExtra(EXTRA_REPO);
    }

    public static Intent newIntent(Context context, String login, int group) {
        Intent intent = new Intent(context, UserListActivity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        intent.putExtra(EXTRA_GROUP, group);
        return intent;
    }

    public static Intent newIntent(Context context, String login, String repoName, int group) {
        Intent intent = new Intent(context, UserListActivity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        intent.putExtra(EXTRA_REPO, repoName);
        intent.putExtra(EXTRA_GROUP, group);
        return intent;
    }
}
