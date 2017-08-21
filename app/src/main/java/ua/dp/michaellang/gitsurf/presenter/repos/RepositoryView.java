package ua.dp.michaellang.gitsurf.presenter.repos;

import android.support.v4.app.LoaderManager;
import org.eclipse.egit.github.core.Repository;
import ua.dp.michaellang.gitsurf.presenter.BaseView;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public interface RepositoryView extends BaseView {
    void onRepositoryLoaded(Repository repository);
    void onIssuesCountLoaded(int issues, int pulls);
    void onReadmeLoaded(String text);
    void onCollaboratorChecked(boolean isCollaborator);
    void onWatchingChecked(Boolean result);
    void onStarringChecked(Boolean isStarred);
    void onForkSuccess(Repository repository);
    void onForkFail();
    void onStarSuccess();
    void onStarFail();
    void onWatchSuccess();
    void onWatchFail();

    LoaderManager getLoader();
}
