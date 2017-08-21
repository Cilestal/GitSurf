package ua.dp.michaellang.gitsurf.loader;

import android.util.Log;
import org.eclipse.egit.github.core.RepositoryId;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.services.GitSurfStargazerService;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.io.IOException;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class WatchTask extends BaseAsyncTask<Boolean> {
    public static final String TAG = WatchTask.class.toString();

    private final RepositoryId mRepositoryId;
    private final boolean mIsWatching;
    private final boolean mIsNotifying;

    public WatchTask(BasePresenter presenter, String owner,
            String reponame, boolean isWatching, boolean isNotifying) {
        super(presenter);
        mRepositoryId = new RepositoryId(owner, reponame);
        mIsWatching = isWatching;
        mIsNotifying = isNotifying;
    }

    @Override
    protected Boolean doInBackground() {
        try {
            GitSurfStargazerService service = ServiceUtil.getStargazerService();

            if (mIsWatching) {
                service.unwatch(mRepositoryId);
            } else {
                service.watch(mRepositoryId, mIsNotifying);
            }

            return true;
        } catch (IOException e) {
            Log.e(TAG, "Failed to watch.");
            return false;
        }
    }
}
