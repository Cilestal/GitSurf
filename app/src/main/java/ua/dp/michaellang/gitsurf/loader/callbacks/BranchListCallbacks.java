package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.RepositoryBranch;
import ua.dp.michaellang.gitsurf.loader.BranchListLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class BranchListCallbacks extends LoaderCallbacks<List<RepositoryBranch>>  {
    private final Context mContext;
    private final String mOwner;
    private final String mRepoName;

    public BranchListCallbacks(BasePresenter presenter, Context context, String owner, String repoName) {
        super(presenter);
        mContext = context;
        mOwner = owner;
        mRepoName = repoName;
    }

    @Override
    public Loader<TaskResult<List<RepositoryBranch>>> onCreateLoader(int id, Bundle args) {
        return new BranchListLoader(mContext, mOwner, mRepoName);
    }
}
