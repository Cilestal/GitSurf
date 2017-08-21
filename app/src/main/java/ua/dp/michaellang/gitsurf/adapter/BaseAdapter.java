package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import ua.dp.michaellang.gitsurf.view.viewholder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 17.03.17
 *
 * @author Michael Lang
 */
public abstract class BaseAdapter<V>
        extends RecyclerView.Adapter<BaseHolder<V>> {

    protected final Context mContext;
    protected List<? extends V> mData;

    BaseAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    BaseAdapter(Context context, List<V> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public void onBindViewHolder(BaseHolder<V> holder, int position) {
        V data = mData.get(position);
        holder.bind(mContext, data);
    }

    @Override
    public BaseHolder<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return getViewHolder(parent, inflater);
    }

    protected abstract BaseHolder<V> getViewHolder(ViewGroup parent, LayoutInflater inflater);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<? extends V> data) {
        mData = data;
    }
}
