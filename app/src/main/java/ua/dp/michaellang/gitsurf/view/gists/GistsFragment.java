package ua.dp.michaellang.gitsurf.view.gists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import org.eclipse.egit.github.core.Gist;
import ua.dp.michaellang.gitsurf.adapter.GistsAdapter;
import ua.dp.michaellang.gitsurf.adapter.LoadMoreAdapter;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;
import ua.dp.michaellang.gitsurf.presenter.gists.GistsImpl;
import ua.dp.michaellang.gitsurf.presenter.gists.GistsView;
import ua.dp.michaellang.gitsurf.view.PagedFragment;

/**
 * Date: 01.06.17
 *
 * @author Michael Lang
 */
public class GistsFragment extends PagedFragment<Gist>
        implements GistsView {

    private GistsAdapter mAdapter;
    private PagedPresenter mPresenter;

    private static final String ARG_LOGIN = "ARG_LOGIN";
    private String mLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArgs();

        mAdapter = new GistsAdapter(getContext());
        mPresenter = new GistsImpl(getContext(), this, mLogin);
    }

    private void loadArgs() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mLogin = arguments.getString(ARG_LOGIN);
        }
    }

    @Override
    protected LoadMoreAdapter<Gist> getAdapter() {
        return mAdapter;
    }

    @Override
    protected PagedPresenter getPresenter() {
        return mPresenter;
    }

    public static GistsFragment newInstance(String login) {
        Bundle args = new Bundle();
        args.putString(ARG_LOGIN, login);

        GistsFragment fragment = new GistsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
