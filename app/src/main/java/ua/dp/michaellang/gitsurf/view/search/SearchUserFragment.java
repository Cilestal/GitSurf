package ua.dp.michaellang.gitsurf.view.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.adapter.BaseAdapter;
import ua.dp.michaellang.gitsurf.adapter.SearchUserAdapter;
import ua.dp.michaellang.gitsurf.presenter.search.SearchPresenter;
import ua.dp.michaellang.gitsurf.presenter.search.SearchUserImpl;
import ua.dp.michaellang.gitsurf.presenter.search.SearchUserView;
import ua.dp.michaellang.gitsurf.services.entity.SearchUser;
import ua.dp.michaellang.gitsurf.view.RecyclerViewFragment;

/**
 * Date: 08.05.17
 *
 * @author Michael Lang
 */
public class SearchUserFragment extends RecyclerViewFragment<SearchUser>
        implements SearchUserView,
                   SearchView.OnQueryTextListener {
    private static final String KEY_QUERY = "QUERY";
    private String mQuery;

    private SearchPresenter mPresenter;
    private SearchUserAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mQuery = savedInstanceState.getString(KEY_QUERY);
        }

        mAdapter = new SearchUserAdapter(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new SearchUserImpl(getContext(), this);
        mPresenter.search(mQuery);
    }

    @Override
    protected BaseAdapter<SearchUser> getAdapter() {
        return mAdapter;
    }

    public static SearchUserFragment newInstance() {
        return new SearchUserFragment();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPresenter.search(mQuery);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mQuery = newText;
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_QUERY, mQuery);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.toolbar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        searchView.setQuery(mQuery, false);
    }
}
