package ua.dp.michaellang.gitsurf.presenter.gists;

import android.content.Context;
import org.eclipse.egit.github.core.Gist;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.callbacks.GistsCallbacks;
import ua.dp.michaellang.gitsurf.loader.callbacks.LoaderCallbacks;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenterImpl;

/**
 * Date: 13.05.17
 *
 * @author Michael Lang
 */
public class GistsImpl extends PagedPresenterImpl<Gist>
        implements GistsPresenter {

    private static final int LOADER_GISTS = 0;

    private final GistsCallbacks mCallbacks;

    public GistsImpl(Context context, GistsView view, String login) {
        super(view);
        mCallbacks = new GistsCallbacks(this, context, login) {
            @Override
            protected void onResultReady(int id, LoadedPage<Gist> result) {
                mView.onItemsLoaded(result);
            }
        };
    }

    @Override
    protected int getLoaderId() {
        return LOADER_GISTS;
    }

    @Override
    protected LoaderCallbacks<?> getCallbacks() {
        return mCallbacks;
    }
}
