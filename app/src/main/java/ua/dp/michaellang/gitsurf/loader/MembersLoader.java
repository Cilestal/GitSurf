package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.OrganizationService;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.util.List;

/**
 * Date: 19.03.17
 *
 * @author Michael Lang
 */
public class MembersLoader extends BaseLoader<LoadedPage<User>> {
    private final String mLogin;

    public MembersLoader(Context context, @NonNull String login) {
        super(context);
        this.mLogin = login;
    }

    @Override
    protected LoadedPage<User> doLoadInBackground() throws Exception {
        OrganizationService orgService = ServiceUtil.getOrganizationService();

        List<User> members = orgService.getMembers(mLogin);
        return new LoadedPage<>(members, false);
    }
}
