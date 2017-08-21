package ua.dp.michaellang.gitsurf.presenter.users;

import android.content.Context;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.Constants.UserListGroup;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.callbacks.*;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenterImpl;

/**
 * Date: 17.03.17
 *
 * @author Michael Lang
 */
public class UserListImpl extends PagedPresenterImpl<User>
        implements UserListPresenter {
    private static final int LOADER_USER_LIST = 0;

    private LoaderCallbacks<?> mCallbacks;

    public UserListImpl(Context context, UserListView view,
            String login, String repoName, int group) {
        super(view);

        switch (group) {
            case UserListGroup.FOLLOWERS:
                mCallbacks = new FollowersListCallbacks(this, login, context) {
                    @Override
                    protected void onResultReady(int id, LoadedPage<User> result) {
                        UserListImpl.this.onResultReady(result);
                    }
                };
                break;
            case UserListGroup.FOLLOWING:
                mCallbacks = new FollowingListCallbacks(this, login, context) {
                    @Override
                    protected void onResultReady(int id, LoadedPage<User> result) {
                        UserListImpl.this.onResultReady(result);
                    }
                };
                break;
            case UserListGroup.ORGANIZATIONS:
                mCallbacks = new OrgsListCallbacks(this, login, context) {
                    @Override
                    protected void onResultReady(int id, LoadedPage<User> result) {
                        UserListImpl.this.onResultReady(result);
                    }
                };
                break;
            case UserListGroup.MEMBERS:
                mCallbacks = new MembersListCallbacks(this, login, context) {
                    @Override
                    protected void onResultReady(int id, LoadedPage<User> result) {
                        UserListImpl.this.onResultReady(result);
                    }
                };
                break;
            case UserListGroup.STARGAZERS:
                mCallbacks = new StargazersCallbacks(this, context, login, repoName) {
                    @Override
                    protected void onResultReady(int id, LoadedPage<User> result) {
                        UserListImpl.this.onResultReady(result);
                    }
                };
                break;
            case UserListGroup.WATCHERS:
                mCallbacks = new WatchersCallbacks(this, context, login, repoName) {
                    @Override
                    protected void onResultReady(int id, LoadedPage<User> result) {
                        UserListImpl.this.onResultReady(result);
                    }
                };
                break;
            case UserListGroup.COLLABORATORS:
                mCallbacks = new CollaboratorsCallbacks(this, context, login, repoName) {
                    @Override
                    protected void onResultReady(int id, LoadedPage<User> result) {
                        UserListImpl.this.onResultReady(result);
                    }
                };
                break;
            case UserListGroup.CONTRIBUTORS:
                mCallbacks = new ContributorsCallbacks(this, context, login, repoName) {
                    @Override
                    protected void onResultReady(int id, LoadedPage<User> result) {
                        UserListImpl.this.onResultReady(result);
                    }
                };
                break;
        }
    }

    private void onResultReady(LoadedPage<User> result) {
        mView.onItemsLoaded(result);
    }

    @Override
    protected int getLoaderId() {
        return LOADER_USER_LIST;
    }

    @Override
    protected LoaderCallbacks<?> getCallbacks() {
        return mCallbacks;
    }
}
