package ua.dp.michaellang.gitsurf.loader;

import android.util.Log;
import org.eclipse.egit.github.core.RepositoryId;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.io.IOException;

/**
 * Date: 16.07.2017
 *
 * @author Michael Lang
 */
public abstract class StarTask extends BaseAsyncTask<Boolean> {
    private static final String TAG = StarTask.class.toString();

    private RepositoryId mRepositoryId;
    private boolean mIsStarred;

    public StarTask(BasePresenter presenter, String owner,
            String repoName, boolean isStarred) {
        super(presenter);
        mRepositoryId = new RepositoryId(owner, repoName);
        mIsStarred = isStarred;
    }

    @Override
    protected Boolean doInBackground() {
        try {
            if(mIsStarred){
                ServiceUtil.getStargazerService().unstar(mRepositoryId);
            } else {
                ServiceUtil.getStargazerService().star(mRepositoryId);
            }

            return true;
        } catch (IOException e){
            Log.e(TAG, "Starring failed.");
            return false;
        }
    }
}
