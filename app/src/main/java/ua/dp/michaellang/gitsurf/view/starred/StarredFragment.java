package ua.dp.michaellang.gitsurf.view.starred;

import android.os.Bundle;
import android.support.annotation.Nullable;
import org.eclipse.egit.github.core.Repository;
import ua.dp.michaellang.gitsurf.adapter.LoadMoreAdapter;
import ua.dp.michaellang.gitsurf.adapter.RepositoryListAdapter;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;
import ua.dp.michaellang.gitsurf.presenter.starred.StarredPresenter;
import ua.dp.michaellang.gitsurf.presenter.starred.StarredPresenterImpl;
import ua.dp.michaellang.gitsurf.presenter.starred.StarredView;
import ua.dp.michaellang.gitsurf.view.PagedFragment;

/**
 * Date: 14.06.17
 *
 * @author Michael Lang
 */
public class StarredFragment extends PagedFragment<Repository>
        implements StarredView {

    private static final String ARG_LOGIN = "LOGIN";
    private String mLogin;

    private StarredPresenter mPresenter;
    private RepositoryListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArguments();
        mPresenter = new StarredPresenterImpl(getContext(), this, mLogin);
        mAdapter = new RepositoryListAdapter(getContext());
    }

    private void loadArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mLogin = arguments.getString(ARG_LOGIN);
        }
    }

    @Override
    protected LoadMoreAdapter<Repository> getAdapter() {
        return mAdapter;
    }

    @Override
    protected PagedPresenter getPresenter() {
        return mPresenter;
    }

    public static StarredFragment newInstance(String login) {
        Bundle args = new Bundle();
        args.putString(ARG_LOGIN, login);

        StarredFragment fragment = new StarredFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
