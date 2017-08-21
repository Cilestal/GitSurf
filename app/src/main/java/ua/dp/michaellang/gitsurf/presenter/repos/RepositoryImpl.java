package ua.dp.michaellang.gitsurf.presenter.repos;

import android.content.Context;
import android.support.v4.content.Loader;
import android.support.v4.os.AsyncTaskCompat;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import ua.dp.michaellang.gitsurf.loader.ForkTask;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.StarTask;
import ua.dp.michaellang.gitsurf.loader.WatchTask;
import ua.dp.michaellang.gitsurf.loader.callbacks.*;
import ua.dp.michaellang.gitsurf.presenter.BasePresenterImpl;
import ua.dp.michaellang.gitsurf.view.repos.RepositoryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 20.05.17
 *
 * @author Michael Lang
 */
public class RepositoryImpl extends BasePresenterImpl
        implements RepositoryPresenter {
    private static final int LOADER_REPOSITORY = 0;
    private static final int LOADER_PULLS = 1;
    private static final int LOADER_README = 2;
    private static final int LOADER_IS_COLLABORATOR = 3;
    private static final int LOADER_IS_STARRING = 4;
    private static final int LOADER_IS_WATCHING = 5;

    private final RepositoryView mView;
    private final String mOwner;
    private final String mRepoName;

    private Loader<?> mRepoLoader;
    private Loader<?> mPullsLoader;
    private Loader<?> mReadmeLoader;
    private Loader<?> mCollaboratorLoader;
    private Loader<?> mIsStarredLoader;
    private Loader<?> mIsWatchingLoader;

    private final RepositoryCallbacks mRepoCallbacks;
    private final IssueListCallbacks mPullsCallbacks;
    private final ReadmeCallbacks mReadmeCallbacks;
    private final IsCollaboratorCallbacks mIsCollaboratorCallbacks;
    private final IsStarringCallbacks mIsStarringCallbacks;
    private final IsWatchingCallbacks mIsWatchingCallbacks;

    public RepositoryImpl(Context context, RepositoryActivity view, String owner, String repoName) {
        super(view);
        mView = view;
        mOwner = owner;
        mRepoName = repoName;

        mRepoCallbacks = new RepositoryCallbacks(this, context, owner, repoName) {
            @Override
            protected void onResultReady(int id, Repository result) {
                mView.onRepositoryLoaded(result);
            }
        };

        mPullsCallbacks = new IssueListCallbacks(this, context, owner, repoName) {
            @Override
            protected void onResultReady(int id, LoadedPage<Issue> result) {
                List<Issue> results = result.getResults();
                int pulls = getPulls(results).size();
                int issues = results.size() - pulls;

                mView.onIssuesCountLoaded(issues, pulls);
            }
        };

        mReadmeCallbacks = new ReadmeCallbacks(this, context, owner, repoName) {
            @Override
            protected void onResultReady(int id, String result) {
                mView.onReadmeLoaded(result);
            }
        };

        mIsCollaboratorCallbacks = new IsCollaboratorCallbacks(this, context, owner, repoName) {
            @Override
            protected void onResultReady(int id, Boolean result) {
                mView.onCollaboratorChecked(result);
            }
        };

        mIsStarringCallbacks = new IsStarringCallbacks(this, context, owner, repoName) {
            @Override
            protected void onResultReady(int id, Boolean result) {
                mView.onStarringChecked(result);
            }
        };

        mIsWatchingCallbacks = new IsWatchingCallbacks(this, context, owner, repoName) {
            @Override
            protected void onResultReady(int id, Boolean result) {
                mView.onWatchingChecked(result);
            }
        };
    }

    private List<Issue> getPulls(List<Issue> list) {
        List<Issue> pulls = new ArrayList<>();

        for (Issue issue : list) {
            if (issue.getPullRequest() != null) {
                pulls.add(issue);
            }
        }

        return pulls;
    }

    @Override
    public void loadRepository() {
        mView.showProgress(true);
        mRepoLoader = mView.getLoader()
                .initLoader(LOADER_REPOSITORY, null, mRepoCallbacks);
        mPullsLoader = mView.getLoader()
                .initLoader(LOADER_PULLS, null, mPullsCallbacks);
        mReadmeLoader = mView.getLoader()
                .initLoader(LOADER_README, null, mReadmeCallbacks);
        mCollaboratorLoader = mView.getLoader()
                .initLoader(LOADER_IS_COLLABORATOR, null, mIsCollaboratorCallbacks);
        mIsStarredLoader = mView.getLoader()
                .initLoader(LOADER_IS_STARRING, null, mIsStarringCallbacks);
        mIsWatchingLoader = mView.getLoader()
                .initLoader(LOADER_IS_WATCHING, null, mIsWatchingCallbacks);
    }

    @Override
    public void reloadReadme(String ref) {
        mReadmeCallbacks.setRef(ref);
        mReadmeLoader = mView.getLoader()
                .restartLoader(LOADER_README, null, mReadmeCallbacks);
    }

    @Override
    public void reloadRepository() {
        mView.showProgress(true);
        if (mRepoLoader != null) {
            this.mRepoLoader.onContentChanged();
        }

        if (mReadmeLoader != null) {
            this.mReadmeLoader.onContentChanged();
        }

        if (mPullsLoader != null) {
            this.mPullsLoader.onContentChanged();
        }

        if (mCollaboratorLoader != null) {
            this.mCollaboratorLoader.onContentChanged();
        }

        if (mIsStarredLoader != null) {
            this.mIsStarredLoader.onContentChanged();
        }

        if (mIsWatchingLoader != null) {
            this.mIsWatchingLoader.onContentChanged();
        }
    }

    @Override
    public void forkRepository() {
        AsyncTaskCompat.executeParallel(new ForkTask(this, mOwner, mRepoName) {
            @Override
            protected void onResultReady(Repository repository) {
                if (repository == null) {
                    mView.onForkFail();
                } else {
                    mView.onForkSuccess(repository);
                }
            }
        });
    }

    @Override
    public void star() {
        AsyncTaskCompat.executeParallel(createStarTask(false));
    }

    @Override
    public void unstar() {
        AsyncTaskCompat.executeParallel(createStarTask(true));
    }

    @Override
    public void watch(boolean isNotifying) {
        AsyncTaskCompat.executeParallel(createWatchTask(false, isNotifying));
    }

    @Override
    public void unwatch() {
        AsyncTaskCompat.executeParallel(createWatchTask(true, false));
    }

    private StarTask createStarTask(boolean isStarred) {
        return new StarTask(this, mOwner, mRepoName, isStarred) {
            @Override
            protected void onResultReady(Boolean success) {
                if (success) {
                    mView.onStarSuccess();
                } else {
                    mView.onStarFail();
                }
            }
        };
    }

    private WatchTask createWatchTask(boolean isWatching, boolean isNotifying) {
        return new WatchTask(this, mOwner, mRepoName, isWatching, isNotifying) {
            @Override
            protected void onResultReady(Boolean success) {
                if (success) {
                    mView.onWatchSuccess();
                } else {
                    mView.onWatchFail();
                }
            }
        };
    }
}
