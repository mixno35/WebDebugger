package com.mixno.web_debugger.menu;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;
import com.squareup.picasso.Picasso;

public class ImageMenu {

    public static void alert(final Context context, final WebView.HitTestResult hit) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        final View view = inflater.inflate(R.layout.alert_long_web_image, null);

        final TextView textURL = view.findViewById(R.id.textID);
        final ImageView imagePREVIEW = view.findViewById(R.id.imagePREVIEW);

        textURL.post(new Runnable() {
            @Override
            public void run() {
                textURL.setText(hit.getExtra());
            }
        });

        textURL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Data.clipboard(context, textURL.getText().toString(), true);
                return true;
            }
        });

//        Picasso.with(context)
//                .load(hit.getExtra())
//                .placeholder(R.drawable.ic_image_placeholder)
//                .error(R.drawable.ic_error_image_placeholder)
//                .into(imagePREVIEW);

        imagePREVIEW.post(new Runnable() {
            @Override
            public void run() {
                imagePREVIEW.setVisibility(View.GONE);
            }
        });

        textURL.post(new Runnable() {
            @Override
            public void run() {
                textURL.setMaxLines(3);
            }
        });

        textURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textURL.getMaxLines() > 3) {
                    textURL.setMaxLines(3);
                } else {
                    textURL.setMaxLines(60);
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(hit.getExtra());
        builder.setView(view);

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
