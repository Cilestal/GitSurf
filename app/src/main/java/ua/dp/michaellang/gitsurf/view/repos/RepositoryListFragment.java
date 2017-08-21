package ua.dp.michaellang.gitsurf.view.repos;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import org.eclipse.egit.github.core.Repository;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.adapter.LoadMoreAdapter;
import ua.dp.michaellang.gitsurf.adapter.RepositoryListAdapter;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;
import ua.dp.michaellang.gitsurf.presenter.repos.RepositoryListImpl;
import ua.dp.michaellang.gitsurf.presenter.repos.RepositoryListPresenter;
import ua.dp.michaellang.gitsurf.presenter.repos.RepositoryListView;
import ua.dp.michaellang.gitsurf.view.PagedFragment;

import java.util.HashMap;

import static ua.dp.michaellang.gitsurf.Constants.RepositoryList.*;

/**
 * Date: 12.04.17
 *
 * @author Michael Lang
 */
public class RepositoryListFragment extends PagedFragment<Repository>
        implements RepositoryListView {
    private static final String ARG_LOGIN = "ARG_LOGIN";
    private static final String ARG_IS_ORG = "ARG_IS_ORG";

    private static final String KEY_SORT_MENU_ITEM = "KEY_SORT_MENU_ITEM";
    private static final String KEY_SORT = "KEY_SORT";
    private static final String KEY_DIRECTION = "KEY_SORT_MENU_ITEM";

    private String mLogin;
    private boolean mIsOrg;

    private HashMap<String, String> mFilters;
    private int mSortMenuId;

    private RepositoryListPresenter mPresenter;
    private RepositoryListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilters = new HashMap<>();

        loadState(savedInstanceState);
        loadArguments();
        setHasOptionsMenu(true);
        mPresenter = new RepositoryListImpl(getContext(), this, mLogin, mIsOrg);
        mAdapter = new RepositoryListAdapter(getContext());
    }

    private void loadState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSortMenuId = savedInstanceState.getInt(KEY_SORT_MENU_ITEM, -1);

            String sort = savedInstanceState.getString(KEY_SORT);
            mFilters.put(FIELD_SORT, sort);
            String direction = savedInstanceState.getString(KEY_DIRECTION);
            mFilters.put(FIELD_DIRECTION, direction);
        }
    }

    private void loadArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mLogin = arguments.getString(ARG_LOGIN);
            mIsOrg = arguments.getBoolean(ARG_IS_ORG);
        }
    }

    @Override
    protected LoadMoreAdapter<Repository> getAdapter() {
        return mAdapter;
    }

    @Override
    protected PagedPresenter getPresenter() {
        return mPresenter;
    }

    public static RepositoryListFragment newInstance(String login, boolean isOrg) {
        Bundle args = new Bundle();
        args.putString(ARG_LOGIN, login);
        args.putBoolean(ARG_IS_ORG, isOrg);

        RepositoryListFragment fragment = new RepositoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_repo_list, menu);

        if (mSortMenuId > 0) {
            menu.findItem(mSortMenuId).setChecked(true);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_repo_list_sort_created_asc:
                setSort(SORT_CREATED, DIRECTION_ASCENDING, itemId);
                break;
            case R.id.menu_repo_list_sort_created_desc:
                setSort(SORT_CREATED, DIRECTION_DESCENDING, itemId);
                break;
            case R.id.menu_repo_list_sort_full_name_asc:
                setSort(SORT_NAME, DIRECTION_ASCENDING, itemId);
                break;
            case R.id.menu_repo_list_sort_full_name_desc:
                setSort(SORT_NAME, DIRECTION_DESCENDING, itemId);
                break;
            case R.id.menu_repo_list_sort_updated_asc:
                setSort(SORT_UPDATED, DIRECTION_ASCENDING, itemId);
                break;
            case R.id.menu_repo_list_sort_updated_desc:
                setSort(SORT_UPDATED, DIRECTION_DESCENDING, itemId);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        mPresenter.loadItems(mFilters);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SORT_MENU_ITEM, mSortMenuId);
        String sort = mFilters.get(FIELD_SORT);
        String direction = mFilters.get(FIELD_DIRECTION);

        outState.putString(KEY_SORT, sort);
        outState.putString(KEY_DIRECTION, direction);
    }

    private void setSort(String value, String direction, @IdRes int id) {
        mFilters.put(FIELD_SORT, value);
        mFilters.put(FIELD_DIRECTION, direction);
        mSortMenuId = id;
    }

}
