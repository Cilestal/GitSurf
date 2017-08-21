package ua.dp.michaellang.gitsurf.view.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import org.eclipse.egit.github.core.RepositoryContents;
import ua.dp.michaellang.gitsurf.R;
import ua.dp.michaellang.gitsurf.utils.StringUtil;

/**
 * Date: 06.06.17
 *
 * @author Michael Lang
 */
public class RepositoryFileHolder extends BaseHolder<RepositoryContents> {
    @BindView(R.id.item_repository_file_IV) ImageView mIconIV;
    @BindView(R.id.item_repository_file_TV) TextView mTitleTV;
    @BindView(R.id.item_repository_file_size_TV) TextView mSizeTV;

    private RepositoryContents mContents;

    public RepositoryFileHolder(View itemView) {
        super(itemView);

        mIconIV = (ImageView) itemView.findViewById(R.id.item_repository_file_IV);
        mTitleTV = (TextView) itemView.findViewById(R.id.item_repository_file_TV);
        mSizeTV = (TextView) itemView.findViewById(R.id.item_repository_file_size_TV);
    }

    @Override
    public void bind(Context context, final RepositoryContents data) {
        mContents = data;
        mTitleTV.setText(data.getName());

        final String type = data.getType();
        switch (type) {
            case RepositoryContents.TYPE_FILE:
                mIconIV.setImageResource(R.drawable.ic_file);
                String byteCount = StringUtil.formatByteCount(data.getSize(), true);
                mSizeTV.setText(byteCount);
                break;
            case RepositoryContents.TYPE_DIR:
                mSizeTV.setVisibility(View.GONE);
                mIconIV.setImageResource(R.drawable.ic_folder);
                break;
            default:
                mIconIV.setImageResource(R.drawable.ic_file);
                break;
        }
    }

    public RepositoryContents getContents() {
        return mContents;
    }
}
