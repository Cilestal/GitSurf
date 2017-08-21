package ua.dp.michaellang.gitsurf;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Date: 17.07.2017
 *
 * @author Michael Lang
 */
public class ReceiverDownloadNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent downloadManagerIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        downloadManagerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        downloadManagerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(downloadManagerIntent);
    }
}
