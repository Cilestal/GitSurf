package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.event.Event;
import org.eclipse.egit.github.core.service.EventService;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.loader.PageIteratorLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import static ua.dp.michaellang.gitsurf.Constants.PAGE_ITERATOR_SIZE;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class EventsCallbacks extends LoaderCallbacks<LoadedPage<Event>> {
    private final Context mContext;
    private final String mLogin;

    private final boolean mIsOrg;
    private final boolean mIsReceived;

    public EventsCallbacks(BasePresenter presenter, Context context,
            String login, boolean isOrg, boolean isReceived) {
        super(presenter);
        mContext = context;
        mLogin = login;
        mIsOrg = isOrg;
        mIsReceived = isReceived;
    }

    @Override
    public Loader<TaskResult<LoadedPage<Event>>> onCreateLoader(int id, Bundle args) {
        EventService eventService = ServiceUtil.getEventService();
        PageIterator<Event> pageIterator;

        if (mIsOrg) {
            pageIterator = eventService.pageOrgEvents(mLogin, PAGE_ITERATOR_SIZE);
        } else {
            if (mIsReceived) {
                pageIterator = eventService.pageUserReceivedEvents(mLogin, true, PAGE_ITERATOR_SIZE);
            } else {
                pageIterator = eventService.pageUserEvents(mLogin, true, PAGE_ITERATOR_SIZE);
            }
        }

        return new PageIteratorLoader<>(mContext, pageIterator);
    }
}
