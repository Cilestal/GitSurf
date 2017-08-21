package ua.dp.michaellang.gitsurf.view.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.ImageUtil;
import ua.dp.michaellang.gitsurf.utils.StringUtil;

/**
 * Date: 06.06.17
 *
 * @author Michael Lang
 */
public class CommitHolder extends BaseHolder<RepositoryCommit> {
    private RepositoryCommit mCommit;

    @BindView(R.id.item_commit_avatar_IV) ImageView mAvatarIV;
    @BindView(R.id.item_commit_login_TV) TextView mLoginTV;
    @BindView(R.id.item_commit_description_TV) TextView mDescriptionTV;
    @BindView(R.id.item_commit_created_at_TV) TextView mCreatedAtTV;
    @BindView(R.id.item_commit_comments_TV) TextView mCommentsCount;

    public CommitHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Context context, RepositoryCommit data) {
        mCommit = data;

        User user = data.getAuthor();
        Commit commit = data.getCommit();
        String comments = String.valueOf(commit.getCommentCount());
        String time = StringUtil.dateFormat(context, commit.getAuthor().getDate());

        if (user != null) {
            ImageUtil.loadUserCircleAvatar(context, mAvatarIV, user.getAvatarUrl());
            mLoginTV.setText(user.getLogin());
        } else {
            ImageUtil.loadUserCircleAvatar(context, mAvatarIV, null);
            mLoginTV.setText(commit.getAuthor().getName());
        }

        mDescriptionTV.setText(commit.getMessage());
        mCreatedAtTV.setText(time);
        mCommentsCount.setText(comments);
    }


}
