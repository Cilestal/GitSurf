package ua.dp.michaellang.gitsurf.view.repos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import org.eclipse.egit.github.core.RepositoryContents;
import ua.dp.michaellang.breadcrumbs.BreadcrumbsView;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.StringUtil;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;

/**
 * Date: 10.07.17
 *
 * @author Michael Lang
 */
public class RepositoryFilesActivity
        extends ToolbarActivity
        implements RepositoryDirFragment.Callbacks {
    public static final String TAG = RepositoryFilesActivity.class.toString();

    private static final String EXTRA_OWNER = "ua.dp.michaellang.gitsurf.view.repos.owner";
    private static final String EXTRA_REPO = "ua.dp.michaellang.gitsurf.view.repos.repo";
    private static final String EXTRA_REF = "ua.dp.michaellang.gitsurf.view.repos.ref";

    @BindView(R.id.content_repository_files_breadcrumbs) BreadcrumbsView mBreadcrumbsView;

    private String mOwner;
    private String mRepoName;
    private String mRef;
    private String mPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.content_repository_files);
        ButterKnife.bind(this);

        loadExtra();

        String toolbarTitle = getString(R.string.repo_title, mOwner, mRepoName);
        setToolbarTitle(toolbarTitle);
        mToolbar.setSubtitle(mRef);
        initBreadCrumbs();
        initDirectoryFragment(false);
    }

    private void initBreadCrumbs() {
        mBreadcrumbsView.setRootTitle(mRepoName);

        mBreadcrumbsView.setCrumbSelectedListener(new BreadcrumbsView.OnCrumbSelectedListener() {
            @Override
            public void onHomeSelected() {
                if (mPath == null) {
                    return;
                }
                mPath = null;
                initDirectoryFragment(true);
            }

            @Override
            public void onCrumbSelected(String crumb, String absolutePath, int position) {
                if (position == mBreadcrumbsView.getCrumbsCount() - 1) {
                    return;
                }

                int index = absolutePath.indexOf("/"); //remove root dir
                mPath = absolutePath.substring(index + 1);
                initDirectoryFragment(true);
            }
        });
    }

    private void loadExtra() {
        Intent intent = getIntent();

        mOwner = intent.getStringExtra(EXTRA_OWNER);
        mRepoName = intent.getStringExtra(EXTRA_REPO);
        mRef = intent.getStringExtra(EXTRA_REF);
    }

    private void initDirectoryFragment(boolean update) {
        FragmentManager sfm = getSupportFragmentManager();
        Fragment fragment = sfm.findFragmentById(R.id.content_repository_files_dir);

        if (fragment == null) {
            fragment = RepositoryDirFragment.newInstance(mOwner, mRepoName, mPath, mRef);

            sfm.beginTransaction()
                    .add(R.id.content_repository_files_dir, fragment)
                    .commit();
        } else if (update) {
            ((RepositoryDirFragment) fragment).updatePath(mPath);
        }
    }

    @Override
    public void onDirectorySelected(String path) {
        mPath = path;
        mBreadcrumbsView.setFullPath(mPath, BreadcrumbsView.DEFAULT_SEPARATOR);
        initDirectoryFragment(true);
    }

    @Override
    public void onFileSelected(RepositoryContents file) {
        String content = null;

        if (file.getContent() != null) { //если файл загружен
            content = StringUtil.fromBase64(file.getContent());
        }

        Intent intent = FileActivity.newIntent(this, mOwner,
                mRepoName, mRef, content, file.getPath());

        startActivity(intent);
    }

    @Override
    protected boolean isSwipeRefreshEnabled() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repo_files, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initDirectoryFragment(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_repo_files_refresh:
                onRefresh();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context, String owner, String repoName,
            String ref) {
        Intent intent = new Intent(context, RepositoryFilesActivity.class);
        intent.putExtra(EXTRA_OWNER, owner);
        intent.putExtra(EXTRA_REPO, repoName);
        intent.putExtra(EXTRA_REF, ref);

        return intent;
    }

}
