package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.loader.OrganizationsLoader;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 19.03.17
 *
 * @author Michael Lang
 */
public abstract class OrgsListCallbacks extends AbstractUserListCallbacks {
    public OrgsListCallbacks(BasePresenter presenter, String login, Context context) {
        super(presenter, login, context);
    }

    @Override
    public Loader<TaskResult<LoadedPage<User>>> onCreateLoader(int id, Bundle args) {
        return new OrganizationsLoader(mContext, mLogin);
    }
}
