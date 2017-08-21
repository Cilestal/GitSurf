package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.eclipse.egit.github.core.event.Event;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.viewholder.EventHolder;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class EventsAdapter extends LoadMoreAdapter<Event> {
    public EventsAdapter(Context context) {
        super(context);
    }

    public EventsAdapter(Context context, List<Event> data) {
        super(context, data);
    }

    @Override
    protected EventHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_event, parent, false);

        return new EventHolder(view);
    }
}
