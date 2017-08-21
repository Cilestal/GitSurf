package ua.dp.michaellang.gitsurf.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.adapter.LoadMoreAdapter;
import ua.dp.michaellang.gitsurf.loader.LoadedPage;
import ua.dp.michaellang.gitsurf.presenter.PagedPresenter;
import ua.dp.michaellang.gitsurf.presenter.PagedView;

import static ua.dp.michaellang.gitsurf.Constants.PAGE_ITERATOR_SIZE;

/**
 * Date: 08.04.17
 *
 * @author Michael Lang
 */
public abstract class PagedFragment<T>
        extends RecyclerViewFragment<T>
        implements PagedView<T> {

    protected boolean mIsLoading = false;
    protected boolean mIsLastPage = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (view != null) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.content_recyclerview_RV);
            mEmptyTextView = (TextView) view.findViewById(R.id.content_recyclerview_empty_TV);
            mRecyclerViewPB = (ProgressBar) view.findViewById(R.id.content_recyclerview_progress);
        }

        final int spanCount = getResources().getInteger(R.integer.recycler_view_fragment_span_count);
        mLayoutManager = new GridLayoutManager(getContext(), spanCount);
        mLayoutManager.setSpanSizeLookup(getSpanSizeLookup(spanCount));

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mRecyclerView.setAdapter(getAdapter());
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().loadItems();
    }

    @Override
    public void onRefresh() {
        getPresenter().reloadItems();
    }

    @NonNull
    private GridLayoutManager.SpanSizeLookup getSpanSizeLookup(final int spanCount) {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = getAdapter().getItemViewType(position);
                return viewType == LoadMoreAdapter.LOAD_MORE_VIEW_TYPE ? spanCount : 1;
            }
        };
    }

    @Override
    public void onItemsLoaded(LoadedPage<? extends T> list) {
        showProgress(false);

        if (list.getResults() == null) return;
        mIsLastPage = !list.isHasMoreData();
        mIsLoading = false;

        getAdapter().setData(list.getResults());
        getAdapter().setHasLoading(!mIsLastPage);

        boolean isEmpty = getAdapter().getItemCount() == 0;
        showEmptyTextField(isEmpty);
    }

    protected final RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (getPresenter() != null && mLayoutManager != null) {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                if (!mIsLoading && !mIsLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_ITERATOR_SIZE) {
                        getPresenter().loadMoreItems();
                    }
                }
            }
        }
    };

    @Override
    public void setLoadingStatus(boolean status) {
        mIsLoading = status;
    }

    protected abstract PagedPresenter getPresenter();

    @Override
    protected abstract LoadMoreAdapter<T> getAdapter();
}
