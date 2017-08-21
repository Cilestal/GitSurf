package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.viewholder.BaseHolder;
import ua.dp.michaellang.gitsurf.view.viewholder.LoadMoreViewHolder;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public abstract class LoadMoreAdapter<V> extends BaseAdapter<V> {

    //Yo 148, 3-to-the-3-to-the-6-to-the-9. Representin' the ABQ. What up, biatch? Leave it at the tone!
    public static final int LOAD_MORE_VIEW_TYPE = 148_3_3_6_9;

    private boolean hasMoreData = true;

    public LoadMoreAdapter(Context context) {
        super(context);
    }

    public LoadMoreAdapter(Context context, List<V> data) {
        super(context, data);
    }

    @Override
    public BaseHolder<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        if (viewType == LOAD_MORE_VIEW_TYPE) {
            View view = inflater.inflate(R.layout.item_loadmore, parent, false);
            return new LoadMoreViewHolder<>(view);
        }

        return getViewHolder(parent, inflater);
    }

    @Override
    public void onBindViewHolder(BaseHolder<V> holder, int position) {
        if (getItemViewType(position) == LOAD_MORE_VIEW_TYPE) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
            int visibility = hasMoreData ? View.VISIBLE : View.GONE;
            loadMoreViewHolder.itemView.setVisibility(visibility);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasMoreData && position == getItemCount() - 1) {
            return LOAD_MORE_VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int itemCount = super.getItemCount();
        return hasMoreData ? itemCount + 1 : itemCount;
    }

    public void setHasLoading(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
        notifyDataSetChanged();
    }
}
