package com.mixno.web_debugger.menu;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;

public class ImageMenu {

    public static void alert(final Context context, final WebView.HitTestResult hit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(hit.getExtra());

        String[] menu = {context.getString(R.string.action_download), context.getString(R.string.action_menu_copy_url), context.getString(R.string.action_menu_share)};
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Download
                        downloadImage(context, hit.getExtra());
                        break;
                    case 1: // Copy URL
                        Data.clipboard(context, hit.getExtra(), true);
                        break;
                    case 2: // Share
                        Data.shareText(context, hit.getExtra());
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private static void downloadImage(final Context context, final String url) {
        if (URLUtil.isValidUrl(url)) {
            try {
                String url1 = url;
                try {
                    url1 = url.split("\\?")[0];
                } catch (Exception e) {}

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url1));
                request.allowScanningByMediaScanner();

                // request.setTitle(DownloadManager.COLUMN_LOCAL_FILENAME);
                request.setVisibleInDownloadsUi(true);
                request.setDescription(context.getString(R.string.download_description));
                // request.setMimeType("image/*");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                if (!url.endsWith(".jpg") || !url.endsWith(".jpeg") || !url.endsWith(".png") || !url.endsWith(".gif")) {
                    // request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Data.gerRandomString(10) + ".png");
                }
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);

                Toast.makeText(context, context.getString(R.string.toast_downloading), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, "Unknown error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Invalid url!", Toast.LENGTH_LONG).show();
        }
    }
}
