package ua.dp.michaellang.gitsurf.view.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import org.eclipse.egit.github.core.SearchRepository;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.StringUtil;
import ua.dp.michaellang.gitsurf.view.repos.RepositoryActivity;

import java.util.Date;

/**
 * Date: 12.04.17
 *
 * @author Michael Lang
 */
public class SearchRepositoryHolder extends BaseHolder<SearchRepository> {
    private SearchRepository mRepository;
    private Context mContext;

    @BindView(R.id.item_repository_title_TV) AppCompatTextView mTitleTV;
    @BindView(R.id.item_repository_description_TV) AppCompatTextView mDescriptionTV;
    @BindView(R.id.item_repository_update_time_TV) AppCompatTextView mUpdateTimeTV;
    @BindView(R.id.item_repository_language_TV) AppCompatTextView mLanguageTV;
    @BindView(R.id.item_repository_stars_TV) AppCompatTextView mStarsTV;
    @BindView(R.id.item_repository_forks_TV) AppCompatTextView mForksTV;

    public SearchRepositoryHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Context context, SearchRepository data) {
        mRepository = data;
        mContext = context;

        String repoTitle = context.getString(R.string.repo_title, data.getOwner(), data.getName());
        mTitleTV.setText(repoTitle);
        mDescriptionTV.setText(data.getDescription());

        Date createdAt = data.getCreatedAt();
        mUpdateTimeTV.setText(StringUtil.dateFormat(context, createdAt));

        mLanguageTV.setText(data.getLanguage());

        int watchers = data.getWatchers();
        mStarsTV.setText(String.valueOf(watchers));

        int forks = data.getForks();
        mForksTV.setText(String.valueOf(forks));
    }

    @OnClick(R.id.item_repository_layout)
    public void onClick(){
        if (mRepository != null) {
            String owner = mRepository.getOwner();
            String name = mRepository.getName();

            Intent intent = RepositoryActivity.newIntent(mContext, owner, name);
            mContext.startActivity(intent);
        }
    }
}
