package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import butterknife.BindView;
import org.eclipse.egit.github.core.RepositoryBranch;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.view.viewholder.BaseHolder;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class BranchListAdapter extends BaseAdapter<RepositoryBranch> {
    private String mSelectedBranch;
    private final OnItemCheckedListener mListener;

    public BranchListAdapter(Context context, String selectedBranch, OnItemCheckedListener listener) {
        super(context);
        mSelectedBranch = selectedBranch;
        mListener = listener;
    }

    @Override
    protected BranchHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_branch, parent, false);
        return new BranchHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseHolder<RepositoryBranch> holder, int position) {
        RepositoryBranch repositoryBranch = mData.get(position);
        String title = repositoryBranch.getName();
        ((BranchHolder) holder).setChecked(mSelectedBranch.equals(title));

        super.onBindViewHolder(holder, position);
    }

    public interface OnItemCheckedListener {
        void checkItem(String branch);
    }

    class BranchHolder extends BaseHolder<RepositoryBranch> {
        @BindView(R.id.item_branch_title) RadioButton mBranchTitleRB;

        private String mTitle;

        BranchHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Context context, RepositoryBranch data) {
            mTitle = data.getName();
            mBranchTitleRB.setText(mTitle);
            mBranchTitleRB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedBranch = mTitle;
                    mListener.checkItem(mTitle);
                    notifyDataSetChanged();
                }
            });
        }

        void setChecked(boolean flag) {
            mBranchTitleRB.setChecked(flag);
        }
    }
}
