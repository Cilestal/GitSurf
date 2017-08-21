package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.loader.UserLoader;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 19.03.17
 *
 * @author Michael Lang
 */
public abstract class UserLoaderCallbacks extends LoaderCallbacks<User> {
    private final String mLogin;
    private final Context mContext;

    public UserLoaderCallbacks(BasePresenter presenter, Context context, String login) {
        super(presenter);
        mLogin = login;
        mContext = context;
    }

    @Override
    public Loader<TaskResult<User>> onCreateLoader(int id, Bundle args) {
        return new UserLoader(mContext, mLogin);
    }
}
