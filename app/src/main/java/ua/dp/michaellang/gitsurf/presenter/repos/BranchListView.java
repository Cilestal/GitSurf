package ua.dp.michaellang.gitsurf.presenter.repos;

import android.support.v4.app.LoaderManager;
import org.eclipse.egit.github.core.RepositoryBranch;
import ua.dp.michaellang.gitsurf.presenter.BaseView;

import java.util.List;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public interface BranchListView extends BaseView{
    void onBranchListLoaded(List<RepositoryBranch> list);
    LoaderManager getLoader();
}
