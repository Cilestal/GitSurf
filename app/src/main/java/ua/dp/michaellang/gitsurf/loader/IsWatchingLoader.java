package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import android.util.Log;
import org.eclipse.egit.github.core.RepositoryId;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.io.IOException;

/**
 * Date: 16.07.2017
 *
 * @author Michael Lang
 */
public class IsWatchingLoader extends BaseLoader<Boolean> {
    private static final String TAG = IsStarringRepository.class.toString();

    private RepositoryId mRepositoryId;

    public IsWatchingLoader(Context context, String owner, String repoName) {
        super(context);
        mRepositoryId = new RepositoryId(owner, repoName);
    }

    @Override
    protected Boolean doLoadInBackground() {
        try {
            return ServiceUtil.getStargazerService().isWatching(mRepositoryId);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
