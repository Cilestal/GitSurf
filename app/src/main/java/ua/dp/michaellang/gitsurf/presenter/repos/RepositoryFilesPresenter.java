package ua.dp.michaellang.gitsurf.presenter.repos;

import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public interface RepositoryFilesPresenter extends BasePresenter {
    void loadContent();
    void reloadContent();
    void loadFile(String path);
    void updatePath(String path);
}
