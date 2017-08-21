package ua.dp.michaellang.breadcrumbs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 07.06.17
 *
 * @author Michael Lang
 */
public class BreadcrumbsView extends LinearLayout
        implements View.OnClickListener {

    static final String TAG = "BreadcrumbsView";
    public static final String DEFAULT_SEPARATOR = "/";

    public static final int HOME_BUTTON_TAG = -1;

    private static final int VIEW_TYPE_LAST = 9696;
    private static final int VIEW_TYPE_HOME = 9699;

    private List<String> mCrumbs;
    private BreadcrumbsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private ImageView mHomeIV;
    private ImageView mRootSeparatorIV;

    private String mSeparator = DEFAULT_SEPARATOR;
    private String mRootTitle;

    private OnCrumbSelectedListener mCrumbSelectedListener;

    public BreadcrumbsView(Context context) {
        this(context, null);
    }

    public BreadcrumbsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BreadcrumbsView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.breadcrumbs_layout, this);
        setSaveEnabled(true);
        init();
        loadAttrs(context, attrs);

        mCrumbs = new ArrayList<>();
    }

    private void init() {
        setGravity(HORIZONTAL);
        setVerticalGravity(Gravity.CENTER_VERTICAL);

        mRootSeparatorIV = (ImageView) findViewById(R.id.breadcrumbs_layout_root_IV);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.breadcrumbs_layout_RV);
        mHomeIV = (ImageView) findViewById(R.id.breadcrumbs_layout_home_IV);
        mHomeIV.setTag(HOME_BUTTON_TAG);

        mLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BreadcrumbsAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * @param context Context
     * @param attrs   An attribute set which can contain attributes from
     *                {@link ua.dp.michaellang.breadcrumbs.BreadcrumbsView} as well as attributes inherited
     *                from {@link android.view.View}.
     */
    private void loadAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BreadcrumbsView,
                0, 0);

        boolean homeButtonFlag = false;
        String rootTitle;

        try {
            homeButtonFlag = typedArray
                    .getBoolean(R.styleable.BreadcrumbsView_showHomeButton, false);
            rootTitle = typedArray
                    .getString(R.styleable.BreadcrumbsView_rootTitleText);
        } finally {
            typedArray.recycle();
        }

        if(rootTitle != null){
            setRootTitle(rootTitle);
        }

        showHomeButton(homeButtonFlag);
    }

    /**
     * Assigns text to the root of the directory
     *
     * @param title Root directory name
     */
    public void setRootTitle(String title) {
        mRootTitle = title;
        mCrumbs.add(0, mRootTitle);
        updateUI();
    }

    public void removeRootTitle() {
        if(mRootTitle != null){
            mRootTitle = null;
            mCrumbs.remove(0);
        }

        updateUI();
    }

    /**
     * Set the visibility state of the home button.
     *
     * @param flag Visibility Flag
     */
    public void showHomeButton(boolean flag) {
        if (flag) {
            mHomeIV.setVisibility(VISIBLE);
            mHomeIV.setOnClickListener(this);
        } else {
            mHomeIV.setVisibility(GONE);
            mHomeIV.setOnClickListener(null);
        }
    }

    /**
     * Set the separator for crumbs.
     *
     * @param separator used to separate crumbs
     */
    public void setSeparator(String separator) {
        mSeparator = separator;
    }

    /**
     * Get the separator for crumbs.
     *
     * @return crumbs separator
     */
    public String getSeparator() {
        return mSeparator;
    }

    /**
     * Appends the specified crumb to the end of this layout.
     *
     * @param crumb crumb to be appended to this layout.
     */
    public void addCrumb(String crumb) {
        mCrumbs.add(crumb);
        updateUI();
    }

    /**
     * Removes the crumb at the specified position in this layout.
     *
     * @param position the index of the crumb to be removed.
     */
    public void removeCrumbs(int position) {
        int size = mCrumbs.size() - 1;
        for (int i = size; i > position; i--) {
            mCrumbs.remove(i);
        }

        updateUI();
    }

    /**
     * Removes all of the crumbs from this layout.
     */
    public void clearCrumbs() {
        mCrumbs.clear();
        if(mRootTitle != null){
            mCrumbs.add(0, mRootTitle);
        }

        updateUI();
    }

    public void setFullPath(String absolutePath, String separator) {
        if (absolutePath.startsWith(separator)) {
            absolutePath = absolutePath.substring(separator.length());
        }

        String[] split = absolutePath.split(separator);
        clearCrumbs();
        Collections.addAll(mCrumbs, split);
        updateUI();
    }

    public String getAbsolutePath(int position, @NonNull String separator) {
        if (position < 0 || position >= getCrumbsCount()) {
            throw new IllegalArgumentException("Invalid position value - " + position);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= position; i++) {
            sb.append(mCrumbs.get(i)).append(separator);
        }

        return sb.toString();
    }

    public String getAbsolutePath(@NonNull String separator) {
        return getAbsolutePath(getCrumbsCount() - 1, separator);
    }

    public String getCurrentCrumb() {
        int crumbsCount = getCrumbsCount();
        if (crumbsCount > 0) {
            return mCrumbs.get(crumbsCount - 1);
        }

        return null;
    }

    public int getCrumbsCount() {
        return mCrumbs.size();
    }

    public void setCrumbSelectedListener(OnCrumbSelectedListener crumbSelectedListener) {
        mCrumbSelectedListener = crumbSelectedListener;
    }

    private void updateUI() {
        mAdapter.notifyDataSetChanged();
        mLayoutManager.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.setCrumbs(mCrumbs);
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        mCrumbs = ss.getCrumbs();
        super.onRestoreInstanceState(ss.getSuperState());
    }

    @Override
    public void onClick(View v) {
        if (mCrumbSelectedListener != null) {
            Integer position = (Integer) v.getTag();

            if (position == HOME_BUTTON_TAG) {
                clearCrumbs();
                mCrumbSelectedListener.onHomeSelected();
            } else {
                String absolutePath = getAbsolutePath(position, mSeparator);
                String crumb = mCrumbs.get(position);
                mCrumbSelectedListener.onCrumbSelected(crumb, absolutePath, position);
                removeCrumbs(position);
            }
        }
    }

    private final class BreadcrumbsAdapter extends RecyclerView.Adapter<BreadcrumbHolder> {

        @Override
        public BreadcrumbHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater factory = LayoutInflater.from(getContext());
            View view = factory.inflate(R.layout.item_breadcrumb, parent, false);
            return new BreadcrumbHolder(view);
        }

        @Override
        public void onBindViewHolder(BreadcrumbHolder holder, int position) {
            String crumb = mCrumbs.get(position);
            holder.onBind(crumb, position, BreadcrumbsView.this);
        }

        @Override
        public int getItemCount() {
            return mCrumbs.size();
        }

        @Override
        public int getItemViewType(int position) {
            if(mRootTitle != null && position == 0){
                return VIEW_TYPE_HOME;
            }

            if (position == getCrumbsCount() - 1) {
                return VIEW_TYPE_LAST;
            }
            return super.getItemViewType(position);
        }
    }

    private static final class BreadcrumbHolder extends RecyclerView.ViewHolder {
        private final ImageView mSeparatorIV;
        private final TextView mTextView;

        public BreadcrumbHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_breadcrumb_TV);
            mSeparatorIV = (ImageView) itemView.findViewById(R.id.item_breadcrumb_IV);
        }

        public void onBind(String text, int position,
                OnClickListener listener) {
            mTextView.setTag(position);
            mTextView.setText(text);
            mTextView.setOnClickListener(listener);

            switch (getItemViewType()){
                case VIEW_TYPE_LAST:
                    mTextView.setTypeface(null, Typeface.BOLD);
                    break;
                case VIEW_TYPE_HOME:
                    itemView.setTag(HOME_BUTTON_TAG);
                    break;
            }
        }
    }

    private static final class SavedState extends BaseSavedState {
        private List<String> mCrumbs;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            if(mCrumbs == null){
                mCrumbs = new ArrayList<>();
            }

            in.readStringList(mCrumbs);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeStringList(mCrumbs);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        public List<String> getCrumbs() {
            return mCrumbs;
        }

        public void setCrumbs(List<String> crumbs) {
            mCrumbs = crumbs;
        }

    }

    public interface OnCrumbSelectedListener {
        void onHomeSelected();

        void onCrumbSelected(String crumb, String absolutePath, int position);
    }
}
