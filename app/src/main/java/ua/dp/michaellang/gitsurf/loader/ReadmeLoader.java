package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import android.util.Log;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.ContentsService;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;
import ua.dp.michaellang.gitsurf.utils.StringUtil;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class ReadmeLoader extends BaseLoader<String> {

    private static final String TAG = ReadmeLoader.class.toString();
    private final String mOwner;
    private final String mRepoName;
    private final String mRef;

    public ReadmeLoader(Context context, String owner, String repoName, String ref) {
        super(context);
        mOwner = owner;
        mRepoName = repoName;
        mRef = ref;
    }

    @Override
    protected String doLoadInBackground() {
        ContentsService contentsService = ServiceUtil.getContentsService();

        try{
            RepositoryContents readme
                    = contentsService.getReadme(new RepositoryId(mOwner, mRepoName), mRef);

            return StringUtil.fromBase64(readme.getContent());
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
            return null; //README not found!
        }
    }
}
