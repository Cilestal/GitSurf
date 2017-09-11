package ua.dp.michaellang.gitsurf;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.*;
import ua.dp.michaellang.gitsurf.services.GitSurfRepositoryService;
import ua.dp.michaellang.gitsurf.services.GitSurfStargazerService;
import ua.dp.michaellang.gitsurf.services.GitSurfUserService;
import ua.dp.michaellang.gitsurf.utils.SPUtil;

import java.util.EnumMap;
import java.util.Map;

/**
 * Base class for maintaining global application state.
 * Date: 21.02.17
 *
 * @author Michael Lang
 */
public final class GitSurfApplication extends Application
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public enum GitHubServices {
        COMMIT_SERVICE,
        CONTENTS_SERVICE,
        DOWNLOAD_SERVICE,
        EVENT_SERVICE,
        GIST_SERVICE,
        ISSUE_SERVICE,
        LABEL_SERVICE,
        MARKDOWN_SERVICE,
        MILESTONE_SERVICE,
        ORGANIZATION_SERVICE,
        PULLREQUEST_SERVICE,
        REPOSITORY_SERVICE,
        STARGAZER_SERVICE,
        USER_SERVICE
    }

    private GitHubClient mClient;
    private Map<GitHubServices, GitHubService> mServices;

    private static GitSurfApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        this.mClient = new GitHubClient();
        this.mClient.setOAuth2Token(SPUtil.getAuthToken(this));

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SPUtil.getPrefs(this).registerOnSharedPreferenceChangeListener(this);

        this.mServices = new EnumMap<>(GitHubServices.class);
        this.mServices.put(GitHubServices.COMMIT_SERVICE, new CommitService(mClient));
        this.mServices.put(GitHubServices.CONTENTS_SERVICE, new ContentsService(mClient));
        this.mServices.put(GitHubServices.DOWNLOAD_SERVICE, new DownloadService(mClient));
        this.mServices.put(GitHubServices.EVENT_SERVICE, new EventService(mClient));
        this.mServices.put(GitHubServices.GIST_SERVICE, new GistService(mClient));
        this.mServices.put(GitHubServices.ISSUE_SERVICE, new IssueService(mClient));
        this.mServices.put(GitHubServices.LABEL_SERVICE, new LabelService(mClient));
        this.mServices.put(GitHubServices.MARKDOWN_SERVICE, new MarkdownService(mClient));
        this.mServices.put(GitHubServices.MILESTONE_SERVICE, new MilestoneService(mClient));
        this.mServices.put(GitHubServices.ORGANIZATION_SERVICE, new OrganizationService(mClient));
        this.mServices.put(GitHubServices.PULLREQUEST_SERVICE, new PullRequestService(mClient));
        this.mServices.put(GitHubServices.REPOSITORY_SERVICE, new GitSurfRepositoryService(mClient));
        this.mServices.put(GitHubServices.STARGAZER_SERVICE, new GitSurfStargazerService(mClient));
        this.mServices.put(GitHubServices.USER_SERVICE, new GitSurfUserService(mClient));
    }

    public static GitSurfApplication getInstance() {
        return sInstance;
    }

    public GitHubService getService(GitHubServices service) {
        return mServices.get(service);
    }

    public static void logout(Context context) {
        SPUtil.getPrefs(context)
                .edit()
                .remove(Constants.Prefs.User.LOGIN)
                .remove(Constants.Prefs.User.TOKEN)
                .apply();

        FirebaseAuth.getInstance()
                .signOut();
    }

    public static boolean isAuthorized() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case Constants.Prefs.User.TOKEN:
                String token = sharedPreferences.getString(key, null);
                mClient.setOAuth2Token(token);
                break;
        }
    }
}
