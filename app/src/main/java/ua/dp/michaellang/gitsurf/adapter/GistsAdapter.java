package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.eclipse.egit.github.core.Gist;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.viewholder.GistHolder;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class GistsAdapter extends LoadMoreAdapter<Gist> {
    public GistsAdapter(Context context) {
        super(context);
    }

    public GistsAdapter(Context context, List<Gist> data) {
        super(context, data);
    }

    @Override
    protected GistHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_gist, parent, false);
        return new GistHolder(view);
    }
}
