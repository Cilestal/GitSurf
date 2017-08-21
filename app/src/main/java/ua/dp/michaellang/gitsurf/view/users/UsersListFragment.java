package ua.dp.michaellang.gitsurf.view.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.adapter.LoadMoreAdapter;
import ua.dp.michaellang.gitsurf.adapter.UserListAdapter;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;
import ua.dp.michaellang.gitsurf.presenter.users.UserListImpl;
import ua.dp.michaellang.gitsurf.presenter.users.UserListView;
import ua.dp.michaellang.gitsurf.view.PagedFragment;

/**
 * Date: 04.04.17
 *
 * @author Michael Lang
 */
public class UsersListFragment extends PagedFragment<User>
        implements UserListView {

    private static final String ARG_LOGIN = "LOGIN";
    private static final String ARG_REPO = "REPO";
    private static final String ARG_GROUP = "GROUP";

    private String mLogin;
    private String mRepoName;
    private int mGroup;

    private UserListImpl mPresenter;
    private UserListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArguments();
        mPresenter = new UserListImpl(getContext(), this, mLogin, mRepoName, mGroup);
        mAdapter = new UserListAdapter(getContext());
    }

    private void loadArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mLogin = arguments.getString(ARG_LOGIN);
            mGroup = arguments.getInt(ARG_GROUP);
            mRepoName = arguments.getString(ARG_REPO);
        }
    }

    public static UsersListFragment newInstance(String login, int group) {
        Bundle args = new Bundle();
        args.putString(ARG_LOGIN, login);
        args.putInt(ARG_GROUP, group);

        UsersListFragment fragment = new UsersListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static UsersListFragment newInstance(String login, String repoName, int group) {
        Bundle args = new Bundle();
        args.putString(ARG_LOGIN, login);
        args.putString(ARG_REPO, repoName);
        args.putInt(ARG_GROUP, group);

        UsersListFragment fragment = new UsersListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected LoadMoreAdapter<User> getAdapter() {
        return mAdapter;
    }

    @Override
    protected PagedPresenter getPresenter() {
        return mPresenter;
    }
}
