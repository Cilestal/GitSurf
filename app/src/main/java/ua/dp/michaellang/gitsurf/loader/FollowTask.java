package ua.dp.michaellang.gitsurf.loader;

import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.services.GitSurfUserService;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.io.IOException;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class FollowTask extends BaseAsyncTask<Boolean> {
    private final String mLogin;
    private final boolean mIsFollowing;

    public FollowTask(BasePresenter presenter, String login, boolean isFollowing) {
        super(presenter);
        mLogin = login;
        mIsFollowing = isFollowing;
    }

    @Override
    protected Boolean doInBackground() {
        try {
            GitSurfUserService userService = ServiceUtil.getUserService();

            if (mIsFollowing) {
                userService.unfollow(mLogin);
            } else {
                userService.follow(mLogin);
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
