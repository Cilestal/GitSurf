package ua.dp.michaellang.gitsurf.presenter.repos;

import android.content.Context;
import org.eclipse.egit.github.core.Repository;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.callbacks.*;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenterImpl;

import java.util.Map;

/**
 * Date: 13.04.17
 *
 * @author Michael Lang
 */
public class RepositoryListImpl extends PagedPresenterImpl<Repository>
        implements RepositoryListPresenter {

    private static final int LOADER_REPOS_LIST = 0;

    private final AbstractRepositoryListCallbacks mCallbacks;

    public RepositoryListImpl(Context context, RepositoryListView view, String login, boolean isOrg) {
        super(view);
        mCallbacks = new UserRepoListCallbacks(this, context, login, isOrg) {
            @Override
            protected void onResultReady(int id, LoadedPage<Repository> result) {
                mView.onItemsLoaded(result);
            }
        };
    }

    public RepositoryListImpl(Context context, RepositoryListView view, String owner, String repoName) {
        super(view);
        mCallbacks = new ForksCallbacks(this, context, owner, repoName) {
            @Override
            protected void onResultReady(int id, LoadedPage<Repository> result) {
                mView.onItemsLoaded(result);
            }
        };
    }

    @Override
    protected int getLoaderId() {
        return LOADER_REPOS_LIST;
    }

    @Override
    protected LoaderCallbacks<?> getCallbacks() {
        return mCallbacks;
    }

    @Override
    public void loadItems(Map<String, String> filters) {
        mCallbacks.setFilters(filters);
        reloadItems();
    }
}
