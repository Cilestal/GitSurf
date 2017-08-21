package ua.dp.michaellang.gitsurf.presenter.repos;

import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public interface RepositoryPresenter extends BasePresenter {
    void loadRepository();
    void reloadReadme(String ref);
    void reloadRepository();
    void forkRepository();
    void star();
    void unstar();
    void watch(boolean receiveNotification);
    void unwatch();
}
