package ua.dp.michaellang.gitsurf.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import ua.dp.michaellang.gitsurf.Constants;

/**
 * Date: 05.03.17
 *
 * @author Michael Lang
 */
public final class SPUtil {

    private SPUtil() {

    }

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(Constants.Prefs.PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void putString(Context context, String key, String value) {
        getPrefs(context).edit()
                .putString(key, value)
                .apply();
    }

    public static String getValue(Context context, String key) {
        return getPrefs(context).getString(key, null);
    }

    public static String getValue(Context context, String key, String defaultValue) {
        return getPrefs(context).getString(key, defaultValue);
    }

    public static String getAuthLogin(Context context) {
        return getPrefs(context).getString(Constants.Prefs.User.LOGIN, null);
    }

    public static String getAuthToken(Context context) {
        return getPrefs(context).getString(Constants.Prefs.User.TOKEN, null);
    }

    @Nullable
    public static String getCodeStyle(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String key = sharedPref.getString(Constants.Prefs.KEY_PREF_CODE_STYLE, null);

        if (key != null) {
            key = key.toUpperCase().replace(" ", "_");
            return CodeStyles.valueOf(key).getValue();
        }

        return null;
    }

}
