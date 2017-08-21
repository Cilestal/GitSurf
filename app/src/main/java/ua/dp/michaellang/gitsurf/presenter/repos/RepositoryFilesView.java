package ua.dp.michaellang.gitsurf.presenter.repos;

import org.eclipse.egit.github.core.RepositoryContents;
import ua.dp.michaellang.gitsurf.presenter.ListView;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public interface RepositoryFilesView extends ListView<RepositoryContents> {
    void onFileLoaded(RepositoryContents file);
}
