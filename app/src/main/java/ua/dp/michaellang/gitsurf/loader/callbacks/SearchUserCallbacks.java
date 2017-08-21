package ua.dp.michaellang.gitsurf.loader.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import ua.dp.michaellang.gitsurf.loader.SearchUserLoader;
import ua.dp.michaellang.gitsurf.loader.TaskResult;
import ua.dp.michaellang.gitsurf.presenter.BasePresenter;
import ua.dp.michaellang.gitsurf.services.entity.SearchUser;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class SearchUserCallbacks extends LoaderCallbacks<List<SearchUser>> {
    private String mLogin;
    private final Context mContext;

    public SearchUserCallbacks(BasePresenter presenter, Context context) {
        super(presenter);
        mContext = context;
    }

    public void setArguments(String login) {
        mLogin = login;
    }

    @Override
    public Loader<TaskResult<List<SearchUser>>> onCreateLoader(int id, Bundle args) {
        return new SearchUserLoader(mContext, mLogin);
    }
}
