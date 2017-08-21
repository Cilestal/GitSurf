package ua.dp.michaellang.gitsurf.utils;

import org.eclipse.egit.github.core.service.*;
import ua.dp.michaellang.gitsurf.GitSurfApplication;
import ua.dp.michaellang.gitsurf.services.GitSurfRepositoryService;
import ua.dp.michaellang.gitsurf.services.GitSurfStargazerService;
import ua.dp.michaellang.gitsurf.services.GitSurfUserService;

import static ua.dp.michaellang.gitsurf.GitSurfApplication.GitHubServices.*;

/**
 * Date: 19.03.17
 *
 * @author Michael Lang
 */
public final class ServiceUtil {
    private ServiceUtil() {

    }

    public static CommitService getCommitService() {
        return (CommitService) GitSurfApplication.getInstance()
                .getService(COMMIT_SERVICE);
    }

    public static ContentsService getContentsService() {
        return (ContentsService) GitSurfApplication.getInstance()
                .getService(CONTENTS_SERVICE);
    }

    public static DownloadService getDownloadService() {
        return (DownloadService) GitSurfApplication.getInstance()
                .getService(DOWNLOAD_SERVICE);
    }

    public static EventService getEventService() {
        return (EventService) GitSurfApplication.getInstance()
                .getService(EVENT_SERVICE);
    }

    public static GistService getGistService() {
        return (GistService) GitSurfApplication.getInstance()
                .getService(GIST_SERVICE);
    }

    public static IssueService getIssueService() {
        return (IssueService) GitSurfApplication.getInstance()
                .getService(ISSUE_SERVICE);
    }

    public static LabelService getLabelService() {
        return (LabelService) GitSurfApplication.getInstance()
                .getService(LABEL_SERVICE);
    }

    public static MarkdownService getMarkdownService() {
        return (MarkdownService) GitSurfApplication.getInstance()
                .getService(MARKDOWN_SERVICE);
    }

    public static MilestoneService getMilestoneService() {
        return (MilestoneService) GitSurfApplication.getInstance()
                .getService(MILESTONE_SERVICE);
    }

    public static OrganizationService getOrganizationService() {
        return (OrganizationService) GitSurfApplication.getInstance()
                .getService(ORGANIZATION_SERVICE);
    }

    public static PullRequestService getPullRequestService() {
        return (PullRequestService) GitSurfApplication.getInstance()
                .getService(PULLREQUEST_SERVICE);
    }

    public static GitSurfRepositoryService getRepositoryService() {
        return (GitSurfRepositoryService) GitSurfApplication.getInstance()
                .getService(REPOSITORY_SERVICE);
    }

    public static GitSurfStargazerService getStargazerService() {
        return (GitSurfStargazerService) GitSurfApplication.getInstance()
                .getService(STARGAZER_SERVICE);
    }

    public static GitSurfUserService getUserService() {
        return (GitSurfUserService) GitSurfApplication.getInstance()
                .getService(USER_SERVICE);
    }
}
