package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import org.eclipse.egit.github.core.RepositoryId;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.io.IOException;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class IsCollaboratorLoader extends BaseLoader<Boolean> {
    private final RepositoryId mRepositoryId;
    private final String mLogin;

    public IsCollaboratorLoader(Context context, String owner, String repo, String login) {
        super(context);
        this.mRepositoryId = new RepositoryId(owner, repo);
        this.mLogin = login;
    }

    @Override
    protected Boolean doLoadInBackground() {
        if (mLogin == null) return false;

        try {
            return ServiceUtil.getRepositoryService()
                    .isCollaborator(mRepositoryId, mLogin);
        } catch (IOException e) {
            return false;
        }
    }
}
