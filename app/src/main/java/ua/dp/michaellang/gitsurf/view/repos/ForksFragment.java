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
import ua.dp.michaellang.gitsurf.presenter.repos.RepositoryListView;
import ua.dp.michaellang.gitsurf.view.PagedFragment;

import java.util.HashMap;

import static ua.dp.michaellang.gitsurf.Constants.RepositoryList.*;

/**
 * Date: 25.05.17
 *
 * @author Michael Lang
 */
public class ForksFragment extends PagedFragment<Repository>
        implements RepositoryListView {

    private static final String ARG_OWNER = "ARG_OWNER";
    private static final String ARG_REPO = "ARG_REPO";

    private static final String KEY_SORT_MENU_ITEM = "KEY_SORT_MENU_ITEM";
    private static final String KEY_SORT = "KEY_SORT";

    private String mOwner;
    private String mRepoName;

    private HashMap<String, String> mFilters;
    private int mSortMenuId;

    private RepositoryListImpl mPresenter;
    private RepositoryListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilters = new HashMap<>();

        loadState(savedInstanceState);
        loadArguments();
        setHasOptionsMenu(true);
        mPresenter = new RepositoryListImpl(getContext(), this, mOwner, mRepoName);
        mAdapter = new RepositoryListAdapter(getContext());
    }

    private void loadArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mOwner = arguments.getString(ARG_OWNER);
            mRepoName = arguments.getString(ARG_REPO);
        }
    }

    private void loadState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSortMenuId = savedInstanceState.getInt(KEY_SORT_MENU_ITEM, -1);

            String sort = savedInstanceState.getString(KEY_SORT);
            if(sort != null) {
                mFilters.put(FIELD_SORT, sort);
            }
        } else {
            mSortMenuId = -1;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SORT_MENU_ITEM, mSortMenuId);
        outState.putString(KEY_SORT, mFilters.get(FIELD_SORT));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fork_list, menu);

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
            case R.id.menu_fork_list_sort_newest:
                setSort(SORT_NEWEST, itemId);
                break;
            case R.id.menu_fork_list_sort_oldest:
                setSort(SORT_OLDEST, itemId);
                break;
            case R.id.menu_fork_list_sort__stargazers:
                setSort(SORT_STARGAZERS, itemId);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        mPresenter.loadItems(mFilters);
        return super.onOptionsItemSelected(item);
    }

    private void setSort(String value, @IdRes int id) {
        mFilters.put(FIELD_SORT, value);
        mSortMenuId = id;
    }

    public static ForksFragment newInstance(String owner, String repoName) {
        Bundle args = new Bundle();
        args.putString(ARG_OWNER, owner);
        args.putString(ARG_REPO, repoName);

        ForksFragment fragment = new ForksFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
