package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.IssueService;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.PageIteratorLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.util.Map;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class IssueListCallbacks
        extends LoaderCallbacks<LoadedPage<Issue>>
        implements FilteredCallbacks {
    private final Context mContext;
    private final String mLogin;
    private final String mRepo;

    private Map<String, String> mFilterData;

    public IssueListCallbacks(BasePresenter presenter, Context context, String login,
            String repo) {
        super(presenter);
        mContext = context;
        mLogin = login;
        mRepo = repo;
    }

    public IssueListCallbacks(BasePresenter presenter, Context context,
            String login, String repo, Map<String, String> filters) {
        super(presenter);
        mContext = context;
        mLogin = login;
        mRepo = repo;
        mFilterData = filters;
    }

    @Override
    public void setFilters(Map<String, String> filterData) {
        mFilterData = filterData;
    }

    @Override
    public Loader<TaskResult<LoadedPage<Issue>>> onCreateLoader(int id, Bundle args) {
        IssueService service = ServiceUtil.getIssueService();
        PageIterator<? extends Issue> pageIterator;

        if (mLogin == null) {
            pageIterator = service.pageIssues(mFilterData);
        } else {
            pageIterator = service.pageIssues(mLogin, mRepo, mFilterData);
        }

        return new PageIteratorLoader<>(mContext, pageIterator);
    }
}
