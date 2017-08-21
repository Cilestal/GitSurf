package ua.dp.michaellang.gitsurf.presenter;

import android.support.v4.content.Loader;
import ua.dp.michaellang.gitsurf.loader.callbacks.LoaderCallbacks;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class PagedPresenterImpl<T> extends BasePresenterImpl
        implements PagedPresenter {
    protected final PagedView<T> mView;
    protected Loader<?> mLoader;

    public PagedPresenterImpl(PagedView<T> view) {
        super(view);
        mView = view;
    }

    @Override
    public void loadItems() {
        mView.showProgress(true);
        mView.setLoadingStatus(true);

        mLoader = mView.getLoader()
                .initLoader(getLoaderId(), null, getCallbacks());
    }

    @Override
    public void loadMoreItems() {
        if (mLoader != null) {
            mView.setLoadingStatus(true);
            mLoader.onContentChanged();
        }
    }

    @Override
    public void reloadItems() {
        mView.showProgress(true);
        mView.setLoadingStatus(true);

        mLoader = mView.getLoader()
                .restartLoader(getLoaderId(), null, getCallbacks());
    }

    protected abstract int getLoaderId();

    protected abstract LoaderCallbacks<?> getCallbacks();
}
