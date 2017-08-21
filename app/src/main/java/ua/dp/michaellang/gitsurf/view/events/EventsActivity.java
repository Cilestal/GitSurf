package ua.dp.michaellang.gitsurf.view.events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

/**
 * Date: 01.06.17
 *
 * @author Michael Lang
 */
public class EventsActivity extends ToolbarActivity {

    private static final String EXTRA_LOGIN = "ua.dp.michaellang.gitsurf.view.events.extra_login";
    private static final String EXTRA_IS_ORG = "ua.dp.michaellang.gitsurf.view.events.extra_is_org";
    private static final String EXTRA_IS_RECEIVED = "ua.dp.michaellang.gitsurf.view.events.extra_is_recieved";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavigationView.setCheckedItem(R.id.navigation_news);

        String login = getIntent().getStringExtra(EXTRA_LOGIN);
        boolean isOrg = getIntent().getBooleanExtra(EXTRA_IS_ORG, false);
        boolean isReceived = getIntent().getBooleanExtra(EXTRA_IS_RECEIVED, false);

        setToolbar(login, isReceived);

        if(getContentFragment() == null) {
            EventsFragment eventsFragment = EventsFragment.newInstance(login, isOrg, isReceived);
            setContent(eventsFragment);
        }
    }

    private void setToolbar(String login, boolean isReceived) {
        if(isReceived){
            setToolbarTitle(R.string.menu_news);
            showHomeButton();
        } else {
            String title = getString(R.string.user_public_activity_title, login);
            setToolbarTitle(title);
        }
    }

    public static Intent newIntent(Context context, String login, boolean isOrg, boolean isReceived) {
        Intent intent = new Intent(context, EventsActivity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        intent.putExtra(EXTRA_IS_ORG, isOrg);
        intent.putExtra(EXTRA_IS_RECEIVED, isReceived);

        return intent;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EventsActivity.class);

        String login = SPUtil.getAuthLogin(context);

        intent.putExtra(EXTRA_LOGIN, login);
        intent.putExtra(EXTRA_IS_ORG, false);
        intent.putExtra(EXTRA_IS_RECEIVED, true);

        return intent;
    }
}
