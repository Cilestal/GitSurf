package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.RepositoryContents;
import ua.dp.michaellang.gitsurf.loader.RepositoryFilesLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class RepositoryFilesCallbacks extends LoaderCallbacks<List<RepositoryContents>> {
    private String mOwner;
    private String mRepoName;
    private String mRef;
    private String mPath;
    private Context mContext;

    public RepositoryFilesCallbacks(BasePresenter presenter, Context context,
            String owner, String repoName, String ref, String path) {
        super(presenter);
        mOwner = owner;
        mRepoName = repoName;
        mRef = ref;
        mPath = path;
        mContext = context;
    }

    public void setPath(String path) {
        mPath = path;
    }

    @Override
    public Loader<TaskResult<List<RepositoryContents>>> onCreateLoader(int id, Bundle args) {
        return new RepositoryFilesLoader(mContext, mOwner, mRepoName, mRef, mPath);
    }
}
