package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.eclipse.egit.github.core.SearchRepository;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.viewholder.SearchRepositoryHolder;

import java.util.List;

/**
 * Date: 13.04.17
 *
 * @author Michael Lang
 */
public class RepositorySearchAdapter extends BaseAdapter<SearchRepository> {
    public RepositorySearchAdapter(Context context) {
        super(context);
    }

    public RepositorySearchAdapter(Context context, List<SearchRepository> data) {
        super(context, data);
    }

    @Override
    protected SearchRepositoryHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_search_repository, parent, false);

        return new SearchRepositoryHolder(view);
    }
}
