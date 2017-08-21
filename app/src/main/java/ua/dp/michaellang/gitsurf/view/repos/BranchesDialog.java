package ua.dp.michaellang.gitsurf.view.repos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import org.eclipse.egit.github.core.RepositoryBranch;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.adapter.BranchListAdapter;
import ua.dp.michaellang.gitsurf.presenter.repos.BranchListImpl;
import ua.dp.michaellang.gitsurf.presenter.repos.BranchListPresenter;
import ua.dp.michaellang.gitsurf.presenter.repos.BranchListView;
import ua.dp.michaellang.gitsurf.view.BaseDialog;

import java.util.List;

/**
 * Date: 25.05.17
 *
 * @author Michael Lang
 */
public class BranchesDialog extends BaseDialog
        implements BranchListView {

    private static final String ARG_OWNER = "ARG_OWNER";
    private static final String ARG_REPO = "ARG_REPO";
    private static final String ARG_BRANCH = "ARG_BRANCH";

    private String mOwner;
    private String mRepoName;

    private BranchListDialogListener mListener;
    private BranchListAdapter mAdapter;
    private BranchListPresenter mPresenter;

    private ProgressBar mProgressBar;

    private String mBranch;

    interface BranchListDialogListener {
        void onBranchDialogItemSelected(String branch);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArgs();

        mAdapter = new BranchListAdapter(getContext(), mBranch, new BranchListAdapter.OnItemCheckedListener() {
            @Override
            public void checkItem(String branch) {
                mBranch = branch;
            }
        });

        mPresenter = new BranchListImpl(getContext(), this, mOwner, mRepoName);
        mPresenter.loadBranchList();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.dialog_branch_list, null);

        mProgressBar = (ProgressBar) view.findViewById(R.id.dialog_branch_list_progress);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.dialog_branch_list_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(mAdapter);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.branches)
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onBranchDialogItemSelected(mBranch);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                })
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BranchListDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Class must implement BranchListDialogListener");
        }
    }

    private void loadArgs() {
        Bundle arguments = getArguments();

        if (arguments != null) {
            mOwner = arguments.getString(ARG_OWNER);
            mRepoName = arguments.getString(ARG_REPO);
            mBranch = arguments.getString(ARG_BRANCH);
        }
    }

    public static BranchesDialog newInstance(String owner, String repo, String branch) {
        Bundle args = new Bundle();
        args.putString(ARG_OWNER, owner);
        args.putString(ARG_REPO, repo);
        args.putString(ARG_BRANCH, branch);

        BranchesDialog fragment = new BranchesDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBranchListLoaded(List<RepositoryBranch> list) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public LoaderManager getLoader() {
        return getLoaderManager();
    }
}
