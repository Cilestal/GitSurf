package ua.dp.michaellang.gitsurf.loader.callbacks;

import org.eclipse.egit.github.core.Repository;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

import java.util.Map;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class AbstractRepositoryListCallbacks
        extends LoaderCallbacks<LoadedPage<Repository>>
        implements FilteredCallbacks {

    protected Map<String, String> mFilters;

    public AbstractRepositoryListCallbacks(BasePresenter presenter) {
        super(presenter);
    }

    @Override
    public void setFilters(Map<String, String> filters) {
        mFilters = filters;
    }

}
