package com.mixno.web_debugger.menu;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.dialog.DownloadFileDialog;
import com.squareup.picasso.Picasso;

public class AnchorMenu {

    public static void alert(final Context context, final WebView.HitTestResult hit) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        final View view = inflater.inflate(R.layout.alert_long_web_anchor, null);

        final TextView textURL = view.findViewById(R.id.textID);

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

        String[] menu = {context.getString(R.string.action_menu_open), context.getString(R.string.action_menu_copy_url), context.getString(R.string.action_menu_share)};
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Open
                        MainActivity.mWeb.loadUrl(hit.getExtra());
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
}
