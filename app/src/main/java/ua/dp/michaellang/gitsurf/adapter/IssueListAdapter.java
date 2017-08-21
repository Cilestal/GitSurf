package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.eclipse.egit.github.core.Issue;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.viewholder.IssueHolder;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class IssueListAdapter extends LoadMoreAdapter<Issue> {

    public IssueListAdapter(Context context) {
        super(context);
    }

    IssueListAdapter(Context context, List<Issue> data) {
        super(context, data);
    }

    @Override
    protected IssueHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_issue, parent, false);
        return new IssueHolder(view);
    }
}
