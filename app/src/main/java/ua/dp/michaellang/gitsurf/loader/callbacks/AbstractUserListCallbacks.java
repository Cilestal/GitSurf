package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 19.03.17
 *
 * @author Michael Lang
 */
public abstract class AbstractUserListCallbacks extends LoaderCallbacks<LoadedPage<User>> {
    protected final String mLogin;
    protected final Context mContext;

    AbstractUserListCallbacks(BasePresenter presenter, String login, Context context) {
        super(presenter);
        mLogin = login;
        mContext = context;
    }
}
