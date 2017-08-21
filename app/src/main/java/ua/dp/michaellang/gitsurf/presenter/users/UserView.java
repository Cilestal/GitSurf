package ua.dp.michaellang.gitsurf.presenter.users;

import android.support.v4.app.LoaderManager;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.presenter.BaseView;

/**
 * Date: 14.03.17
 *
 * @author Michael Lang
 */
public interface UserView extends BaseView {
    void onUserLoaded(User user);
    LoaderManager getLoader();

    void onFollowChecked(Boolean isFollowing);
    void onFollowSuccess();
    void onFollowFail();
}
