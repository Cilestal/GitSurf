package ua.dp.michaellang.gitsurf.loader;

import android.content.Context;
import android.util.Log;
import ua.dp.michaellang.gitsurf.utils.ServiceUtil;

import java.io.IOException;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class IsFollowingUserLoader extends BaseLoader<Boolean> {
    private static final String TAG = IsFollowingUserLoader.class.toString();

    private final String mUserName;

    public IsFollowingUserLoader(Context context, String userName) {
        super(context);
        mUserName = userName;
    }

    @Override
    protected Boolean doLoadInBackground() {
        try {
            return ServiceUtil.getUserService().isFollowing(mUserName);
        } catch (IOException e) {
            Log.i(TAG, e.getMessage());
            return null;
        }
    }
}
