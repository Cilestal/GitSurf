package ua.dp.michaellang.gitsurf.view.viewholder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.event.*;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.ImageUtil;
import ua.dp.michaellang.gitsurf.utils.StringUtil;
import ua.dp.michaellang.gitsurf.view.repos.RepositoryActivity;

import java.util.List;

/**
 * Date: 06.06.17
 *
 * @author Michael Lang
 */
public class EventHolder extends BaseHolder<Event>{

    private Resources mResources;
    private Context mContext;
    private Event mEvent;

    @BindView(R.id.item_events_avatar_IV) AppCompatImageView mUserAvatarIV;
    @BindView(R.id.item_events_description_TV) AppCompatTextView mDescriptionTV;
    @BindView(R.id.item_events_login_TV) AppCompatTextView mLoginTV;
    @BindView(R.id.item_events_time_TV) AppCompatTextView mTimeTV;

    private static final String TAG = "tag";
    private static final String BRANCH = "branch";
    private static final String REPOSITORY = "repository";
    private static final String REF_HEADS = "refs/heads/";

    private static final String CREATE = "create";
    private static final String UPDATE = "update";

    private static final String OPENED = "opened";
    private static final String CLOSED = "closed";
    private static final String REOPENED = "reopened";
    private static final String SYNCHRONIZED = "synchronized";

    public EventHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Context context, Event data) {
        mEvent = data;
        mContext = context;
        mResources = context.getResources();

        User actor = data.getActor();
        String avatarUrl = actor.getAvatarUrl();
        ImageUtil.loadUserCircleAvatar(context, mUserAvatarIV, avatarUrl);

        mLoginTV.setText(actor.getLogin());

        String date = StringUtil.dateFormat(context, data.getCreatedAt());
        mTimeTV.setText(date);

        Spanned message = Html.fromHtml(getMessage(mEvent));
        mDescriptionTV.setText(message);
    }

    private String getMessage(Event event) {
        String type = event.getType();
        String repo = getRepoName(event.getRepo());

        switch (type) {
            case Event.TYPE_COMMIT_COMMENT:
                return getCommitCommentMessage(event, repo);
            case Event.TYPE_CREATE:
                return getCreateMessage(event, repo);
            case Event.TYPE_DELETE:
                return getDeleteMessage(event, repo);
            case Event.TYPE_DOWNLOAD:
                return mResources.getString(R.string.event_download_title, repo);
            case Event.TYPE_FOLLOW:
                return getFollowMessage(event);
            case Event.TYPE_FORK:
                return mResources.getString(R.string.event_fork_title, repo);
            case Event.TYPE_FORK_APPLY:
                return mResources.getString(R.string.event_fork_apply_title, repo);
            case Event.TYPE_GIST:
                return getGistMessage(event);
            case Event.TYPE_GOLLUM:
                return getGollumMessage(event, repo);
            case Event.TYPE_ISSUE_COMMENT:
                return getIssueCommentMessage(event, repo);
            case Event.TYPE_ISSUES:
                return getIssueMessage(event, repo);
            case Event.TYPE_MEMBER:
                return getMemberMessage(event, repo);
            case Event.TYPE_PUBLIC:
                return mResources.getString(R.string.event_public_title, repo);
            case Event.TYPE_PULL_REQUEST:
                return getPullRequestMessage(event, repo);
            case Event.TYPE_PULL_REQUEST_REVIEW_COMMENT:
                return getPullRequestReviewCommentMessage(event, repo);
            case Event.TYPE_PUSH:
                return getPushPayloadMessage(event, repo);
            case Event.TYPE_RELEASE:
                return mResources.getString(R.string.event_release_title, repo);
            case Event.TYPE_TEAM_ADD:
                return getTeamAddMessage(event, repo);
            case Event.TYPE_WATCH:
                return mResources.getString(R.string.event_watch_title, repo);
            default:
                return null;
        }
    }

    @NonNull
    private String getMemberMessage(Event event, String repo) {
        MemberPayload mp = (MemberPayload) event.getPayload();
        return mResources.getString(R.string.event_member_title,
                mp.getMember().getLogin(), repo);
    }

    @NonNull
    private String getFollowMessage(Event event) {
        FollowPayload followPayload = (FollowPayload) event.getPayload();
        String login = followPayload.getTarget().getLogin();
        return mResources.getString(R.string.event_follow_title, login);
    }

    @Nullable
    private String getTeamAddMessage(Event event, String repo) {
        TeamAddPayload tap = (TeamAddPayload) event.getPayload();
        Team team = tap.getTeam();
        if (team != null) {
            if (repo != null) {
                return mResources.getString(R.string.event_team_repo_add,
                        repo, team.getName());
            } else {
                return mResources.getString(R.string.event_team_user_add,
                        tap.getUser().getLogin(), team.getName());
            }
        }
        return null;
    }

    @NonNull
    private String getPushPayloadMessage(Event event, String repo) {
        PushPayload pushPayload = (PushPayload) event.getPayload();

        String ref = pushPayload.getRef();
        if (ref.startsWith(REF_HEADS)) {
            ref = ref.substring(11);
        }

        return mResources.getString(R.string.event_push_title,
                ref, repo);
    }

    @Nullable
    private String getPullRequestReviewCommentMessage(Event event, String repo) {
        PullRequestReviewCommentPayload pullrp =
                (PullRequestReviewCommentPayload) event.getPayload();

        PullRequest pr = pullrp.getPullRequest();
        CommitComment comment = pullrp.getComment();

        if (pr != null) {
            return mResources.getString(R.string.event_pull_request_review_comment_title,
                    pr.getNumber(), repo);
        } else if (comment != null) {
            return mResources.getString(R.string.event_commit_comment_title,
                    comment.getCommitId().substring(0, 7), repo);
        }
        return null;
    }

    @NonNull
    private String getPullRequestMessage(Event event, String repo) {
        PullRequestPayload prp = (PullRequestPayload) event.getPayload();
        PullRequest pr = prp.getPullRequest();
        final int resId;

        switch (prp.getAction()) {
            case OPENED:
                resId = R.string.event_pr_open_title;
                break;
            case CLOSED:
                resId = pr.isMerged() ? R.string.event_pr_merge_title : R.string.event_pr_close_title;
                break;
            case REOPENED:
                resId = R.string.event_pr_reopen_title;
                break;
            case SYNCHRONIZED:
                resId = R.string.event_pr_update_title;
                break;
            default:
                resId = 0;
        }

        return mResources.getString(resId, prp.getNumber(), repo);
    }

    @NonNull
    private String getIssueMessage(Event event, String repo) {
        IssuesPayload ip = (IssuesPayload) event.getPayload();
        final int resId;

        switch (ip.getAction()) {
            case OPENED:
                resId = R.string.event_issues_open_title;
                break;
            case CLOSED:
                resId = R.string.event_issues_close_title;
                break;
            case REOPENED:
                resId = R.string.event_issues_reopen_title;
                break;
            default:
                return "";
        }

        return mResources.getString(resId, ip.getIssue().getNumber(), repo);
    }

    @Nullable
    private String getIssueCommentMessage(Event event, String repo) {
        IssueCommentPayload icp = (IssueCommentPayload) event.getPayload();
        Issue issue = icp.getIssue();
        if (issue != null) {
            int formatResId = issue.getPullRequest() != null
                    ? R.string.event_pull_request_comment
                    : R.string.event_issue_comment;
            return mResources.getString(formatResId,
                    issue.getNumber(), repo);
        }
        return null;
    }

    @NonNull
    private String getGollumMessage(Event event, String repo) {
        GollumPayload gollumPayload = (GollumPayload) event.getPayload();
        List<GollumPage> pages = gollumPayload.getPages();
        int count = pages == null ? 0 : pages.size();
        String pageTitle = mResources.getQuantityString(R.plurals.page, count, count);
        return mResources.getString(R.string.event_gollum_title, pageTitle, repo);
    }

    @NonNull
    private String getGistMessage(Event event) {
        GistPayload gistPayload = (GistPayload) event.getPayload();
        Gist gist = gistPayload.getGist();

        String id = gist == null
                ? mResources.getString(R.string.deleted)
                : gist.getId();

        int resId = 0;
        String s = gistPayload.getAction();

        if (s.equals(CREATE)) {
            resId = R.string.event_update_gist_title;
        } else if (s.equals(UPDATE)) {
            resId = R.string.event_update_gist_title;

        }

        return mResources.getString(resId, id);
    }

    @Nullable
    private String getDeleteMessage(Event event, String repo) {
        DeletePayload deletePayload = (DeletePayload) event.getPayload();

        switch (deletePayload.getRefType()) {
            case BRANCH:
                return mResources.getString(R.string.event_delete_branch_title,
                        deletePayload.getRef(), repo);
            case TAG:
                return mResources.getString(R.string.event_delete_tag_title,
                        deletePayload.getRef(), repo);
        }
        return null;
    }

    @NonNull
    private String getCommitCommentMessage(Event event, String repo) {
        CommitCommentPayload payload = (CommitCommentPayload) event.getPayload();
        return mResources.getString(R.string.event_commit_comment_title,
                payload.getComment().getCommitId().substring(0, 7),
                repo);
    }

    @Nullable
    private String getCreateMessage(Event event, String repo) {
        CreatePayload createPayload = (CreatePayload) event.getPayload();
        String s = createPayload.getRefType();

        switch (s) {
            case TAG:
                return mResources.getString(R.string.event_create_branch_title,
                        createPayload.getRef(), repo);
            case BRANCH:
                return mResources.getString(R.string.event_create_tag_title,
                        createPayload.getRef(), repo);
            case REPOSITORY:
                return mResources.getString(R.string.event_create_repo_title, repo);
        }
        return null;
    }

    private String getRepoName(EventRepository repository) {
        if (repository != null) {
            return repository.getName();
        }
        return mResources.getString(R.string.deleted);
    }

    @OnClick(R.id.item_events_layout)
    public void onClick(){
        EventRepository repo = mEvent.getRepo();

        if(repo != null) {
            String repoName = repo.getName();
            String[] split = repoName.split("[/]");

            Intent intent = RepositoryActivity.newIntent(mContext, split[0], split[1]);
            mContext.startActivity(intent);
        }
    }
}
