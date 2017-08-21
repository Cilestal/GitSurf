package ua.dp.michaellang.gitsurf.presenter.users;

import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 14.03.17
 *
 * @author Michael Lang
 */
public interface UserPresenter extends BasePresenter {
    void loadUser();
    void reloadUser();

    void checkFollow();
    void follow();
    void unfollow();
}
