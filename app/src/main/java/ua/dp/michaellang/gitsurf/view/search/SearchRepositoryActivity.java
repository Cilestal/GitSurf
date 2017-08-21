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
public class SearchRepositoryActivity extends ToolbarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();
        mNavigationView.setCheckedItem(R.id.navigation_public_repos);

        if (getContentFragment() == null) {
            SearchRepositoryFragment fragment = SearchRepositoryFragment.newInstance();
            setContent(fragment);
        }
    }

    @Override
    protected boolean isSwipeRefreshEnabled() {
        return false;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchRepositoryActivity.class);
    }
}
