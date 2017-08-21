package ua.dp.michaellang.gitsurf.presenter.repos;

import android.content.Context;
import org.eclipse.egit.github.core.RepositoryBranch;
import ua.dp.michaellang.gitsurf.loader.callbacks.BranchListCallbacks;
import ua.dp.michaellang.gitsurf.presenter.BasePresenterImpl;

import java.util.List;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public class BranchListImpl extends BasePresenterImpl implements BranchListPresenter {
    private static final int LOADER_BRANCH_LIST = 0;

    private final BranchListCallbacks mCallbacks;
    private final BranchListView mView;

    public BranchListImpl(Context context, final BranchListView view, String owner, String repoName) {
        super(view);
        mView = view;

        mCallbacks = new BranchListCallbacks(this, context, owner, repoName) {
            @Override
            protected void onResultReady(int id, List<RepositoryBranch> result) {
                view.onBranchListLoaded(result);
            }
        };
    }

    @Override
    public void loadBranchList() {
        mView.getLoader().initLoader(LOADER_BRANCH_LIST, null, mCallbacks);
    }
}
