package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Date: 04.03.17
 *
 * @author Michael Lang
 */
abstract class BaseLoader<T> extends AsyncTaskLoader<TaskResult<T>> {
    private static final String TAG = BaseLoader.class.toString();

    BaseLoader(Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    public TaskResult<T> loadInBackground() {
        try {
            T data = doLoadInBackground();
            return new TaskResult<>(data);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new TaskResult<>(e);
        }
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    protected abstract T doLoadInBackground() throws Exception;
}
