package ua.dp.michaellang.gitsurf.view.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import ua.dp.michaellang.gitsurf.R;

/**
 * Date: 06.06.17
 *
 * @author Michael Lang
 */
public class LoadMoreViewHolder<V> extends BaseHolder<V> {

    @BindView(R.id.item_loadmore_PB) ProgressBar mLoadPB;

    public LoadMoreViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Context context, Object data) {
        //stub
    }
}
