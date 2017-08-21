package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import android.util.Log;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.ContentsService;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class RepositoryFilesLoader extends BaseLoader<List<RepositoryContents>> {
    public static final String TAG = RepositoryFilesLoader.class.toString();

    private RepositoryId mRepositoryId;
    private String mRef;
    private String mPath;

    public RepositoryFilesLoader(Context context, String owner, String repoName,
            String ref, String path) {
        super(context);
        mRepositoryId = new RepositoryId(owner, repoName);
        mRef = ref;
        mPath = path;
    }

    @Override
    protected List<RepositoryContents> doLoadInBackground() {
        ContentsService contentsService = ServiceUtil.getContentsService();
        try {
            return contentsService.getContents(mRepositoryId, mPath, mRef);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return Collections.emptyList();
        }
    }
}
