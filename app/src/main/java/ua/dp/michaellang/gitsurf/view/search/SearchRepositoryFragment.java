package ua.dp.michaellang.gitsurf.view.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import org.eclipse.egit.github.core.SearchRepository;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.adapter.BaseAdapter;
import ua.dp.michaellang.gitsurf.adapter.RepositorySearchAdapter;
import ua.dp.michaellang.gitsurf.presenter.search.SearchPresenter;
import ua.dp.michaellang.gitsurf.presenter.search.SearchRepositoryImpl;
import ua.dp.michaellang.gitsurf.presenter.search.SearchRepositoryView;
import ua.dp.michaellang.gitsurf.view.RecyclerViewFragment;

/**
 * Date: 08.05.17
 *
 * @author Michael Lang
 */
public class SearchRepositoryFragment extends RecyclerViewFragment<SearchRepository>
        implements SearchRepositoryView,
                   SearchView.OnQueryTextListener {
    private static final String KEY_QUERY = "QUERY";

    private SearchPresenter mPresenter;
    private RepositorySearchAdapter mAdapter;
    private String mQuery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new RepositorySearchAdapter(getContext());
        if (savedInstanceState != null) {
            mQuery = savedInstanceState.getString(KEY_QUERY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = getPresenter();
        mPresenter.search(mQuery);
    }

    @Override
    protected BaseAdapter<SearchRepository> getAdapter() {
        return mAdapter;
    }

    protected SearchPresenter getPresenter() {
        return new SearchRepositoryImpl(getContext(), this);
    }

    public static SearchRepositoryFragment newInstance() {
        return new SearchRepositoryFragment();
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
