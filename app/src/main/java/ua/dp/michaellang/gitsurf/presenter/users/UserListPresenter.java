package ua.dp.michaellang.gitsurf.presenter.users;

import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;

/**
 * Date: 17.03.17
 *
 * @author Michael Lang
 */
public interface UserListPresenter extends PagedPresenter {
    @Override
    void loadItems();
}
