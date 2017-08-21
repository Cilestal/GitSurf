package ua.dp.michaellang.gitsurf.view.viewholder;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.service.IssueService;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.ImageUtil;
import ua.dp.michaellang.gitsurf.utils.StringUtil;

/**
 * Date: 06.06.17
 *
 * @author Michael Lang
 */
public class IssueHolder extends BaseHolder<Issue> {
    private Issue mIssue;

    @BindView(R.id.item_issue_user_avatar_IV) ImageView mUserAvatarIV;
    @BindView(R.id.item_issue_number_TV) TextView mNumberTV;
    @BindView(R.id.item_issue_title_TV) TextView mTitleTV;
    @BindView(R.id.item_issue_opened_at_by_TV) TextView mOpenedByTV;
    @BindView(R.id.item_issue_comments_TV) TextView mCommentsTV;
    @BindView(R.id.item_issue_owner_repo_TV) TextView mOwnerRepoTV;
    @BindView(R.id.item_issue_status_view) View mStatusView;

    public IssueHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Context context, Issue data) {
        mIssue = data;

        Resources resources = context.getResources();

        //https://api.github.com/repos/octocat/Hello-World/issues/1347
        String[] urlPart = data.getUrl().split("/");
        String avatarUrl = data.getUser().getAvatarUrl();
        String ownerAndRepo = resources.getString(R.string.issues_owner_repo,
                urlPart[4], urlPart[5]);
        String createdAt = StringUtil.dateFormat(context, data.getCreatedAt());
        String openedBy = resources.getString(R.string.issues_opened_at_by,
                createdAt, data.getUser().getLogin());
        String number = resources.getString(R.string.issues_number, data.getNumber());
        String comments = String.valueOf(data.getComments());

        int color;

        if (data.getState().equals(IssueService.STATE_OPEN)) {
            color = ContextCompat.getColor(context, R.color.colorOpened);
        } else {
            color = ContextCompat.getColor(context, R.color.colorClosed);
        }

        mOwnerRepoTV.setText(ownerAndRepo);
        mOpenedByTV.setText(openedBy);
        mTitleTV.setText(data.getTitle());
        mNumberTV.setText(number);
        mCommentsTV.setText(comments);
        ImageUtil.loadUserCircleAvatar(context, mUserAvatarIV, avatarUrl);
        mStatusView.setBackgroundColor(color);
    }

    @OnClick(R.id.item_issue_layout)
    public void onClick(){
        // TODO: 17.06.2017
    }
}
