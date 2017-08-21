package ua.dp.michaellang.gitsurf.view;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import ua.dp.michaellang.gitsurf.presenter.BaseView;

public class BaseDialog extends DialogFragment
        implements BaseView {

    private BaseView mBaseView;

    @Override
    public void onAttach(Context context) {
        mBaseView = (BaseView) context;
        super.onAttach(context);
    }

    @Override
    public void showProgress(boolean flag) {
        mBaseView.showProgress(flag);
    }

    @Override
    public void onAuthError() {
        mBaseView.onAuthError();
    }

    @Override
    public void onLoadFail() {
        mBaseView.onLoadFail();
    }

    @Override
    public void onRefresh() {

    }
}
