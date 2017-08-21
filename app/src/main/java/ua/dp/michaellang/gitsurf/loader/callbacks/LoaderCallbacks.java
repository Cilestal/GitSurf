package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 04.03.17
 *
 * @author Michael Lang
 */
public abstract class LoaderCallbacks<T>
        implements LoaderManager.LoaderCallbacks<TaskResult<T>> {

    private final BasePresenter mPresenter;

    public LoaderCallbacks(BasePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadFinished(Loader<TaskResult<T>> loader, TaskResult<T> data) {
        if (data.isSuccess()) {
            onResultReady(loader.getId(), data.getResult());
        } else if (data.isAuthError()) {
            mPresenter.onAuthError();
        } else {
            mPresenter.onLoadFail();
        }
    }

    @Override
    public void onLoaderReset(Loader<TaskResult<T>> loader) {
        mPresenter.onLoaderReset();
    }

    protected abstract void onResultReady(int id, T result);
}
