package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.services.entity.SearchUser;
import ua.dp.michaellang.gitsurf.view.viewholder.SearchUserHolder;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class SearchUserAdapter extends BaseAdapter<SearchUser> {
    public SearchUserAdapter(Context context) {
        super(context);
    }

    public SearchUserAdapter(Context context, List<SearchUser> data) {
        super(context, data);
    }

    @Override
    protected SearchUserHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_search_user, parent, false);
        return new SearchUserHolder(view);
    }
}
