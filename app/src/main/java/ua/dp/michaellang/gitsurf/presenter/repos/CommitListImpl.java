package ua.dp.michaellang.gitsurf.presenter.repos;

import android.content.Context;
import org.eclipse.egit.github.core.RepositoryCommit;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.callbacks.CommitListCallbacks;
import ua.dp.michaellang.gitsurf.loader.callbacks.LoaderCallbacks;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenterImpl;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public class CommitListImpl extends PagedPresenterImpl<RepositoryCommit>
        implements CommitListPresenter {

    private static final int LOADER_COMMIT_LIST = 0;
    private final CommitListCallbacks mCallbacks;

    public CommitListImpl(Context context, final CommitListView view, String owner,
            String repoName, String ref, String path) {
        super(view);

        mCallbacks = new CommitListCallbacks(this, context, owner, repoName, ref, path) {
            @Override
            protected void onResultReady(int id, LoadedPage<RepositoryCommit> result) {
                view.onItemsLoaded(result);
            }
        };
    }

    @Override
    protected int getLoaderId() {
        return LOADER_COMMIT_LIST;
    }

    @Override
    protected LoaderCallbacks<?> getCallbacks() {
        return mCallbacks;
    }
}
