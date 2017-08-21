package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.eclipse.egit.github.core.RepositoryContents;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.viewholder.RepositoryFileHolder;

import java.util.List;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class RepositoryFilesAdapter extends BaseAdapter<RepositoryContents> {

    private OnRepositoryFileClickListener mClickListener;

    public RepositoryFilesAdapter(Context context, OnRepositoryFileClickListener listener) {
        super(context);
        mClickListener = listener;
    }

    public RepositoryFilesAdapter(Context context, List<RepositoryContents> data,
            OnRepositoryFileClickListener listener) {
        super(context, data);
        mClickListener = listener;
    }

    @Override
    protected RepositoryFileHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_repository_file, parent, false);
        final RepositoryFileHolder repositoryFileHolder = new RepositoryFileHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepositoryContents file = repositoryFileHolder.getContents();
                mClickListener.onClick(file);
            }
        });
        return repositoryFileHolder;
    }

    public interface OnRepositoryFileClickListener {
        void onClick(RepositoryContents file);
    }
}
