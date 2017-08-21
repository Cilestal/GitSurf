package ua.dp.michaellang.gitsurf.presenter;

import android.support.v4.app.LoaderManager;

import java.util.List;

/**
 * Date: 08.04.17
 *
 * @author Michael Lang
 */
public interface ListView<T> extends BaseView {
    void onItemsLoaded(List<T> list);
    LoaderManager getLoader();
}
