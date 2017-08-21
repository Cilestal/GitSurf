package ua.dp.michaellang.gitsurf.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import ua.dp.michaellang.gitsurf.presenter.BaseView;

/**
 * Date: 15.03.17
 *
 * @author Michael Lang
 */
public abstract class BaseFragment extends Fragment
        implements BaseView{

    private BaseView mBaseView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentView(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @LayoutRes
    protected abstract int getFragmentView();

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
