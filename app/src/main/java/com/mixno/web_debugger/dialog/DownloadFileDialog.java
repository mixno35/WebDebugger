package com.mixno.web_debugger.dialog;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.widget.WebEI;

import java.net.URL;

public class DownloadFileDialog {

    private AlertDialog alertD;

    public DownloadFileDialog(final Context context, final String url, final String contentDisposition, final long contentLength) {
//        if (URLUtil.isFileUrl(url)) {
            if (Data.isValidURL(url)) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
                final View view = inflater.inflate(R.layout.alert_download_file, null);

                final TextInputEditText editValue = view.findViewById(R.id.editValue);
                final Button buttonGo = view.findViewById(R.id.buttonGo);
                final Button buttonCancel = view.findViewById(R.id.buttonCancel);
                final TextView textUrl = view.findViewById(R.id.textUrl);
                final TextView textSize = view.findViewById(R.id.textSize);
                final TextView textType = view.findViewById(R.id.textType);

                Uri source = Uri.parse(url);
                DownloadManager.Request request = new DownloadManager.Request(source);

                String filename_one = Data.convertToNormalString(Data.getFileNameFromURL(url));

                if (filename_one.trim().isEmpty()) {
                    filename_one = Data.convertToNormalString(URLUtil.guessFileName(url, contentDisposition, Data.getMimeType(url)));
                }

                textUrl.post(new Runnable() {
                   @Override
                   public void run() {
                       textUrl.setMaxLines(3);
                   }
               });
                textUrl.post(new Runnable() {
                    @Override
                    public void run() {
                        textUrl.setText(url);
                    }
                });

                textSize.post(new Runnable() {
                    @SuppressLint("StringFormatMatches")
                    @Override
                    public void run() {
                        if (contentLength == 0) {
                            textSize.setText(String.format(context.getString(R.string.param_file_size), Data.longFileSize(Data.getFileSize(url))));
                            if (Data.longFileSize(Data.getFileSize(url)).equals("0 B")) {
                                textSize.setVisibility(View.GONE);
                            }
                        } else {
                            textSize.setText(String.format(context.getString(R.string.param_file_size), Data.longFileSize(contentLength)));
                        }
                    }
                });
                textType.post(new Runnable() {
                    @Override
                    public void run() {
                        textType.setText(String.format(context.getString(R.string.param_mime_type_file), Data.getMimeType(url)));
                        if (Data.getMimeType(url) == null) {
                            textType.setVisibility(View.GONE);
                        }
                    }
                });

                textUrl.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Data.clipboard(context, url, true);
                        return true;
                    }
                });

                textUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        textUrl.post(new Runnable() {
                            @Override
                            public void run() {
                                if (textUrl.getMaxLines() == 3) {
                                    textUrl.setMaxLines(20);
                                } else {
                                    textUrl.setMaxLines(3);
                                }
                            }
                        });
                    }
                });

                final String filename = filename_one;
                final String cookies = CookieManager.getInstance().getCookie(url);

                editValue.post(new Runnable() {
                    @Override
                    public void run() {
                        editValue.setText(filename);
                    }
                });

                buttonGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editValue.getText().toString().length() > 6) {
                            String filename_result = editValue.getText().toString();
                            try {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                                request.setMimeType(Data.getMimeType(url));

                                request.addRequestHeader("cookie", cookies);
                                request.addRequestHeader("User-Agent", new WebEI(context).USERAGENT);
                                request.setDescription(context.getString(R.string.download_description));
                                request.setTitle(filename_result);
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename_result);

                                DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                dm.enqueue(request);
                            } catch (Exception e) {
                                Toast.makeText(context, "Unknown error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            alertD.cancel();
                        } else {
                            editValue.setError("Min value length 6 charset!");
                        }
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertD.cancel();
                    }
                });




                final AlertDialog.Builder builderD = new AlertDialog.Builder(context);

                builderD.setView(view);

                builderD.setTitle(context.getString(R.string.title_download));
//                builderD.setMessage(String.format(context.getString(R.string.message_download), filename, filename, Data.longFileSize(Data.getFileSize(url))));

                alertD = builderD.create();
                alertD.show();
            } else {
                Data.toast(context, "Invalid URL!");
            }
//        } else {
//            Data.toast(context, "Invalid file!");
//        }
    }
}
