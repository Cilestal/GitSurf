package ua.dp.michaellang.gitsurf.view.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

/**
 * Date: 08.05.17
 *
 * @author Michael Lang
 */
public class SearchUserActivity extends ToolbarActivity {

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);

        mNavigationView.setCheckedItem(R.id.navigation_search_users);
        showHomeButton();
        if (getContentFragment() == null) {
            SearchUserFragment fragment = SearchUserFragment.newInstance();
            setContent(fragment);
        }
    }

    @Override
    protected boolean isSwipeRefreshEnabled() {
        return false;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchUserActivity.class);
    }
}
