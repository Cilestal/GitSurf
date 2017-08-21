package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryId;
import ua.dp.michaellang.gitsurf.services.GitSurfRepositoryService;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class BranchListLoader extends BaseLoader<List<RepositoryBranch>> {
    private final String mOwner;
    private final String mRepoName;

    public BranchListLoader(Context context, String owner, String repoName) {
        super(context);
        mOwner = owner;
        mRepoName = repoName;
    }

    @Override
    protected List<RepositoryBranch> doLoadInBackground() throws Exception {
        GitSurfRepositoryService service = ServiceUtil.getRepositoryService();
        return service.getBranches(new RepositoryId(mOwner, mRepoName));
    }
}
