package ua.dp.michaellang.gitsurf.view.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.Constants;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.presenter.users.UserPresenter;
import ua.dp.michaellang.gitsurf.presenter.users.UserPresenterImpl;
import ua.dp.michaellang.gitsurf.presenter.users.UserView;
import ua.dp.michaellang.gitsurf.utils.ImageUtil;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.utils.StringUtil;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;
import ua.dp.michaellang.gitsurf.view.events.EventsActivity;
import ua.dp.michaellang.gitsurf.view.repos.RepositoryListActivity;
import ua.dp.michaellang.gitsurf.view.starred.StarredActivity;

/**
 * Date: 15.03.17
 *
 * @author Michael Lang
 */
public class UserActivity extends ToolbarActivity implements UserView {
    private static final String EXTRA_LOGIN
            = "ua.dp.michaellang.gitsurf.view.user.EXTRA_LOGIN";

    private static final String KEY_ORG = "KEY_ORG";
    private static final String KEY_IS_FOLLOW = "KEY_IS_FOLLOW";

    @BindView(R.id.toolbar_backdrop) ImageView mAvatarIV;
    @BindView(R.id.content_user_follow_floating_button) FloatingActionButton mFollowFB;
    @BindView(R.id.toolbar_collapsing_layout) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.content_user_company_TV) TextView mCompanyTV;
    @BindView(R.id.content_user_bio_TV) TextView mBioTV;
    @BindView(R.id.content_user_followers_TV) TextView mFollowersTV;
    @BindView(R.id.content_user_following_TV) TextView mFollowingTV;
    @BindView(R.id.content_user_location_TV) TextView mLocationTV;
    @BindView(R.id.content_user_url_TV) TextView mUrlTV;
    @BindView(R.id.content_user_created_at_TV) TextView mCreatedAtTV;
    @BindView(R.id.content_user_login_TV) TextView mLoginTV;
    @BindView(R.id.content_user_orgs_TV) TextView mOrgsTV;
    @BindView(R.id.content_user_name_TV) TextView mNameTV;
    @BindView(R.id.content_user_repo_TV) TextView mRepoTV;
    @BindView(R.id.content_user_follow_layout) View mUserFollowLayout;
    @BindView(R.id.content_user_location_layout) View mLocationLayout;
    @BindView(R.id.content_user_url_layout) View mUrlLayout;
    @BindView(R.id.content_user_created_at_layout) View mCreatedAtLayout;
    @BindView(R.id.content_user_company_layout) View mCompanyLayout;
    @BindView(R.id.content_user_bio_layout) View mBioLayout;
    @BindView(R.id.content_user_starred_layout) View mUserStarredLayout;

    private boolean mAppBarExpanded = true;

    private boolean mIsAuthUser = false;
    private String mUserLogin;
    private boolean mIsOrg;
    private Boolean mIsFollowing;

    private UserPresenter mUserPresenter;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        ButterKnife.bind(this);
        loadExtra();
        loadSavedInstanceState(bundle);

        mUserPresenter = new UserPresenterImpl(this, this, mUserLogin);
        setToolbar();
    }

    private void loadExtra() {
        mUserLogin = getIntent().getStringExtra(EXTRA_LOGIN);

        String authLogin = SPUtil.getAuthLogin(this);

        if (authLogin != null) {
            if (mUserLogin == null) {
                showHomeButton();
                mUserLogin = authLogin;
            }

            mIsAuthUser = mUserLogin.equals(authLogin);
        } else {
            mIsAuthUser = true;
        }
    }

    private void setToolbar() {
        setToolbarTitle(mUserLogin);
        final float dimension = getResources().getDimension(R.dimen.app_bar_height);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > dimension * 0.6) {
                    mAppBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    mAppBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUserPresenter.loadUser();
    }

    @Override
    public int getContent() {
        return R.layout.activity_user;
    }

    private void loadSavedInstanceState(Bundle bundle) {
        if (bundle != null) {
            mIsOrg = bundle.getBoolean(KEY_ORG);
            mIsFollowing = bundle.getBoolean(KEY_IS_FOLLOW);
        }
    }

    @Override
    public void onUserLoaded(User user) {
        showProgress(false);
        mIsOrg = user.getType().equals(User.TYPE_ORG);
        String createDate = StringUtil.dateFormat(this, user.getCreatedAt());
        String createAt = getString(R.string.user_registered, createDate);
        String repos = getString(R.string.user_repositories,
                user.getPublicRepos() + user.getTotalPrivateRepos());

        setUserNameAndLogin(user);
        showLayout(mLocationTV, user.getLocation(), mLocationLayout);
        showLayout(mUrlTV, user.getBlog(), mUrlLayout);
        showLayout(mCompanyTV, user.getCompany(), mCompanyLayout);
        showLayout(mBioTV, user.getBio(), mBioLayout);
        showLayout(mCreatedAtTV, createAt, mCreatedAtLayout);

        mRepoTV.setText(repos);

        if (mIsOrg) {
            mOrgsTV.setText(R.string.user_members);
            showLayout(mUserFollowLayout, false);
            showLayout(mUserStarredLayout, false);
        } else {
            mOrgsTV.setText(R.string.user_orgs);
            mFollowersTV.setText(String.valueOf(user.getFollowers()));
            mFollowingTV.setText(String.valueOf(user.getFollowing()));

            if (!mIsAuthUser) {
                mUserPresenter.checkFollow();
            }
        }

        ImageUtil.loadUserAvatar(this, mAvatarIV, user.getAvatarUrl(), mCollapsingToolbarLayout);
    }

    @Override
    public LoaderManager getLoader() {
        return getSupportLoaderManager();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mUserPresenter.reloadUser();
    }

    @Override
    public void onFollowChecked(Boolean isFollowing) {
        mIsFollowing = isFollowing;

        if (mIsFollowing != null) {
            int iconId;
            mFollowFB.setVisibility(View.VISIBLE);

            if (mIsFollowing) {
                iconId = R.drawable.ic_menu_remove;
            } else {
                iconId = R.drawable.ic_menu_add;
            }

            invalidateOptionsMenu();
            mFollowFB.setImageResource(iconId);
        }
    }

    @Override
    public void onFollowSuccess() {
        onFollowChecked(!mIsFollowing);
        int strId = mIsFollowing ? R.string.follow_success : R.string.unfollow_success;
        Snackbar.make(mContentRoot, strId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onFollowFail() {
        int strId = !mIsFollowing ? R.string.follow_fail : R.string.unfollow_fail;
        Snackbar.make(mContentRoot, strId, Snackbar.LENGTH_LONG).show();
    }

    private void setUserNameAndLogin(User user) {
        String userName = user.getName();

        if (userName == null || userName.length() == 0) {
            mNameTV.setText(user.getLogin());
            showLayout(mLoginTV, false);
        } else {
            mLoginTV.setText(user.getLogin());
            showLayout(mLoginTV, true);
            mNameTV.setText(userName);
        }
    }

    private void showLayout(TextView v, String text, View parent) {
        if (text == null || text.length() == 0) {
            showLayout(parent, false);
        } else {
            showLayout(parent, true);
            v.setText(text);
        }
    }

    private void showLayout(View v, boolean flag) {
        v.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    private void follow() {
        if (mIsFollowing) {
            mUserPresenter.unfollow();
        } else {
            mUserPresenter.follow();
        }
    }

    @OnClick(R.id.content_user_following_layout)
    public void onFollowingClick() {
        Intent intent = UserListActivity.newIntent(this,
                mUserLogin, Constants.UserListGroup.FOLLOWING);
        startActivity(intent);
    }

    @OnClick(R.id.content_user_followers_layout)
    public void onFollowersClick() {
        Intent intent = UserListActivity.newIntent(this,
                mUserLogin, Constants.UserListGroup.FOLLOWERS);
        startActivity(intent);
    }

    @OnClick(R.id.content_user_orgs_layout)
    public void onOrgsClick() {
        int group;

        if (mIsOrg) {
            group = Constants.UserListGroup.MEMBERS;
        } else {
            group = Constants.UserListGroup.ORGANIZATIONS;
        }

        Intent intent = UserListActivity.newIntent(this, mUserLogin, group);
        startActivity(intent);
    }

    @OnClick(R.id.content_user_repo_layout)
    public void onReposClick() {
        Intent intent = RepositoryListActivity.newIntent(this, mUserLogin, mIsOrg);
        startActivity(intent);
    }

    @OnClick(R.id.content_user_public_activity_layout)
    public void onPublicActivityClick() {
        Intent intent = EventsActivity.newIntent(this, mUserLogin, mIsOrg, false);
        startActivity(intent);
    }

    @OnClick(R.id.content_user_starred_layout)
    public void onStaredClick() {
        Intent intent = StarredActivity.newIntent(this, mUserLogin);
        startActivity(intent);
    }

    @OnClick(R.id.content_user_follow_floating_button)
    public void onFollowFloatingButtonClick() {
        follow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_page, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mIsAuthUser || mIsOrg || mIsFollowing == null) return true;

        MenuItem item = menu.findItem(R.id.menu_user_follow);

        if (!mAppBarExpanded) {
            item.setVisible(true);
        } else {
            item.setVisible(false);
            //expanded
        }

        if (mIsFollowing) {
            item.setIcon(R.drawable.ic_menu_remove);
            item.setTitle(R.string.menu_follow);
        } else {
            item.setIcon(R.drawable.ic_menu_add);
            item.setTitle(R.string.menu_unfollow);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_user_refresh:
                onRefresh();
                break;
            case R.id.menu_user_follow:
                follow();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ORG, mIsOrg);
        if (mIsFollowing != null) {
            outState.putBoolean(KEY_IS_FOLLOW, mIsFollowing);
        }
    }

    public static Intent newIntent(Context context, String login) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        return intent;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, UserActivity.class);
    }
}
