package com.mixno.web_debugger.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class WhatIsItAddonsDialog {

    public static BottomSheetDialog dialog_w;

    private static ImageView ivClose;
    private static TextView textManifest, textManifestDescription;
    private static Button buttonCopy, buttonCreateAddon;

    private static String manifest = "{\n" +
            "  \"id\": \"\",\n" +
            "  \"name\": \"\",\n" +
            "  \"description\": \"\",\n" +
            "  \"icon\": \"\",\n" +
            "  \"main_source\": \"\",\n" +
            "  \"version\": \"\",\n" +
            "  \"version_code\": 0\n" +
            "}";

    public static void openDialog(final Context context) {
        dialog_w = new BottomSheetDialog(context);
        View inflate = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_what_is_it_addons, null);
        dialog_w.setContentView(inflate);
        dialog_w.setCancelable(true);
        ivClose = inflate.findViewById(R.id.ivClose);
        buttonCopy = inflate.findViewById(R.id.buttonCopy);
        buttonCreateAddon = inflate.findViewById(R.id.buttonCreateAddon);
        textManifest = inflate.findViewById(R.id.textManifest);
        textManifestDescription = inflate.findViewById(R.id.textManifestDescription);

        if (dialog_w != null) {
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_w.dismiss();
                }
            });
            buttonCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data.clipboard(context, textManifest.getText().toString(), true);
                }
            });

            buttonCreateAddon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Data.isInstallPackage(context, "com.doctorsteep.ide.web")) {
                        try {
                            context.startActivity(new Intent(context, Class.forName("com.mixno.ide.web.MainActivity")));
                        } catch (Exception e) {
                            Toast.makeText(context, "Error open class for make add-ons!", Toast.LENGTH_SHORT).show();
                            try {
                                Data.openUrl(context, "https://play.google.com/store/apps/details?id=com.doctorsteep.ide.web");
                            } catch (Exception e2) {
                                Toast.makeText(context, "Error open link!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        try {
                            Data.openUrl(context, "https://play.google.com/store/apps/details?id=com.doctorsteep.ide.web");
                        } catch (Exception e) {
                            Toast.makeText(context, "Error open link!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            textManifest.post(new Runnable() {
                @Override
                public void run() {
                    textManifest.setText(manifest);
                }
            });

            textManifestDescription.post(new Runnable() {
                @Override
                public void run() {
                    textManifestDescription.setText(Html.fromHtml(context.getString(R.string.message_manifest_addons)));
                }
            });
        }

        if (dialog_w != null) {
            dialog_w.show();
        }
    }
}
