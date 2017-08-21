package ua.dp.michaellang.gitsurf.view.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.services.entity.SearchUser;
import ua.dp.michaellang.gitsurf.utils.ImageUtil;
import ua.dp.michaellang.gitsurf.utils.StringUtil;
import ua.dp.michaellang.gitsurf.view.users.UserActivity;

/**
 * Date: 06.06.17
 *
 * @author Michael Lang
 */
public class SearchUserHolder extends BaseHolder<SearchUser>{
    private SearchUser mSearchUser;
    private Context mContext;

    @BindView(R.id.item_search_user_avatar_IV) ImageView mAvatarIV;
    @BindView(R.id.item_search_user_login_TV) TextView mLoginTV;
    @BindView(R.id.item_search_user_location_TV) TextView mLocationTV;
    @BindView(R.id.item_search_user_created_at_TV) TextView mCreatedAtTV;

    public SearchUserHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Context context, SearchUser data) {
        mSearchUser = data;
        mContext = context;

        int id = Integer.parseInt(data.getId().substring(5)); //user-id
        ImageUtil.loadUserCircleAvatar(context, mAvatarIV, id);

        mLoginTV.setText(mSearchUser.getLogin());
        mLocationTV.setText(data.getLocation());
        String createdAt = StringUtil.dateFormat(context, data.getCreatedAt());
        mCreatedAtTV.setText(createdAt);
    }

    @OnClick(R.id.item_search_user_layout)
    public void onClick(){
        if(mSearchUser != null) {
            Intent intent = UserActivity.newIntent(mContext, mSearchUser.getLogin());
            mContext.startActivity(intent);
        }
    }
}
