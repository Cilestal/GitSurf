package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.CommitService;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.PageIteratorLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import static ua.dp.michaellang.gitsurf.Constants.PAGE_ITERATOR_SIZE;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class CommitListCallbacks extends LoaderCallbacks<LoadedPage<RepositoryCommit>> {
    private final Context mContext;
    private final RepositoryId mRepositoryId;
    private final String mRef;
    private final String mPath;

    public CommitListCallbacks(BasePresenter presenter, Context context, String login,
            String repo, String ref, String path) {
        super(presenter);
        mContext = context;
        mRef = ref;
        mPath = path;
        mRepositoryId = new RepositoryId(login, repo);
    }

    @Override
    public Loader<TaskResult<LoadedPage<RepositoryCommit>>> onCreateLoader(int id, Bundle args) {
        CommitService service = ServiceUtil.getCommitService();
        PageIterator<RepositoryCommit> pageIterator =
                service.pageCommits(mRepositoryId, mRef, mPath, PAGE_ITERATOR_SIZE);

        return new PageIteratorLoader<>(mContext, pageIterator);
    }
}
