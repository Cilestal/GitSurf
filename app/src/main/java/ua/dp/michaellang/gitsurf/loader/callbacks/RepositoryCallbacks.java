package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.Repository;
import ua.dp.michaellang.gitsurf.loader.RepositoryLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class RepositoryCallbacks extends LoaderCallbacks<Repository> {
    private final Context mContext;
    private final String mOwner;
    private final String mName;

    public RepositoryCallbacks(BasePresenter presenter, Context context, String owner, String name) {
        super(presenter);
        mContext = context;
        mOwner = owner;
        mName = name;
    }

    @Override
    public Loader<TaskResult<Repository>> onCreateLoader(int id, Bundle args) {
        return new RepositoryLoader(mContext, mOwner, mName);
    }
}
