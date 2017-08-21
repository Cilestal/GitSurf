package ua.dp.michaellang.gitsurf.presenter;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class BasePresenterImpl implements BasePresenter {
    private final BaseView mView;

    public BasePresenterImpl(BaseView view) {
        mView = view;
    }

    @Override
    public void onAuthError() {
        mView.onAuthError();
    }

    @Override
    public void onLoadFail() {
        mView.onLoadFail();
    }

    @Override
    public void onLoaderReset() {
        mView.showProgress(false);
    }
}
