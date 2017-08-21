package ua.dp.michaellang.gitsurf.view.repos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import org.eclipse.egit.github.core.RepositoryCommit;
import ua.dp.michaellang.gitsurf.adapter.CommitListAdapter;
import ua.dp.michaellang.gitsurf.adapter.LoadMoreAdapter;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;
import ua.dp.michaellang.gitsurf.presenter.repos.CommitListImpl;
import ua.dp.michaellang.gitsurf.presenter.repos.CommitListPresenter;
import ua.dp.michaellang.gitsurf.presenter.repos.CommitListView;
import ua.dp.michaellang.gitsurf.view.PagedFragment;

/**
 * Date: 25.05.17
 *
 * @author Michael Lang
 */
public class CommitListFragment extends PagedFragment<RepositoryCommit>
        implements CommitListView {
    public static final String ARG_OWNER = "ARG_OWNER";
    public static final String ARG_REPO_NAME = "ARG_REPO_NAME";
    public static final String ARG_REF = "ARG_REF";
    public static final String ARG_PATH = "ARG_PATH";

    private CommitListPresenter mPresenter;
    private CommitListAdapter mAdapter;

    private String mOwner;
    private String mRepoName;
    private String mRef;
    private String mPath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArguments();
        mPresenter = new CommitListImpl(getContext(), this, mOwner, mRepoName, mRef, mPath);
        mAdapter = new CommitListAdapter(getContext());
    }

    private void loadArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mOwner = arguments.getString(ARG_OWNER);
            mRepoName = arguments.getString(ARG_REPO_NAME);
            mRef = arguments.getString(ARG_REF);
            mPath = arguments.getString(ARG_PATH);
        }
    }

    @Override
    protected LoadMoreAdapter<RepositoryCommit> getAdapter() {
        return mAdapter;
    }

    @Override
    protected PagedPresenter getPresenter() {
        return mPresenter;
    }

    public static CommitListFragment newInstance(String owner, String repoName,
            String ref, String path) {
        Bundle args = new Bundle();
        args.putString(ARG_OWNER, owner);
        args.putString(ARG_REPO_NAME, repoName);
        args.putString(ARG_REF, ref);
        args.putString(ARG_PATH, path);

        CommitListFragment fragment = new CommitListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
