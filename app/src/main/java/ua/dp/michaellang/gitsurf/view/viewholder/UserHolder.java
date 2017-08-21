package ua.dp.michaellang.gitsurf.view.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.ImageUtil;
import ua.dp.michaellang.gitsurf.view.users.UserActivity;

/**
 * Date: 17.03.17
 *
 * @author Michael Lang
 */
public class UserHolder extends BaseHolder<User>{
    private User mUser;
    private Context mContext;

    @BindView(R.id.item_user_avatar_IV) ImageView mAvatarIV;
    @BindView(R.id.item_user_login_TV) TextView mLoginTV;

    public UserHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Context context, User user) {
        mUser = user;
        mContext = context;

        ImageUtil.loadUserCircleAvatar(context, mAvatarIV, mUser.getAvatarUrl());
        mLoginTV.setText(mUser.getLogin());
    }

    @OnClick(R.id.item_user_layout)
    public void onClick() {
        if(mUser != null){
            Intent intent = UserActivity.newIntent(mContext, mUser.getLogin());
            mContext.startActivity(intent);
        }
    }
}
