package ua.dp.michaellang.gitsurf.presenter.login;

import android.support.annotation.StringRes;
import android.support.v4.app.LoaderManager;
import ua.dp.michaellang.gitsurf.presenter.BaseView;

/**
 * Date: 22.02.17
 *
 * @author Michael Lang
 */
public interface LoginView extends BaseView {
    LoaderManager getLoader();

    void onLoginSuccess(String token);
    void onDialogComplete(String code);
    void onError(@StringRes int resId);
}
