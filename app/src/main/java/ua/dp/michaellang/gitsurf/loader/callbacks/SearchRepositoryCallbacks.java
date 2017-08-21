package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import org.eclipse.egit.github.core.SearchRepository;
import ua.dp.michaellang.gitsurf.loader.SearchRepositoryLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class SearchRepositoryCallbacks extends LoaderCallbacks<List<SearchRepository>> {
    private String mQuery;
    private String mLanguage;
    private final Context mContext;

    public SearchRepositoryCallbacks(BasePresenter presenter, Context context) {
        super(presenter);
        mContext = context;
    }

    public void setArguments(String query, String... args) {
        mQuery = query;
        if(args.length > 0){
            mLanguage  = args[0];
        }
    }

    @Override
    public Loader<TaskResult<List<SearchRepository>>> onCreateLoader(int id, Bundle args) {
        if(mLanguage == null) {
            return new SearchRepositoryLoader(mContext, mQuery);
        } else {
            return new SearchRepositoryLoader(mContext, mQuery, mLanguage);
        }
    }
}
