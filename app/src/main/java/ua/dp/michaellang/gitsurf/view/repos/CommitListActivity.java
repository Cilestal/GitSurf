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
public class CommitListActivity extends ToolbarActivity {
    private static final String EXTRA_OWNER = "ua.dp.michaellang.gitsurf.view.repos.owner";
    private static final String EXTRA_REPO = "ua.dp.michaellang.gitsurf.view.repos.repo";
    private static final String EXTRA_REF = "ua.dp.michaellang.gitsurf.view.repos.ref";
    private static final String EXTRA_PATH = "ua.dp.michaellang.gitsurf.view.repos.path";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        String owner = intent.getStringExtra(EXTRA_OWNER);
        String repoName = intent.getStringExtra(EXTRA_REPO);
        String ref = intent.getStringExtra(EXTRA_REF);
        String path = intent.getStringExtra(EXTRA_PATH);

        setToolbarTitle(getString(R.string.commits));

        if(getContentFragment() == null) {
            CommitListFragment fragment = CommitListFragment.newInstance(owner, repoName, ref, path);
            setContent(fragment);
        }
    }

    public static Intent newIntent(Context context, String owner, String repoName,
            String ref, String path) {
        Intent intent = new Intent(context, CommitListActivity.class);
        intent.putExtra(EXTRA_OWNER, owner);
        intent.putExtra(EXTRA_REPO, repoName);
        intent.putExtra(EXTRA_REF, ref);
        intent.putExtra(EXTRA_PATH, path);

        return intent;
    }
}
