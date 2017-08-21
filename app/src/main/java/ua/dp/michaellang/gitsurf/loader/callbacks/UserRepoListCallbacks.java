package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.PageIterator;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.PageIteratorLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.services.GitSurfRepositoryService;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import static ua.dp.michaellang.gitsurf.Constants.PAGE_ITERATOR_SIZE;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class UserRepoListCallbacks extends AbstractRepositoryListCallbacks {
    private final Context mContext;
    private final String mLogin;
    private final boolean mIsOrg;

    public UserRepoListCallbacks(BasePresenter presenter, Context context,
            String login, boolean isOrg) {
        super(presenter);
        mContext = context;
        mLogin = login;
        mIsOrg = isOrg;
    }

    @Override
    public Loader<TaskResult<LoadedPage<Repository>>> onCreateLoader(int id, Bundle args) {
        GitSurfRepositoryService repositoryService = ServiceUtil.getRepositoryService();

        String authLogin = SPUtil.getAuthLogin(mContext);
        PageIterator<Repository> pageIterator;

        if (authLogin != null && authLogin.equals(mLogin)) {
            pageIterator = repositoryService.pageRepositories(mFilters, PAGE_ITERATOR_SIZE);
        } else {
            pageIterator = mIsOrg
                    ? repositoryService.pageOrgRepositories(mLogin, mFilters, PAGE_ITERATOR_SIZE)
                    : repositoryService.pageRepositories(mLogin, mFilters, PAGE_ITERATOR_SIZE);
        }

        return new PageIteratorLoader<>(mContext, pageIterator);
    }
}
