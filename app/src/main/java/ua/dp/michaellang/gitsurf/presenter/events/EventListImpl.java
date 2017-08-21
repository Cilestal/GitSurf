package ua.dp.michaellang.gitsurf.presenter.events;

import android.content.Context;
import org.eclipse.egit.github.core.event.Event;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.callbacks.EventsCallbacks;
import ua.dp.michaellang.gitsurf.loader.callbacks.LoaderCallbacks;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenterImpl;

/**
 * Date: 13.05.17
 *
 * @author Michael Lang
 */
public class EventListImpl extends PagedPresenterImpl<Event>
        implements EventPresenter {
    private static final int LOADER_EVENTS = 0;

    private final LoaderCallbacks<LoadedPage<Event>> mCallbacks;

    public EventListImpl(Context context, final EventView view,
            String login, boolean isOrg, boolean isReceived) {
        super(view);

        mCallbacks = new EventsCallbacks(this, context, login, isOrg, isReceived) {
            @Override
            protected void onResultReady(int id, LoadedPage<Event> result) {
                view.onItemsLoaded(result);
            }
        };
    }

    @Override
    protected int getLoaderId() {
        return LOADER_EVENTS;
    }

    @Override
    protected LoaderCallbacks<?> getCallbacks() {
        return mCallbacks;
    }
}
