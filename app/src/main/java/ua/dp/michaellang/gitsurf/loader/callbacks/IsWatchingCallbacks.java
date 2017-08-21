package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import ua.dp.michaellang.gitsurf.loader.IsWatchingLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 16.07.2017
 *
 * @author Michael Lang
 */
public abstract class IsWatchingCallbacks extends LoaderCallbacks<Boolean>  {
    private final Context mContext;
    private final String mOwner;
    private final String mRepoName;

    public IsWatchingCallbacks(BasePresenter presenter, Context context, String owner, String repoName) {
        super(presenter);
        mContext = context;
        mOwner = owner;
        mRepoName = repoName;
    }

    @Override
    public Loader<TaskResult<Boolean>> onCreateLoader(int id, Bundle args) {
        return new IsWatchingLoader(mContext, mOwner, mRepoName);
    }
}