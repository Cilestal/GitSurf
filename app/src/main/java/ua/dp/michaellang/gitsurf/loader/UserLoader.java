package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.UserService;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.io.IOException;

/**
 * Date: 04.03.17
 *
 * @author Michael Lang
 */
public class UserLoader extends BaseLoader<User> {
    private String mLogin;

    public UserLoader(Context context) {
        super(context);
    }

    public UserLoader(Context context, final String login) {
        super(context);
        this.mLogin = login;
    }

    @Override
    protected User doLoadInBackground() throws IOException {
        UserService userService = ServiceUtil.getUserService();

        if (mLogin == null) {
            return userService.getUser();
        } else {
            return userService.getUser(mLogin);
        }
    }
}
