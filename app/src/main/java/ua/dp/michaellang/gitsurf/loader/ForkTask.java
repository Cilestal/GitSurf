package ua.dp.michaellang.gitsurf.loader;

import android.util.Log;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryId;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.io.IOException;

/**
 * Date: 16.07.2017
 *
 * @author Michael Lang
 */
public abstract class ForkTask extends BaseAsyncTask<Repository> {
    public static final String TAG = ForkTask.class.toString();

    private RepositoryId mRepositoryId;

    public ForkTask(BasePresenter presenter, String owner, String repoName) {
        super(presenter);
        mRepositoryId = new RepositoryId(owner, repoName);
    }

    @Override
    protected Repository doInBackground(){
        try {
            return ServiceUtil.getRepositoryService().forkRepository(mRepositoryId);
        } catch (IOException e) {
            Log.e(TAG, "Failed to fork.");
            return null;
        }
    }
}
