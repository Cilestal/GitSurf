package ua.dp.michaellang.gitsurf.view.repos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.eclipse.egit.github.core.Repository;
import ua.dp.michaellang.gitsurf.GitSurfApplication;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.presenter.repos.RepositoryImpl;
import ua.dp.michaellang.gitsurf.presenter.repos.RepositoryPresenter;
import ua.dp.michaellang.gitsurf.presenter.repos.RepositoryView;
import ua.dp.michaellang.gitsurf.utils.DownloadUtil;
import ua.dp.michaellang.gitsurf.utils.ImageUtil;
import ua.dp.michaellang.gitsurf.utils.SPUtil;
import ua.dp.michaellang.gitsurf.utils.StringUtil;
import ua.dp.michaellang.gitsurf.view.ToolbarActivity;
import ua.dp.michaellang.gitsurf.view.issues.IssueListActivity;
import ua.dp.michaellang.gitsurf.view.users.UserActivity;
import ua.dp.michaellang.gitsurf.view.users.UserListActivity;

import static ua.dp.michaellang.gitsurf.Constants.UserListGroup.*;

/**
 * Date: 25.05.17
 *
 * @author Michael Lang
 */
public class RepositoryActivity extends ToolbarActivity
        implements RepositoryView, BranchesDialog.BranchListDialogListener {

    private static final String EXTRA_LOGIN
            = "ua.dp.michaellang.gitsurf.view.repos.login";
    private static final String EXTRA_REPOSITORY
            = "ua.dp.michaellang.gitsurf.view.repos.repository";

    public static final String DIALOG_BRANCH_LIST = "dialog_branch_list";

    @BindView(R.id.content_repository_avatar_IV) ImageView mAvatarIV;
    @BindView(R.id.content_repository_title_TV) TextView mTitleTV;
    @BindView(R.id.content_repository_description_TV) TextView mDescriptionTV;
    @BindView(R.id.content_repository_update_at_TV) TextView mUpdatedAtTV;
    @BindView(R.id.content_repository_language_TV) TextView mLanguageTV;
    @BindView(R.id.content_repository_forks_TV) TextView mForksTV;
    @BindView(R.id.content_repository_issues_TV) TextView mIssuesTV;
    @BindView(R.id.content_repository_pulls_TV) TextView mPullsTV;
    @BindView(R.id.content_repository_stargazers_TV) TextView mWatchersTV;
    @BindView(R.id.content_repository_branches_layout) View mBranchesLayout;
    @BindView(R.id.content_repository_branches_TV) TextView mBranchesTV;
    @BindView(R.id.content_repository_collaborators_layout) View mCollaboratorsLayout;
    @BindView(R.id.content_repository_contributors_layout) View mContributorsLayout;
    @BindView(R.id.content_repository_readme_layout) View mReadmeLayout;
    @BindView(R.id.content_repository_readme_TV) TextView mReadmeTV;

    private RepositoryPresenter mPresenter;

    private String mOwner;
    private String mRepoName;
    private String mRef;

    private boolean mIsAuthorized;
    private Boolean mIsStarring;
    private Boolean mIsWatching;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.content_repository);
        ButterKnife.bind(this);

        mIsAuthorized = GitSurfApplication.isAuthorized();

        loadExtra();
        setToolbarTitle(getString(R.string.repo_title, mOwner, mRepoName));

        mPresenter = new RepositoryImpl(this, this, mOwner, mRepoName);
        mPresenter.loadRepository();
    }

    private void loadExtra() {
        Intent intent = getIntent();
        mOwner = intent.getStringExtra(EXTRA_LOGIN);
        mRepoName = intent.getStringExtra(EXTRA_REPOSITORY);
    }

    public static Intent newIntent(Context context, String owner, String name) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_LOGIN, owner);
        intent.putExtra(EXTRA_REPOSITORY, name);
        return intent;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mRef = null;
        mPresenter.reloadRepository();
    }

    @Override
    public void onRepositoryLoaded(Repository repository) {
        showProgress(false);

        String updatedTime = StringUtil.dateFormat(this, repository.getUpdatedAt());
        String updatedAt = getString(R.string.repo_updated_at, updatedTime);
        String language = getString(R.string.repo_language, repository.getLanguage());

        if (mRef == null) {
            mRef = repository.getDefaultBranch();
        }

        String branch = getString(R.string.repo_branch, mRef);

        mTitleTV.setText(getString(R.string.repo_title, mOwner, mRepoName));
        ImageUtil.loadUserCircleAvatar(this, mAvatarIV, repository.getOwner().getAvatarUrl());

        showLayout(mDescriptionTV, repository.getDescription(), mDescriptionTV);
        showLayout(mUpdatedAtTV, updatedAt, mDescriptionTV);
        showLayout(mLanguageTV, language, mLanguageTV);
        showLayout(mBranchesTV, branch, mBranchesLayout);
        mForksTV.setText(String.valueOf(repository.getForks()));
        mWatchersTV.setText(String.valueOf(repository.getWatchers()));
    }

    @Override
    public void onIssuesCountLoaded(int issues, int pulls) {
        mPullsTV.setText(String.valueOf(pulls));
        mIssuesTV.setText(String.valueOf(issues));
    }

    @Override
    public void onReadmeLoaded(String text) {
        if (text == null) {
            return;
        }

        Spanned spanned;
        text = text.replace("\n", "<br/>");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            spanned = Html.fromHtml(text);
        }
        mReadmeTV.setText(spanned);
        mReadmeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCollaboratorChecked(boolean isCollaborator) {
        showLayout(mCollaboratorsLayout, isCollaborator);
    }

    @Override
    public void onWatchingChecked(Boolean result) {
        mIsWatching = result;
        invalidateOptionsMenu();
    }

    @Override
    public void onStarringChecked(Boolean isStarred) {
        mIsStarring = isStarred;
        invalidateOptionsMenu();
    }

    @Override
    public void onForkSuccess(final Repository repository) {
        Snackbar.make(mContentRoot, R.string.fork_success, Snackbar.LENGTH_LONG)
                .setAction(R.string.fork_open, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String login = repository.getOwner().getLogin();
                        Intent intent = RepositoryActivity.newIntent(RepositoryActivity.this,
                                login, repository.getName());
                        startActivity(intent);
                    }
                })
                .show();
    }

    @Override
    public void onStarSuccess() {
        mIsStarring = !mIsStarring;

        int strId = mIsStarring ? R.string.star_success : R.string.unstar_success;
        Snackbar.make(mContentRoot, strId, Snackbar.LENGTH_LONG).show();
        invalidateOptionsMenu();
    }

    @Override
    public void onWatchSuccess() {
        mIsWatching = !mIsWatching;

        int strId = mIsWatching ? R.string.watch_success : R.string.stop_watch_success;
        Snackbar.make(mContentRoot, strId, Snackbar.LENGTH_LONG).show();
        invalidateOptionsMenu();
    }

    @Override
    public void onForkFail() {
        Snackbar.make(mContentRoot, R.string.fork_fail, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onStarFail() {
        int strId = !mIsStarring ? R.string.star_fail : R.string.unstar_fail;
        Snackbar.make(mContentRoot, strId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onWatchFail() {
        int strId = !mIsWatching ? R.string.watch_fail : R.string.stop_watch_fail;
        Snackbar.make(mContentRoot, strId, Snackbar.LENGTH_LONG).show();
    }

    private void showWatchDialog() {
        SubscriptionDialogListener listener = new SubscriptionDialogListener();
        new AlertDialog.Builder(this)
                .setMessage(R.string.dialog_subscribe)
                .setPositiveButton(R.string.dialog_yes, listener)
                .setNegativeButton(R.string.dialog_ignore, listener)
                .setCancelable(true)
                .show();
    }

    private void showForkDialog() {
        String message = getString(R.string.dialog_fork, mRepoName);

        ForkDialogListener listener = new ForkDialogListener();
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_yes, listener)
                .setNegativeButton(R.string.dialog_no, listener)
                .show();
    }

    private void star() {
        if (mIsStarring) {
            mPresenter.unstar();
        } else {
            mPresenter.star();
        }
    }

    private void watch() {
        if (mIsWatching) {
            mPresenter.unwatch();
        } else {
            showWatchDialog();
        }
    }

    @Override
    public void onBranchDialogItemSelected(String branch) {
        if (branch == null) return;

        mRef = branch;
        mBranchesTV.setText(getString(R.string.repo_branch, branch));
        mReadmeLayout.setVisibility(View.GONE);
        mPresenter.reloadReadme(mRef);
    }

    private void showLayout(TextView v, String text, View parent) {
        if (text == null || text.length() == 0) {
            showLayout(parent, false);
        } else {
            showLayout(parent, true);
            v.setText(text);
        }
    }

    private void showLayout(View v, boolean flag) {
        v.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.content_repository_issues_layout)
    void onIssueClick() {
        Intent intent = IssueListActivity.newIntent(this, mOwner, mRepoName, false);
        startActivity(intent);
    }

    @OnClick(R.id.content_repository_pulls_layout)
    void onPullsClick() {
        Intent intent = IssueListActivity.newIntent(this, mOwner, mRepoName, true);
        startActivity(intent);
    }

    @OnClick(R.id.content_repository_stargazers_layout)
    void onStargazersClick() {
        Intent intent = UserListActivity.newIntent(this, mOwner, mRepoName, STARGAZERS);
        startActivity(intent);
    }

    @OnClick(R.id.content_repository_watchers_layout)
    void onWatchersClick() {
        Intent intent = UserListActivity.newIntent(this, mOwner, mRepoName, WATCHERS);
        startActivity(intent);
    }

    @OnClick(R.id.content_repository_contributors_layout)
    void onContributorsClick() {
        Intent intent = UserListActivity.newIntent(this, mOwner, mRepoName, CONTRIBUTORS);
        startActivity(intent);
    }

    @OnClick(R.id.content_repository_collaborators_layout)
    void onCollaboratorsClick() {
        Intent intent = UserListActivity.newIntent(this, mOwner, mRepoName, COLLABORATORS);
        startActivity(intent);
    }

    @OnClick(R.id.content_repository_branches_layout)
    void onBranchListClick() {
        BranchesDialog bf = BranchesDialog.newInstance(mOwner, mRepoName, mRef);
        bf.show(getSupportFragmentManager(), DIALOG_BRANCH_LIST);
    }

    @OnClick(R.id.content_repository_forks_layout)
    void onForksClick() {
        Intent intent = ForkListActivity.newIntent(this, mOwner, mRepoName);
        startActivity(intent);
    }

    @OnClick(R.id.content_repository_commits_layout)
    void onCommitListClick() {
        Intent intent = CommitListActivity.newIntent(this, mOwner, mRepoName, mRef, "");
        startActivity(intent);
    }

    @OnClick(R.id.content_repository_avatar_IV)
    void onUserAvatarClick() {
        Intent intent = UserActivity.newIntent(this, mOwner);
        startActivity(intent);
    }

    @OnClick(R.id.content_repository_code_layout)
    void onCodeClick() {
        Intent intent = RepositoryFilesActivity.newIntent(this, mOwner, mRepoName, mRef);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repository, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mIsAuthorized) {
            MenuItem starredItem = menu.findItem(R.id.menu_repository_star);
            MenuItem watchedItem = menu.findItem(R.id.menu_repository_watch);
            MenuItem forkItem = menu.findItem(R.id.menu_repository_fork);

            if (mIsStarring != null) {
                starredItem.setVisible(true);

                if (mIsStarring) {
                    starredItem.setTitle(R.string.menu_unstar);
                    starredItem.setIcon(R.drawable.ic_menu_unstar);
                } else {
                    starredItem.setTitle(R.string.menu_star);
                    starredItem.setIcon(R.drawable.ic_menu_star);
                }
            } else {
                starredItem.setVisible(false);
            }

            if (mIsWatching != null) {
                watchedItem.setVisible(true);

                if (mIsWatching) {
                    watchedItem.setTitle(R.string.menu_unwatch);
                    watchedItem.setIcon(R.drawable.ic_menu_unwatch);
                } else {
                    watchedItem.setTitle(R.string.menu_watch);
                    watchedItem.setIcon(R.drawable.ic_menu_watch);
                }
            } else {
                watchedItem.setVisible(false);
            }

            String authLogin = SPUtil.getAuthLogin(this);
            if (!authLogin.equals(mOwner)) {
                forkItem.setVisible(true);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_repository_star:
                star();
                break;
            case R.id.menu_repository_watch:
                watch();
                break;
            case R.id.menu_repository_fork:
                showForkDialog();
                break;
            case R.id.menu_repository_download:
                download();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void download() {
        DownloadUtil.downloadRepository(this, mOwner,
                mRepoName, DownloadUtil.ZIP_FORMAT, mRef);
    }

    @Override
    public LoaderManager getLoader() {
        return getSupportLoaderManager();
    }

    private final class SubscriptionDialogListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            boolean receiveNotifications = which == DialogInterface.BUTTON_POSITIVE;
            mPresenter.watch(receiveNotifications);
        }
    }

    private final class ForkDialogListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                mPresenter.forkRepository();
            } else {
                dialog.dismiss();
            }
        }
    }
}
