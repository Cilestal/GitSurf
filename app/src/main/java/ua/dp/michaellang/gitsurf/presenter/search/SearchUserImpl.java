package ua.dp.michaellang.gitsurf.presenter.search;

import android.content.Context;
import ua.dp.michaellang.gitsurf.loader.callbacks.SearchUserCallbacks;
import ua.dp.michaellang.gitsurf.presenter.BasePresenterImpl;
import ua.dp.michaellang.gitsurf.services.entity.SearchUser;

import java.util.List;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public class SearchUserImpl extends BasePresenterImpl
        implements SearchPresenter {
    private static final int LOADER_SEARCH_USER = 0;

    private final SearchUserView mView;
    private final SearchUserCallbacks mCallbacks;

    private String mQuery;

    public SearchUserImpl(Context context, SearchUserView view) {
        super(view);
        mView = view;
        mCallbacks = new SearchUserCallbacks(this, context) {
            @Override
            protected void onResultReady(int id, List<SearchUser> result) {
                mView.onItemsLoaded(result);
            }
        };
    }

    private void newSearch() {
        if (mQuery != null) {
            mView.showProgress(true);
            mView.getLoader()
                    .restartLoader(LOADER_SEARCH_USER, null, mCallbacks);
        }
    }

    @Override
    public void search(String query, String ... args) {
        if (query == null) {
            return;
        }

        boolean flag = mQuery == null;
        mQuery = query;
        mCallbacks.setArguments(mQuery);

        if (flag) {
            if (mQuery != null) {
                mView.showProgress(true);
                mView.getLoader()
                        .initLoader(LOADER_SEARCH_USER, null, mCallbacks);
            }
        } else {
            newSearch();
        }
    }
}
