package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.service.RepositoryService;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class SearchRepositoryLoader extends BaseLoader<List<SearchRepository>> {
    private final String mQuery;
    private String mLanguage;

    public SearchRepositoryLoader(Context context, @NonNull String query) {
        super(context);
        this.mQuery = query;
    }

    public SearchRepositoryLoader(Context context, String query, String language) {
        super(context);
        mQuery = query;
        mLanguage = language;
    }

    @Override
    protected List<SearchRepository> doLoadInBackground() throws Exception {
        RepositoryService repositoryService = ServiceUtil.getRepositoryService();

        if (mLanguage == null) {
            return repositoryService.searchRepositories(mQuery);
        } else {
            return repositoryService.searchRepositories(mQuery, mLanguage);
        }
    }
}
