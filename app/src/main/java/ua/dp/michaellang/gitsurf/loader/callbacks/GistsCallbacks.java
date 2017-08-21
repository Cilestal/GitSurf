package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.GistService;
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
public abstract class GistsCallbacks extends LoaderCallbacks<LoadedPage<Gist>> {
    private final String mLogin;
    private final Context mContext;

    public GistsCallbacks(BasePresenter presenter, Context context, String login) {
        super(presenter);
        mContext = context;
        mLogin = login;
    }

    @Override
    public Loader<TaskResult<LoadedPage<Gist>>> onCreateLoader(int id, Bundle args) {
        GistService gistService = ServiceUtil.getGistService();
        PageIterator<Gist> pageIterator = gistService.pageGists(mLogin, PAGE_ITERATOR_SIZE);
        return new PageIteratorLoader<>(mContext, pageIterator);
    }
}
