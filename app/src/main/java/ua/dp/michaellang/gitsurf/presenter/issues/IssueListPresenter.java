package ua.dp.michaellang.gitsurf.presenter.issues;

import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;

import java.util.Map;

/**
 * Date: 13.05.17
 *
 * @author Michael Lang
 */
public interface IssueListPresenter extends PagedPresenter {
    void loadItems(Map<String, String> filters);
}

