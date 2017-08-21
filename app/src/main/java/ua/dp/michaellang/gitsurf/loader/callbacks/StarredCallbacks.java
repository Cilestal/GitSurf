package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.StargazerService;
import ua.dp.michaellang.gitsurf.Constants;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.PageIteratorLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.io.IOException;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class StarredCallbacks extends LoaderCallbacks<LoadedPage<Repository>> {
    private static final String TAG = StarredCallbacks.class.toString();

    private final Context mContext;
    private final String mLogin;

    public StarredCallbacks(BasePresenter presenter, Context context, String login) {
        super(presenter);
        mContext = context;
        mLogin = login;
    }

    @Override
    public Loader<TaskResult<LoadedPage<Repository>>> onCreateLoader(int id, Bundle args) {
        StargazerService service = ServiceUtil.getStargazerService();

        PageIterator<Repository> pageIterator = null;
        try {
            pageIterator = service.pageStarred(mLogin, Constants.PAGE_ITERATOR_SIZE);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return new PageIteratorLoader<>(mContext, pageIterator);
    }
}
