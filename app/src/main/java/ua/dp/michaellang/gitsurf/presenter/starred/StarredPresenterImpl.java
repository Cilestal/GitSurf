package ua.dp.michaellang.gitsurf.presenter.starred;

import android.content.Context;
import org.eclipse.egit.github.core.Repository;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.callbacks.LoaderCallbacks;
import ua.dp.michaellang.gitsurf.loader.callbacks.StarredCallbacks;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenterImpl;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public class StarredPresenterImpl
        extends PagedPresenterImpl<Repository>
        implements StarredPresenter {
    private static final int LOADER_REPOS_STARRED = 0;

    private final StarredCallbacks mCallbacks;

    public StarredPresenterImpl(Context context, StarredView view, String login) {
        super(view);

        mCallbacks = new StarredCallbacks(this, context, login) {
            @Override
            protected void onResultReady(int id, LoadedPage<Repository> result) {
                mView.onItemsLoaded(result);
            }
        };
    }

    @Override
    protected int getLoaderId() {
        return LOADER_REPOS_STARRED;
    }

    @Override
    protected LoaderCallbacks<?> getCallbacks() {
        return mCallbacks;
    }
}
