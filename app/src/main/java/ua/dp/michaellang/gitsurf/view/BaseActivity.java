package ua.dp.michaellang.gitsurf.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import ua.dp.michaellang.gitsurf.GitSurfApplication;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.presenter.BaseView;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.view.login.LoginActivity;
import ua.dp.michaellang.gitsurf.view.users.UserActivity;

/**
 * Date: 24.02.17
 *
 * @author Michael Lang
 */
public class BaseActivity extends AppCompatActivity implements BaseView {
    //private long mBackPressed = System.currentTimeMillis();

    protected ViewGroup mContentRoot;
    protected SwipeRefreshLayout mSwipeLayout;

    protected Snackbar mSnackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContent());
        mContentRoot = (ViewGroup) findViewById(getContentRoot());

        initSwipeRefresh();
    }

    public int getContentRoot() {
        return R.id.base_content;
    }

    public int getContent() {
        return R.layout.activity_base;
    }

    private void initSwipeRefresh() {
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.base_swipe_container);

        if (mSwipeLayout != null) {
            if (isSwipeRefreshEnabled()) {
                mSwipeLayout.setOnRefreshListener(this);
            } else {
                mSwipeLayout.setEnabled(false);
            }
        }
    }

    protected boolean isSwipeRefreshEnabled() {
        return true;
    }

    protected final void setContent(Fragment fragment) {
        FragmentManager sfm = getSupportFragmentManager();

        sfm.beginTransaction()
                .replace(R.id.base_content, fragment)
                .commit();
    }

    protected final Fragment getContentFragment() {
        return getSupportFragmentManager()
                .findFragmentById(R.id.base_content);
    }

    protected final void setContent(@LayoutRes int layoutResId) {
        View contentView = getLayoutInflater()
                .inflate(layoutResId, mContentRoot, false);
        mContentRoot.addView(contentView);
    }

    protected final void goToTopLevelActivity() {
        Intent intent = getTopActivity();
        startActivity(intent);
        finish();
    }

    protected final Intent getTopActivity() {
        if (SPUtil.isAuthorized(this)) {
            return UserActivity.newIntent(this);
        } else {
            return LoginActivity.newIntent(this);
        }
    }

    @Override
    public void onAuthError() {
        logout();
        if (mSwipeLayout != null) {
            mSwipeLayout.setEnabled(false);
        }
        mSnackbar = Snackbar
                .make(mContentRoot, R.string.load_auth_failure_notice, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.login_title, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToTopLevelActivity();
                    }
                });
        mSnackbar.show();
    }

    @Override
    public void onLoadFail() {
        mSnackbar = Snackbar
                .make(mContentRoot, R.string.error_unknown_host, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRefresh();
                    }
                });
        mSnackbar.show();
    }

    @Override
    public void onRefresh() {
        if (mSwipeLayout != null) {
            mSwipeLayout.setRefreshing(false);
        }
        // TODO: 13.07.2017 removeSwipeRefresh
        hideSnackBar();
        refreshFragment();
    }

    protected final void hideSnackBar() {
        if (mSnackbar != null) {
            mSnackbar.dismiss();
        }
    }

    private void refreshFragment() {
        BaseView fragment = (BaseView) getSupportFragmentManager()
                .findFragmentById(R.id.base_content);

        if (fragment != null) {
            fragment.onRefresh();
        }
    }

    @Override
    public void showProgress(boolean flag) {
        showContentRoot(flag);
    }

    protected final void showContentRoot(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        mContentRoot.setVisibility(show ? View.GONE : View.VISIBLE);
        mContentRoot
                .animate()
                .setDuration(shortAnimTime)
                .alpha(show ? 0 : 1)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mContentRoot.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });
    }

    protected void logout() {
        GitSurfApplication.getInstance().logout();
    }

/*    @Override
    public void onBackPressed() {
        if (mBackPressed + Constants.EXIT_TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Snackbar.make(mContentRoot, R.string.back_button_to_exit, Snackbar.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
        }
    }*/
}
