package ua.dp.michaellang.gitsurf.view.repos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

/**
 * Date: 25.05.17
 *
 * @author Michael Lang
 */
public class ForkListActivity extends ToolbarActivity {
    private static final String EXTRA_OWNER = "ua.dp.michaellang.gitsurf.view.repos.owner";
    private static final String EXTRA_REPO = "ua.dp.michaellang.gitsurf.view.repos.repo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String owner = getIntent().getStringExtra(EXTRA_OWNER);
        String repoName = getIntent().getStringExtra(EXTRA_REPO);

        setToolbarTitle(getString(R.string.forks));

        if(getContentFragment() == null) {
            ForksFragment fragment = ForksFragment.newInstance(owner, repoName);
            setContent(fragment);
        }
    }

    public static Intent newIntent(Context context, String owner, String repoName) {
        Intent intent = new Intent(context, ForkListActivity.class);
        intent.putExtra(EXTRA_OWNER, owner);
        intent.putExtra(EXTRA_REPO, repoName);

        return intent;
    }
}
