package ua.dp.michaellang.gitsurf.presenter.search;

import android.content.Context;
import org.eclipse.egit.github.core.SearchRepository;
import ua.dp.michaellang.gitsurf.loader.callbacks.SearchRepositoryCallbacks;
import ua.dp.michaellang.gitsurf.presenter.BasePresenterImpl;

import java.util.List;

/**
 * Date: 07.06.17
 *
 * @author Michael Lang
 */
public class SearchRepositoryImpl extends BasePresenterImpl
        implements SearchPresenter {
    private static final int LOADER_SEARCH_REPOSITORY = 0;

    private final SearchRepositoryView mView;
    private final SearchRepositoryCallbacks mCallbacks;

    private String mQuery;

    public SearchRepositoryImpl(Context context, SearchRepositoryView view) {
        super(view);
        mView = view;
        mCallbacks = new SearchRepositoryCallbacks(this, context) {
            @Override
            protected void onResultReady(int id, List<SearchRepository> result) {
                mView.onItemsLoaded(result);
            }
        };
    }

    // TODO: 13.07.2017 удалить гавнокод 
    private void newSearch() {
        if (mQuery != null) {
            mView.showProgress(true);
            mView.getLoader()
                    .restartLoader(LOADER_SEARCH_REPOSITORY, null, mCallbacks);
        }
    }

    @Override
    public void search(String query, String ... args) {
        if (query == null) {
            return;
        }

        boolean flag = mQuery == null;
        mQuery = query;
        mCallbacks.setArguments(mQuery, args);

        if (flag) {
            if (mQuery != null) {
                mView.showProgress(true);
                mView.getLoader()
                        .initLoader(LOADER_SEARCH_REPOSITORY, null, mCallbacks);
            }
        } else {
            newSearch();
        }
    }
}