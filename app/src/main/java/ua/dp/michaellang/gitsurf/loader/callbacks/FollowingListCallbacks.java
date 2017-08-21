package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.UserService;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.loader.PageIteratorLoader;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import static ua.dp.michaellang.gitsurf.Constants.PAGE_ITERATOR_SIZE;

/**
 * Date: 19.03.17
 *
 * @author Michael Lang
 */
public abstract class FollowingListCallbacks extends AbstractUserListCallbacks {

    public FollowingListCallbacks(BasePresenter presenter, String login,
            Context context) {
        super(presenter, login, context);
    }

    @Override
    public Loader<TaskResult<LoadedPage<User>>> onCreateLoader(int id, Bundle args) {
        UserService userService = ServiceUtil.getUserService();
        PageIterator<User> page = userService.pageFollowing(mLogin, PAGE_ITERATOR_SIZE);
        return new PageIteratorLoader<>(mContext, page);
    }
}