package ua.dp.michaellang.gitsurf.presenter;

import android.support.v4.app.LoaderManager;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;

/**
 * Date: 04.04.17
 *
 * @author Michael Lang
 */
public interface PagedView<T> extends BaseView {
    void onItemsLoaded(LoadedPage<? extends T> list);
    void setLoadingStatus(boolean status);
    LoaderManager getLoader();
}
