package ua.dp.michaellang.gitsurf.presenter.search;

import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public interface SearchPresenter extends BasePresenter {
    void search(String query, String ... args);
}
