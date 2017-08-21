package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import ua.dp.michaellang.gitsurf.loader.IsCollaboratorLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.utils.SPUtil;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class IsCollaboratorCallbacks extends LoaderCallbacks<Boolean> {
    private final Context mContext;
    private final String mOwner;
    private final String mRepoName;

    public IsCollaboratorCallbacks(BasePresenter presenter, Context context, String owner, String repoName) {
        super(presenter);
        mContext = context;
        mOwner = owner;
        mRepoName = repoName;
    }

    @Override
    public Loader<TaskResult<Boolean>> onCreateLoader(int id, Bundle args) {
        String userLogin = SPUtil.getAuthLogin(mContext);
        return new IsCollaboratorLoader(mContext, mOwner, mRepoName, userLogin);
    }
}
