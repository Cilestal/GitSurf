package ua.dp.michaellang.gitsurf.presenter.users;

import android.content.Context;
import android.support.v4.content.Loader;
import android.support.v4.os.AsyncTaskCompat;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.loader.FollowTask;
import ua.dp.michaellang.gitsurf.loader.callbacks.IsFollowingCallbacks;
import ua.dp.michaellang.gitsurf.loader.callbacks.UserLoaderCallbacks;
import ua.dp.michaellang.gitsurf.presenter.BasePresenterImpl;

/**
 * Date: 14.03.17
 *
 * @author Michael Lang
 */
public class UserPresenterImpl extends BasePresenterImpl
        implements UserPresenter {
    private static final int LOADER_USER = 1;
    private static final int LOADER_IS_FOLLOWING = 2;

    private final UserView mView;
    private final String mLogin;

    private final UserLoaderCallbacks mUserCallbacks;
    private final IsFollowingCallbacks mIsFollowingCallbacks;

    private Loader<?> mUserLoader;

    public UserPresenterImpl(UserView view,
            final Context context, final String login) {
        super(view);
        mView = view;
        mLogin = login;

        mUserCallbacks = new UserLoaderCallbacks(this, context, login) {
            @Override
            protected void onResultReady(int id, User result) {
                mView.onUserLoaded(result);
            }
        };

        mIsFollowingCallbacks = new IsFollowingCallbacks(this, context, login) {
            @Override
            protected void onResultReady(int id, Boolean result) {
                mView.onFollowChecked(result);
            }
        };
    }

    @Override
    public void loadUser() {
        mView.showProgress(true);
        mUserLoader = mView
                .getLoader()
                .initLoader(LOADER_USER, null, mUserCallbacks);
    }

    @Override
    public void reloadUser() {
        mView.showProgress(true);
        this.mUserLoader.onContentChanged();
    }

    @Override
    public void checkFollow() {
        mView.getLoader()
                .restartLoader(LOADER_IS_FOLLOWING, null, mIsFollowingCallbacks);
    }

    private FollowTask createFollowTask(boolean isFollowing){
        return new FollowTask(this, mLogin, isFollowing) {
            @Override
            protected void onResultReady(Boolean success) {
                if (success) {
                    mView.onFollowSuccess();
                } else {
                    mView.onFollowFail();
                }
            }
        };
    }

    @Override
    public void follow() {
        AsyncTaskCompat.executeParallel(createFollowTask(false));
    }

    @Override
    public void unfollow() {
        AsyncTaskCompat.executeParallel(createFollowTask(true));
    }
}
