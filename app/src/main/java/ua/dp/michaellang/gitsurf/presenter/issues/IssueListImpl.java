package ua.dp.michaellang.gitsurf.presenter.issues;

import android.content.Context;
import org.eclipse.egit.github.core.Issue;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.callbacks.IssueListCallbacks;
import ua.dp.michaellang.gitsurf.loader.callbacks.LoaderCallbacks;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Date: 13.05.17
 *
 * @author Michael Lang
 */
public class IssueListImpl extends PagedPresenterImpl<Issue>
        implements IssueListPresenter {

    private static final int LOADER_ISSUES = 0;

    private final IssueListCallbacks mCallbacks;

    public IssueListImpl(Context context, IssueListView view,
            String login, String repo, Map<String, String> filters, final boolean isPullRequest) {
        super(view);
        mCallbacks = new IssueListCallbacks(this, context, login, repo, filters) {
            @Override
            protected void onResultReady(int id, LoadedPage<Issue> result) {
                List<Issue> pulls = getPulls(result.getResults());

                if(isPullRequest){
                    result.getResults().retainAll(pulls);
                } else {
                    result.getResults().removeAll(pulls);
                }

                mView.onItemsLoaded(result);
            }
        };
    }

    private List<Issue> getPulls(List<Issue> list){
        List<Issue> pulls = new ArrayList<>();

        for (Issue issue : list) {
            if(issue.getPullRequest() != null){
                pulls.add(issue);
            }
        }

        return pulls;
    }

    @Override
    public void loadItems(Map<String, String> filters) {
        mCallbacks.setFilters(filters);
        reloadItems();
    }

    @Override
    protected int getLoaderId() {
        return LOADER_ISSUES;
    }

    @Override
    protected LoaderCallbacks<?> getCallbacks() {
        return mCallbacks;
    }
}
