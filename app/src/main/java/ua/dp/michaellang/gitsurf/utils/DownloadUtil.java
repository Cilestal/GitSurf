package ua.dp.michaellang.gitsurf.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import ua.dp.michaellang.gitsurf.R;

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_CONTENTS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.URL_API;
import static ua.dp.michaellang.gitsurf.GitHubConstants.IMAGE_URL;

/**
 * Date: 17.07.2017
 *
 * @author Michael Lang
 */
public class DownloadUtil {
    public static final String ZIP_FORMAT = "zipball";
    public static final String TAR_FORMAT = "tarball";

    public static long downloadRepository(Context context, String owner,
            String repo, String format, String ref) {

        String repoUrl = getRepoUrl(owner, repo, format, ref);

        int strId = format.equals(ZIP_FORMAT)
                ? R.string.download_title_zip
                : R.string.download_title_tar;

        String title = context.getString(strId, owner, repo, ref);

        return download(context, repoUrl, title);
    }


    public static long download(Context context, String url, String fileName) {
        DownloadManager downloadManager = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setAllowedOverRoaming(false);

        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //set the notification
        request.setDescription(context.getString(R.string.download_description));
        request.setTitle(fileName);

        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, fileName);

        // TODO: 17.07.2017
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);

        return downloadManager.enqueue(request);
    }

    public static String getRepoUrl(String owner, String repo, String format, String ref) {
        return URL_API + SEGMENT_REPOS + '/' + owner + '/' +
                repo + '/' + format + '/' + ref;
    }

    public static String getFileUrl(String owner, String repo, String ref, String path) {
        return URL_API + SEGMENT_REPOS + '/' + owner + '/'
                + repo + SEGMENT_CONTENTS + '/' + path + "?ref=" + ref;
    }

    public static String getImageUrl(String owner, String repo, String ref, String path) {
        return IMAGE_URL + owner + '/' + repo + '/' + ref + '/' + path;
    }
}
