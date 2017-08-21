package ua.dp.michaellang.gitsurf.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import ua.dp.michaellang.gitsurf.R;

/**
 * Date: 24.02.17
 *
 * @author Michael Lang
 */
public class ToolbarActivity extends LeftDrawerActivity {
    private final String TAG = getClass().getName();

    protected Toolbar mToolbar;
    protected Spinner mSpinner;
    protected ProgressBar mToolBarProgressBar;
    protected AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mSpinner = (Spinner) findViewById(R.id.toolbar_spinner);
        mToolBarProgressBar = (ProgressBar) mToolbar.findViewById(R.id.toolbar_progress_bar);
        mToolBarProgressBar.setVisibility(View.GONE);

        //back down when click nav by default
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public int getContent() {
        return R.layout.activity_app_bar;
    }

    protected void showHomeButton() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    protected final void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    protected final void setToolbarTitle(@StringRes int titleRes) {
        setToolbarTitle(getString(titleRes));
    }

    @Override
    public final void showProgress(final boolean show) {
        super.showProgress(show);
        showToolBarProgressBar(show);
    }

    protected final void showToolBarProgressBar(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        mToolBarProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mToolBarProgressBar
                .animate()
                .setDuration(shortAnimTime)
                .alpha(show ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mToolBarProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void onLoadFail() {
        super.onLoadFail();
        showToolBarProgressBar(false);
    }

    @Override
    public void onAuthError() {
        super.onAuthError();
        showToolBarProgressBar(false);
    }
}
