package ua.dp.michaellang.gitsurf.presenter.issues;

import org.eclipse.egit.github.core.Issue;
import ua.dp.michaellang.gitsurf.presenter.PagedView;

/**
 * Date: 12.04.17
 *
 * @author Michael Lang
 */
public interface IssueListView extends PagedView<Issue> {
    void updateFilter(String filter);
}
