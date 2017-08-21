package ua.dp.michaellang.gitsurf.presenter;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Date: 04.03.17
 *
 * @author Michael Lang
 */
public interface BaseView extends SwipeRefreshLayout.OnRefreshListener {
    void showProgress(boolean flag);
    void onAuthError();
    void onLoadFail();
}
