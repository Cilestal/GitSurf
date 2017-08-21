package ua.dp.michaellang.gitsurf.presenter.repos;

import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;

import java.util.Map;

/**
 * Date: 12.04.17
 *
 * @author Michael Lang
 */
public interface RepositoryListPresenter extends PagedPresenter {
    void loadItems(Map<String, String> filters);
}
