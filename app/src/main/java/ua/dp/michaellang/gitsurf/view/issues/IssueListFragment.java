package ua.dp.michaellang.gitsurf.view.issues;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import org.eclipse.egit.github.core.Issue;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.adapter.IssueListAdapter;
import ua.dp.michaellang.gitsurf.adapter.LoadMoreAdapter;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;
import ua.dp.michaellang.gitsurf.presenter.issues.IssueListImpl;
import ua.dp.michaellang.gitsurf.presenter.issues.IssueListView;
import ua.dp.michaellang.gitsurf.view.PagedFragment;

import java.util.HashMap;

import static org.eclipse.egit.github.core.service.IssueService.*;
import static ua.dp.michaellang.gitsurf.Constants.IssueList.STATE_ALL;

/**
 * Date: 01.06.17
 *
 * @author Michael Lang
 */
public class IssueListFragment extends PagedFragment<Issue>
        implements IssueListView {

    private static final String ARG_LOGIN = "LOGIN";
    private static final String ARG_REPO = "REPO";
    private static final String ARG_FILTER = "FILTER";
    private static final String ARG_IS_PULL_REQUEST = "IS_PULL_REQUEST";

    private static final String KEY_SORT_MENU_ID = "KEY_SORT_MENU_ID";
    private static final String KEY_STATE_MENU_ID = "KEY_STATE_MENU_ID";
    private static final String KEY_STATE = "KEY_STATE";
    private static final String KEY_DIRECTION = "KEY_DIRECTION";
    private static final String KEY_SORT = "KEY_SORT";

    private static int mStateMenuId;
    private static int mSortMenuId;
    private HashMap<String, String> mFilters;

    private String mLogin;
    private String mRepo;
    private boolean mIsPullRequest;

    private IssueListImpl mPresenter;
    private IssueListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilters = new HashMap<>();

        loadState(savedInstanceState);
        loadArgs();
        setHasOptionsMenu(true);
        mPresenter = new IssueListImpl(getContext(), this, mLogin, mRepo, mFilters, mIsPullRequest);
        mAdapter = new IssueListAdapter(getContext());
    }

    private void loadArgs() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mLogin = arguments.getString(ARG_LOGIN);
            mRepo = arguments.getString(ARG_REPO);
            mIsPullRequest = arguments.getBoolean(ARG_IS_PULL_REQUEST, false);

            String filter = arguments.getString(ARG_FILTER, FILTER_ASSIGNED);
            mFilters.put(FIELD_FILTER, filter);
        }
    }

    private void loadState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSortMenuId = savedInstanceState.getInt(KEY_SORT_MENU_ID, -1);
            mStateMenuId = savedInstanceState.getInt(KEY_STATE_MENU_ID, -1);

            String sort = savedInstanceState.getString(KEY_SORT, SORT_CREATED);
            String state = savedInstanceState.getString(KEY_STATE, STATE_OPEN);
            String direction = savedInstanceState.getString(KEY_DIRECTION, DIRECTION_DESCENDING);

            mFilters.put(FIELD_FILTER, sort);
            mFilters.put(FIELD_FILTER, state);
            mFilters.put(FIELD_FILTER, direction);
        } else {
            mSortMenuId = -1;
            mStateMenuId = -1;
        }
    }

    @Override
    protected LoadMoreAdapter<Issue> getAdapter() {
        return mAdapter;
    }

    @Override
    protected PagedPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_issue_list, menu);

        if (mSortMenuId > 0) {
            menu.findItem(mSortMenuId).setChecked(true);
        }

        if (mStateMenuId > 0) {
            menu.findItem(mStateMenuId).setChecked(true);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SORT_MENU_ID, mSortMenuId);
        outState.putInt(KEY_STATE_MENU_ID, mStateMenuId);

        outState.putString(KEY_DIRECTION, mFilters.get(FIELD_DIRECTION));
        outState.putString(KEY_SORT, mFilters.get(FIELD_SORT));
        outState.putString(KEY_STATE, mFilters.get(FILTER_STATE));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_issues_state_opened:
                setState(STATE_OPEN, itemId);
                break;
            case R.id.menu_issues_state_closed:
                setState(STATE_CLOSED, itemId);
                break;
            case R.id.menu_issues_state_all:
                setState(STATE_ALL, itemId);
                break;
            case R.id.menu_issues_sort_comments_asc:
                setSort(SORT_COMMENTS, DIRECTION_ASCENDING, itemId);
                break;
            case R.id.menu_issues_sort_comments_desc:
                setSort(SORT_COMMENTS, DIRECTION_DESCENDING, itemId);
                break;
            case R.id.menu_issues_sort_created_asc:
                setSort(SORT_CREATED, DIRECTION_ASCENDING, itemId);
                break;
            case R.id.menu_issues_sort_created_desc:
                setSort(SORT_CREATED, DIRECTION_DESCENDING, itemId);
                break;
            case R.id.menu_issues_sort_updated_asc:
                setSort(SORT_UPDATED, DIRECTION_ASCENDING, itemId);
                break;
            case R.id.menu_issues_sort_updated_desc:
                setSort(SORT_UPDATED, DIRECTION_DESCENDING, itemId);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        mPresenter.loadItems(mFilters);
        return super.onOptionsItemSelected(item);
    }

    private void setState(String value, @IdRes int id) {
        mFilters.put(FILTER_STATE, value);
        mStateMenuId = id;
    }

    private void setSort(String value, String direction, @IdRes int id) {
        mFilters.put(FIELD_SORT, value);
        mFilters.put(FIELD_DIRECTION, direction);
        mSortMenuId = id;
    }

    @Override
    public void updateFilter(String filter) {
        mFilters.put(FIELD_FILTER, filter);
        mPresenter.loadItems(mFilters);
    }

    public static IssueListFragment newInstance(String login, String repo, boolean isPullRequest) {
        Bundle args = new Bundle();
        args.putString(ARG_LOGIN, login);
        args.putString(ARG_REPO, repo);
        args.putBoolean(ARG_IS_PULL_REQUEST, isPullRequest);

        IssueListFragment fragment = new IssueListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static IssueListFragment newInstance(String filter, boolean isPullRequest) {
        Bundle args = new Bundle();
        args.putString(ARG_FILTER, filter);
        args.putBoolean(ARG_IS_PULL_REQUEST, isPullRequest);

        IssueListFragment fragment = new IssueListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
