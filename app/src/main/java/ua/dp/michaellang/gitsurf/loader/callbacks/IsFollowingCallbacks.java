package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import ua.dp.michaellang.gitsurf.loader.IsFollowingUserLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class IsFollowingCallbacks extends LoaderCallbacks<Boolean> {
    private final Context mContext;
    private final String mUserName;

    public IsFollowingCallbacks(BasePresenter presenter, Context context, String userName) {
        super(presenter);
        mContext = context;
        mUserName = userName;
    }

    @Override
    public Loader<TaskResult<Boolean>> onCreateLoader(int id, Bundle args) {
        return new IsFollowingUserLoader(mContext, mUserName);
    }
}
