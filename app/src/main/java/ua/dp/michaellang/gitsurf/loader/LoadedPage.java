package ua.dp.michaellang.gitsurf.loader;

import java.util.List;

/**
 * Date: 19.03.17
 *
 * @author Michael Lang
 */
public class LoadedPage<T> {
    private final List<T> results;
    private final boolean hasMoreData;

    public LoadedPage(List<T> r, boolean hmd) {
        results = r;
        hasMoreData = hmd;
    }

    public List<T> getResults() {
        return results;
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }
}
