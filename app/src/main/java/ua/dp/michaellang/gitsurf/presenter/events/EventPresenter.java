package ua.dp.michaellang.gitsurf.presenter.events;

import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;

/**
 * Date: 13.05.17
 *
 * @author Michael Lang
 */
public interface EventPresenter extends PagedPresenter {
    @Override
    void loadItems();
}
