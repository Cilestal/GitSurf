package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.OrganizationService;
import ua.dp.michaellang.gitsurf.Constants;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.util.List;

/**
 * Date: 19.03.17
 *
 * @author Michael Lang
 */
public class OrganizationsLoader extends BaseLoader<LoadedPage<User>> {
    private String mLogin;

    public OrganizationsLoader(Context context, String login) {
        super(context);
        this.mLogin = login;
    }

    @Override
    protected LoadedPage<User> doLoadInBackground() throws Exception {
        OrganizationService orgService = ServiceUtil.getOrganizationService();

        if (mLogin == null) {
            mLogin = SPUtil.getValue(getContext(), Constants.Prefs.User.LOGIN);
        }

        List<User> organizations = orgService.getOrganizations(mLogin);
        return new LoadedPage<>(organizations, false);
    }
}
