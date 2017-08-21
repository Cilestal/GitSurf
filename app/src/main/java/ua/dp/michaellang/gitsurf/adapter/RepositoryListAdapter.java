package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.eclipse.egit.github.core.Repository;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.viewholder.RepositoryHolder;

import java.util.List;

/**
 * Date: 13.04.17
 *
 * @author Michael Lang
 */
public class RepositoryListAdapter extends LoadMoreAdapter<Repository> {
    public RepositoryListAdapter(Context context) {
        super(context);
    }

    public RepositoryListAdapter(Context context, List<Repository> data) {
        super(context, data);
    }

    @Override
    protected RepositoryHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_repository, parent, false);

        return new RepositoryHolder(view);
    }
}
