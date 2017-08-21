package ua.dp.michaellang.gitsurf.view.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import org.eclipse.egit.github.core.event.Event;
import ua.dp.michaellang.gitsurf.adapter.EventsAdapter;
import ua.dp.michaellang.gitsurf.adapter.LoadMoreAdapter;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;
import ua.dp.michaellang.gitsurf.presenter.events.EventListImpl;
import ua.dp.michaellang.gitsurf.presenter.events.EventView;
import ua.dp.michaellang.gitsurf.view.PagedFragment;

/**
 * Date: 01.06.17
 *
 * @author Michael Lang
 */
public class EventsFragment extends PagedFragment<Event>
        implements EventView{
    private static final String ARG_LOGIN = "LOGIN";
    private static final String ARG_IS_ORG = "IS_ORG";
    private static final String ARG_IS_RECIEVED = "IS_RECIEVED";

    private String mLogin;
    private boolean mIsOrg;
    private boolean mIsReceived;

    private EventListImpl mPresenter;
    private EventsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArguments();
        mPresenter = new EventListImpl(getContext(), this, mLogin, mIsOrg, mIsReceived);
        mAdapter = new EventsAdapter(getContext());
    }

    private void loadArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mLogin = arguments.getString(ARG_LOGIN);
            mIsOrg = arguments.getBoolean(ARG_IS_ORG, false);
            mIsReceived = arguments.getBoolean(ARG_IS_RECIEVED, false);
        }
    }

    public static EventsFragment newInstance(String login, boolean isOrg, boolean isReceived) {
        Bundle args = new Bundle();
        args.putString(ARG_LOGIN, login);
        args.putBoolean(ARG_IS_ORG, isOrg);
        args.putBoolean(ARG_IS_RECIEVED, isReceived);

        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected PagedPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected LoadMoreAdapter<Event> getAdapter() {
        return mAdapter;
    }
}
