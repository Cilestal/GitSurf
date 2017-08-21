package ua.dp.michaellang.gitsurf.loader;

import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Log;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

import java.io.IOException;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class BaseAsyncTask<Result> extends AsyncTask<Void, Void, TaskResult<Result>> {
    private static final String TAG = BaseAsyncTask.class.toString();

    private final BasePresenter mPresenter;

    public BaseAsyncTask(BasePresenter presenter) {
        mPresenter = presenter;
    }

    public void execute() {
        AsyncTaskCompat.executeParallel(this);
    }

    @Override
    protected TaskResult<Result> doInBackground(Void... params) {
        try {
            Result data = doInBackground();
            return new TaskResult<>(data);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new TaskResult<>(e);
        }
    }

    @Override
    protected void onPostExecute(TaskResult<Result> data) {
        if (data.isSuccess()) {
            onResultReady(data.getResult());
        } else if (data.isAuthError()) {
            mPresenter.onAuthError();
        } else {
            mPresenter.onLoadFail();
        }
    }

    protected abstract Result doInBackground() throws IOException;

    protected abstract void onResultReady(Result result);
}
