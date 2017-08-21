package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryId;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class RepositoryLoader extends BaseLoader<Repository> {
    private final RepositoryId mRepositoryId;

    public RepositoryLoader(Context context, final String owner, final String name) {
        super(context);
        mRepositoryId = new RepositoryId(owner, name);
    }

    @Override
    protected Repository doLoadInBackground() throws Exception {
        return ServiceUtil.getRepositoryService().getRepository(mRepositoryId);
    }
}
