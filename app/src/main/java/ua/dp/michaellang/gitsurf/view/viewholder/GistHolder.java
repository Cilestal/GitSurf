package ua.dp.michaellang.gitsurf.view.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.ImageUtil;
import ua.dp.michaellang.gitsurf.utils.StringUtil;

import java.util.Map;

/**
 * Date: 06.06.17
 *
 * @author Michael Lang
 */
public class GistHolder extends BaseHolder<Gist> {
    private Gist mGist;

    @BindView(R.id.item_gist_avatar_IV) ImageView mAvatar;
    @BindView(R.id.item_gist_comment_count_TV) TextView mCommentCount;
    @BindView(R.id.item_gist_created_at_TV) TextView mCreatedAt;
    @BindView(R.id.item_gist_file_count_TV) TextView mFileCount;
    @BindView(R.id.item_gist_file_ext_TV) TextView mFileExt;
    @BindView(R.id.item_gist_login_TV) TextView mLogin;
    @BindView(R.id.item_gist_description_TV) TextView mDescription;
    @BindView(R.id.item_gists_status_view) View mStatusView;

    public GistHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Context context, Gist data) {
        mGist = data;

        User owner = data.getOwner();
        mLogin.setText(owner.getLogin());
        ImageUtil.loadUserCircleAvatar(context, mAvatar, owner.getAvatarUrl());

        mCommentCount.setText(String.valueOf(data.getComments()));

        String time = StringUtil.dateFormat(context, data.getCreatedAt());
        mCreatedAt.setText(time);

        Map<String, GistFile> files = data.getFiles();
        mFileCount.setText(String.valueOf(files.size()));

        if (files.size() == 1) {
            for (Map.Entry<String, GistFile> entry : files.entrySet()) {
                String filename = entry.getValue().getFilename();

                int extIndex = filename.lastIndexOf('.');
                String fileExt = context.getString(R.string.gist_owner_ext_divider,
                        filename.substring(extIndex));

                mFileExt.setText(fileExt);
            }
        }

        mDescription.setText(data.getDescription());

        int color;
        if (data.isPublic()) {
            color = ContextCompat.getColor(context, R.color.colorOpened);
        } else {
            color = ContextCompat.getColor(context, R.color.colorClosed);
        }

        mStatusView.setBackgroundColor(color);
    }
    
    @OnClick(R.id.item_gist_layout)
    public void onClick(){
        // TODO: 17.06.2017
    }
}
