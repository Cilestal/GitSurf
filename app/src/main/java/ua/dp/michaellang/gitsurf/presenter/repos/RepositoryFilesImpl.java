package ua.dp.michaellang.gitsurf.presenter.repos;

import android.content.Context;
import org.eclipse.egit.github.core.RepositoryContents;
import ua.dp.michaellang.gitsurf.loader.callbacks.RepositoryFilesCallbacks;
import ua.dp.michaellang.gitsurf.presenter.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public class RepositoryFilesImpl extends BasePresenterImpl
        implements RepositoryFilesPresenter {

    private static final int LOADER_REPO_DIR = 0;
    private static final int LOADER_REPO_FILE = 1;

    private RepositoryFilesCallbacks mCallbacks;
    private RepositoryFilesView mView;

    private String mDirectoryPath;

    public RepositoryFilesImpl(final Context context, RepositoryFilesView view,
            final String owner, final String repoName, final String path, final String ref) {
        super(view);
        mView = view;
        mDirectoryPath = path;

        mCallbacks = new RepositoryFilesCallbacks(this, context, owner, repoName, ref, mDirectoryPath) {
            @Override
            protected void onResultReady(int id, List<RepositoryContents> result) {
                if(id == LOADER_REPO_DIR) {
                    mView.onItemsLoaded(sortContent(result));
                } else {
                    mView.onFileLoaded(result.get(0));
                }
            }
        };
    }

    private List<RepositoryContents> sortContent(List<RepositoryContents> contents){
        List<RepositoryContents> dirs = new ArrayList<>();
        List<RepositoryContents> files = new ArrayList<>();

        for (RepositoryContents content : contents) {
            String s = content.getType();
            if (s.equals(RepositoryContents.TYPE_DIR)) {
                dirs.add(content);
            } else if (s.equals(RepositoryContents.TYPE_FILE)) {
                files.add(content);
            }
        }

        List<RepositoryContents> result = new ArrayList<>(dirs);
        result.addAll(files);
        return result;
    }

    @Override
    public void updatePath(String path) {
        mDirectoryPath = path;
    }

    @Override
    public void loadContent() {
        mView.showProgress(true);
        mCallbacks.setPath(mDirectoryPath);
        mView.getLoader().initLoader(LOADER_REPO_DIR, null, mCallbacks);
    }

    @Override
    public void reloadContent() {
        mView.showProgress(true);
        mCallbacks.setPath(mDirectoryPath);
        mView.getLoader().restartLoader(LOADER_REPO_DIR, null, mCallbacks);
    }

    @Override
    public void loadFile(String path) {
        mCallbacks.setPath(path);
        mView.getLoader().restartLoader(LOADER_REPO_FILE, null, mCallbacks);
    }
}
