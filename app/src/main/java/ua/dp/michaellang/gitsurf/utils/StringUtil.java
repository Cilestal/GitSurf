package ua.dp.michaellang.gitsurf.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import ua.dp.michaellang.gitsurf.R;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.*;

/**
 * Date: 13.04.17
 *
 * @author Michael Lang
 */
public final class StringUtil {
    private static final String TAG = StringUtil.class.toString();

    private StringUtil() {
    }

    public static String dateFormat(Context context, Date date) {
        long now = System.currentTimeMillis();

        if (Math.abs(now - date.getTime()) > 60_000L) {
            return DateUtils.getRelativeTimeSpanString(date.getTime(),
                    now, MINUTE_IN_MILLIS, FORMAT_SHOW_DATE
                            | FORMAT_SHOW_YEAR
                            | FORMAT_NUMERIC_DATE).toString();
        } else {
            return context.getString(R.string.just_now);
        }
    }

    public static String formatByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format(Locale.getDefault(),
                "%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    @Nullable
    public static String fromBase64(String content) {
        byte[] decode = Base64.decode(content, Base64.DEFAULT);
        try {
            return new String(decode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static boolean isImageFile(String path) {
        return (path.endsWith(".jpg") || path.endsWith(".gif") || path.endsWith(".png"));
    }

    public static boolean isCodeFile(String path) {
        return !isImageFile(path)
                && !path.endsWith(".exe")
                && !path.endsWith(".dll");
    }

    @NonNull
    public static String getExtension(String path){
        int index = path.lastIndexOf('.');
        if(index > 0) {
            return path.substring(index + 1);
        } else {
            return "";
        }
    }

    public static String getFileName(String path, char separator){
        int index = path.lastIndexOf(separator);

        if(index < 0){
            return path;
        } else {
            return path.substring(index + 1);
        }
    }
}
