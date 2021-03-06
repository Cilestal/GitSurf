package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;
import ua.dp.michaellang.gitsurf.Constants;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.PageIteratorLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.services.GitSurfRepositoryService;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class ContributorsCallbacks extends LoaderCallbacks<LoadedPage<User>> {
    private final Context mContext;
    private final RepositoryId mRepositoryId;

    public ContributorsCallbacks(BasePresenter presenter, Context context, String owner, String repoName) {
        super(presenter);
        mContext = context;
        mRepositoryId = new RepositoryId(owner, repoName);
    }

    @Override
    public Loader<TaskResult<LoadedPage<User>>> onCreateLoader(int id, Bundle args) {
        GitSurfRepositoryService service = ServiceUtil.getRepositoryService();

        PageIterator<User> pageIterator
                = service.pageContributors(mRepositoryId, Constants.PAGE_ITERATOR_SIZE);

        return new PageIteratorLoader<>(mContext, pageIterator);
    }
}
