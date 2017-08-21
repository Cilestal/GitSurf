package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import ua.dp.michaellang.gitsurf.loader.ReadmeLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class ReadmeCallbacks extends LoaderCallbacks<String> {

    private final Context mContext;
    private final String mOwner;
    private final String mRepoName;
    private String mRef;

    public ReadmeCallbacks(BasePresenter presenter, Context context, String owner, String repoName) {
        super(presenter);
        mContext = context;
        mOwner = owner;
        mRepoName = repoName;
    }

    @Override
    public Loader<TaskResult<String>> onCreateLoader(int id, Bundle args) {
        return new ReadmeLoader(mContext, mOwner, mRepoName, mRef);
    }

    public void setRef(String ref) {
        mRef = ref;
    }
}
