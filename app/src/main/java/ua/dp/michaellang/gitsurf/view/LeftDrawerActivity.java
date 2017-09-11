package ua.dp.michaellang.gitsurf.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import ua.dp.michaellang.gitsurf.Constants;
import ua.dp.michaellang.gitsurf.GitSurfApplication;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.ImageUtil;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.view.events.EventsActivity;
import ua.dp.michaellang.gitsurf.view.gists.GistsActivity;
import ua.dp.michaellang.gitsurf.view.issues.IssueListActivity;
import ua.dp.michaellang.gitsurf.view.repos.RepositoryListActivity;
import ua.dp.michaellang.gitsurf.view.search.SearchRepositoryActivity;
import ua.dp.michaellang.gitsurf.view.search.SearchUserActivity;
import ua.dp.michaellang.gitsurf.view.settings.SettingsActivity;
import ua.dp.michaellang.gitsurf.view.starred.StarredActivity;
import ua.dp.michaellang.gitsurf.view.users.UserActivity;

/**
 * Date: 24.02.17
 *
 * @author Michael Lang
 */
public class LeftDrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = getClass().getName();

    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;

    protected static final int NAV_BAR_GRAVITY = GravityCompat.START;

    private View mNavHeader;

    protected ImageView mDrawerAvatar;
    protected TextView mDrawerLogin;
    protected TextView mDrawerName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initNavViewHeader();
        initNavView();
    }

    @Override
    public int getContent() {
        return R.layout.activity_left_drawer;
    }

    private void initNavViewHeader() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.activity_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mNavHeader = mNavigationView.getHeaderView(0);

        mDrawerAvatar = (ImageView) mNavHeader.findViewById(R.id.drawer_avatar_IV);
        mDrawerLogin = (TextView) mNavHeader.findViewById(R.id.drawer_login_TV);
        mDrawerName = (TextView) mNavHeader.findViewById(R.id.drawer_name_TV);

        loadNavViewHeader();
    }

    private void loadNavViewHeader() {
        if (GitSurfApplication.isAuthorized()) {
            mDrawerLogin.setText(SPUtil.getValue(this, Constants.Prefs.User.LOGIN));

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            mDrawerName.setText(currentUser.getDisplayName());
            String avatarUrl = currentUser.getPhotoUrl().toString();

            ImageUtil.loadUserCircleAvatar(this, mDrawerAvatar, avatarUrl);

            mNavHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = UserActivity.newIntent(LeftDrawerActivity.this);
                    startActivity(intent);
                }
            });
        }
    }

    private void initNavView() {
        if (GitSurfApplication.isAuthorized()) {
            mNavigationView.inflateMenu(R.menu.menu_left_drawer);
        } else {
            mNavigationView.inflateMenu(R.menu.menu_left_drawer_base);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        switch (id) {
            case R.id.navigation_logout:
                GitSurfApplication.logout(this);
                goToTopLevelActivity();
                break;
            case R.id.navigation_sign_in:
                goToTopLevelActivity();
                break;
            case R.id.navigation_news:
                intent = EventsActivity.newIntent(this);
                break;
            case R.id.navigation_my_repo:
                intent = RepositoryListActivity.newIntent(this);
                break;
            case R.id.navigation_search_users:
                intent = SearchUserActivity.newIntent(this);
                break;
            case R.id.navigation_public_repos:
                intent = SearchRepositoryActivity.newIntent(this);
                break;
            case R.id.navigation_my_issues:
                intent = IssueListActivity.newIntent(this, false);
                break;
            case R.id.navigation_my_pull_request:
                intent = IssueListActivity.newIntent(this, true);
                break;
            case R.id.navigation_my_gists:
                intent = GistsActivity.newIntent(this);
                break;
            case R.id.navigation_my_starred:
                intent = StarredActivity.newIntent(this);
                break;
            case R.id.navigation_settings:
                intent = SettingsActivity.newIntent(this);
                break;
            default:
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }

        mDrawerLayout.closeDrawer(NAV_BAR_GRAVITY);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(NAV_BAR_GRAVITY)) {
            mDrawerLayout.closeDrawer(NAV_BAR_GRAVITY);
        } else {
            super.onBackPressed();
        }
    }
}
