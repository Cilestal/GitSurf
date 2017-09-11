package ua.dp.michaellang.gitsurf.presenter.login;

import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 04.03.17
 *
 * @author Michael Lang
 */
public interface LoginPresenter extends BasePresenter{
    void auth(String login, String password);
    void auth(String code);
}
