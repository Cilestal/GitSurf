package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.eclipse.egit.github.core.RepositoryCommit;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.viewholder.CommitHolder;

import java.util.List;

public class CommitListAdapter extends LoadMoreAdapter<RepositoryCommit> {
    public CommitListAdapter(Context context) {
        super(context);
    }

    public CommitListAdapter(Context context, List<RepositoryCommit> data) {
        super(context, data);
    }

    @Override
    protected CommitHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_commit, parent, false);
        return new CommitHolder(view);
    }
}
