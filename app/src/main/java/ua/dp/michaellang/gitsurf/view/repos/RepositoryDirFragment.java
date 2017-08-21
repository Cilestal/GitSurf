package ua.dp.michaellang.gitsurf.view.repos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import org.eclipse.egit.github.core.RepositoryContents;
import ua.dp.michaellang.gitsurf.adapter.BaseAdapter;
import ua.dp.michaellang.gitsurf.adapter.RepositoryFilesAdapter;
import ua.dp.michaellang.gitsurf.presenter.repos.RepositoryFilesImpl;
import ua.dp.michaellang.gitsurf.presenter.repos.RepositoryFilesPresenter;
import ua.dp.michaellang.gitsurf.presenter.repos.RepositoryFilesView;
import ua.dp.michaellang.gitsurf.utils.StringUtil;
import ua.dp.michaellang.gitsurf.view.RecyclerViewFragment;

/**
 * Date: 25.05.17
 *
 * @author Michael Lang
 */
public class RepositoryDirFragment
        extends RecyclerViewFragment<RepositoryContents>
        implements RepositoryFilesView,
                   RepositoryFilesAdapter.OnRepositoryFileClickListener {

    private static final String ARG_OWNER = "ARG_OWNER";
    private static final String ARG_REPO_NAME = "ARG_REPO_NAME";
    private static final String ARG_PATH = "ARG_PATH";
    private static final String ARG_REF = "ARG_REF";

    private RepositoryFilesPresenter mPresenter;
    private RepositoryFilesAdapter mAdapter;
    private Callbacks mCallbacks;

    private String mOwner;
    private String mRepoName;
    private String mPath;
    private String mRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArgs();
        mPresenter = new RepositoryFilesImpl(getContext(), this, mOwner, mRepoName, mPath, mRef);
        mAdapter = new RepositoryFilesAdapter(getContext(), this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.loadContent();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.reloadContent();
    }

    private void loadArgs() {
        Bundle arguments = getArguments();

        if (arguments != null) {
            mOwner = arguments.getString(ARG_OWNER);
            mRepoName = arguments.getString(ARG_REPO_NAME);
            mPath = arguments.getString(ARG_PATH);
            mRef = arguments.getString(ARG_REF);
        }
    }

    @Override
    protected BaseAdapter<RepositoryContents> getAdapter() {
        return mAdapter;
    }

    public static RepositoryDirFragment newInstance(String owner, String repoName, String path, String ref) {
        Bundle args = new Bundle();
        args.putString(ARG_OWNER, owner);
        args.putString(ARG_REPO_NAME, repoName);
        args.putString(ARG_PATH, path);
        args.putString(ARG_REF, ref);

        RepositoryDirFragment fragment = new RepositoryDirFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void updatePath(String path) {
        mPresenter.updatePath(path);
        mPresenter.reloadContent();
    }

    @Override
    public void onClick(RepositoryContents file) {
        String type = file.getType();

        if (mCallbacks != null) {
            if (type.equals(RepositoryContents.TYPE_DIR)) {
                mCallbacks.onDirectorySelected(file.getPath());
            } else if (type.equals(RepositoryContents.TYPE_FILE)) {
                //если код - загружаем, остальные возвращаем без загрузки
                if (StringUtil.isCodeFile(file.getPath())) {
                    mPresenter.loadFile(file.getPath());
                } else {
                    onFileLoaded(file);
                }
            }
        }
    }

    @Override
    public void onFileLoaded(RepositoryContents file) {
        if (mCallbacks != null) {
            mCallbacks.onFileSelected(file);
        }
    }

    public interface Callbacks {
        void onDirectorySelected(String path);

        void onFileSelected(RepositoryContents file);
    }
}
