package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.eclipse.egit.github.core.User;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.viewholder.UserHolder;

import java.util.List;

/**
 * Date: 17.03.17
 *
 * @author Michael Lang
 */
public class UserListAdapter extends LoadMoreAdapter<User> {

    public UserListAdapter(Context context) {
        super(context);
    }

    public UserListAdapter(Context context, List<User> data) {
        super(context, data);
    }

    @Override
    protected UserHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }
}
