package ua.dp.michaellang.gitsurf.view.issues;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import butterknife.ButterKnife;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.adapter.SpinnerArrayListAdapter;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

import static org.eclipse.egit.github.core.service.IssueService.*;

/**
 * Date: 01.06.17
 *
 * @author Michael Lang
 */
public class IssueListActivity extends ToolbarActivity
        implements AdapterView.OnItemSelectedListener {

    private static final String KEY_FILTER = "FILTER";

    private static final String EXTRA_IS_PULL_REQUEST = "ua.dp.michaellang.gitsurf.view.issues.isPullRequest";
    private static final String EXTRA_OWNER = "ua.dp.michaellang.gitsurf.view.issues.owner";
    private static final String EXTRA_REPO = "ua.dp.michaellang.gitsurf.view.issues.repo";

    private static final int CREATED = 0;
    private static final int ASSIGNED = 1;
    private static final int MENTIONED = 2;

    private IssueListFragment mFragment;

    private String mFilter;

    private boolean mIsPullRequest;
    private String mOwner;
    private String mRepoName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        loadExtra();

        if (savedInstanceState != null) {
            mFilter = savedInstanceState.getString(KEY_FILTER);
        }

        setToolbar();
        setFragment();
    }

    private void setToolbar() {
        if (mOwner == null) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayShowTitleEnabled(false);
            }

            int checkedNaviItemId = mIsPullRequest
                    ? R.id.navigation_my_pull_request
                    : R.id.navigation_my_issues;

            mNavigationView.setCheckedItem(checkedNaviItemId);
            showHomeButton();
            initSpinner();
        } else {
            int title = mIsPullRequest ? R.string.repo_pulls : R.string.repo_issues;
            setToolbarTitle(getString(title, mRepoName));
        }
    }

    private void loadExtra() {
        Intent intent = getIntent();
        mIsPullRequest = intent.getBooleanExtra(EXTRA_IS_PULL_REQUEST, false);
        mOwner = intent.getStringExtra(EXTRA_OWNER);
        mRepoName = intent.getStringExtra(EXTRA_REPO);
    }

    private void initSpinner() {
        int arrayId = mIsPullRequest
                ? R.array.pull_request_spinner_list_items
                : R.array.issues_spinner_list_items;

        SpinnerAdapter adapter = SpinnerArrayListAdapter
                .newInstance(mToolbar.getContext(), arrayId);

        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
        mSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_FILTER, mFilter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String newFilter;

        switch (position) {
            case CREATED:
                newFilter = FILTER_CREATED;
                break;
            case ASSIGNED:
                newFilter = FILTER_ASSIGNED;
                break;
            case MENTIONED:
                newFilter = FILTER_MENTIONED;
                break;
            default:
                return;
        }

        if (!newFilter.equals(mFilter)) {
            mFilter = newFilter;

            if (mFragment != null) {
                mFragment.updateFilter(mFilter);
            }
        }
    }

    private void setFragment() {
        mFragment = (IssueListFragment) getContentFragment();

        if (getContentFragment() == null) {
            if (mOwner == null) {
                mFragment = IssueListFragment.newInstance(mFilter, mIsPullRequest);
            } else {
                mFragment = IssueListFragment.newInstance(mOwner, mRepoName, mIsPullRequest);
            }
            setContent(mFragment);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static Intent newIntent(Context context, boolean isPullRequest) {
        Intent intent = new Intent(context, IssueListActivity.class);
        intent.putExtra(EXTRA_IS_PULL_REQUEST, isPullRequest);
        return intent;
    }

    public static Intent newIntent(Context context, String login, String repo, boolean isPullRequest) {
        Intent intent = new Intent(context, IssueListActivity.class);
        intent.putExtra(EXTRA_OWNER, login);
        intent.putExtra(EXTRA_REPO, repo);
        intent.putExtra(EXTRA_IS_PULL_REQUEST, isPullRequest);
        return intent;
    }
}
