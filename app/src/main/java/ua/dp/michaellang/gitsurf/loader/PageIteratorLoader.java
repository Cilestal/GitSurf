package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import org.eclipse.egit.github.core.client.PageIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Date: 19.03.17
 *
 * @author Michael Lang
 */
public class PageIteratorLoader<T> extends BaseLoader<LoadedPage<T>> {
    private final PageIterator<? extends T> mPageIterator;
    private final List<T> mDataList;

    public PageIteratorLoader(Context context, PageIterator<? extends T> pageIterator) {
        super(context);
        mPageIterator = pageIterator;
        mDataList = new ArrayList<>();

        onContentChanged();
    }

    @Override
    protected LoadedPage<T> doLoadInBackground() throws Exception {
        boolean hasNext = mPageIterator.hasNext();
        if (hasNext) {
            Collection<? extends T> newData = mPageIterator.next();
            mDataList.addAll(newData);
        }

        return new LoadedPage<>(mDataList, mPageIterator.hasNext());
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}
