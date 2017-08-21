package ua.dp.michaellang.gitsurf.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.adapter.BaseAdapter;
import ua.dp.michaellang.gitsurf.presenter.ListView;

import java.util.List;

/**
 * Date: 03.04.17
 *
 * @author Michael Lang
 */
public abstract class RecyclerViewFragment<T>
        extends BaseFragment
        implements ListView<T> {

    protected RecyclerView mRecyclerView;
    protected TextView mEmptyTextView;
    protected ProgressBar mRecyclerViewPB;

    protected GridLayoutManager mLayoutManager;

    protected int mGridSpanCount = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        initRecyclerView(view);
        mEmptyTextView = (TextView) view.findViewById(R.id.content_recyclerview_empty_TV);
        mRecyclerViewPB = (ProgressBar) view.findViewById(R.id.content_recyclerview_progress);

        return view;
    }

    public void showContent(boolean flag) {
        int visibility = flag ? View.VISIBLE : View.INVISIBLE;
        mRecyclerView.setVisibility(visibility);
    }

    @Override
    public void showProgress(boolean flag) {
        int visibility = flag ? View.VISIBLE : View.GONE;
        mRecyclerViewPB.setVisibility(visibility);
        showContent(!flag);
    }

    protected void showEmptyTextField(boolean flag) {
        int visibility = flag ? View.VISIBLE : View.GONE;
        mEmptyTextView.setVisibility(visibility);
        showContent(!flag);
    }

    protected void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.content_recyclerview_RV);
        mGridSpanCount = getResources().getInteger(R.integer.recycler_view_fragment_span_count);
        mLayoutManager = new GridLayoutManager(getContext(), mGridSpanCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mRecyclerView.setAdapter(getAdapter());
    }

    @Override
    public void onLoadFail() {
        super.onLoadFail();
        showProgress(false);
    }

    @Override
    public void onItemsLoaded(List<T> list) {
        showProgress(false);
        getAdapter().setData(list);

        boolean flag = getAdapter().getItemCount() == 0;
        showEmptyTextField(flag);

        if (!flag) {
            getAdapter().notifyDataSetChanged();
        }
    }

    protected abstract BaseAdapter<T> getAdapter();

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public LoaderManager getLoader() {
        return getLoaderManager();
    }
}
