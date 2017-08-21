package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import ua.dp.michaellang.gitsurf.services.GitSurfUserService;
import ua.dp.michaellang.gitsurf.services.entity.SearchUser;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class SearchUserLoader extends BaseLoader<List<SearchUser>> {
    private final String mLogin;

    public SearchUserLoader(Context context, @NonNull String login) {
        super(context);
        this.mLogin = login;
    }

    @Override
    protected List<SearchUser> doLoadInBackground() throws Exception {
        GitSurfUserService userService = ServiceUtil.getUserService();
        return userService.searchUsers(mLogin);
    }
}
