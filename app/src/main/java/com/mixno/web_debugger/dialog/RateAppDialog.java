package com.mixno.web_debugger.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;

import com.mixno.web_debugger.AboutAppActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;

public class RateAppDialog {

    public RateAppDialog(final Context context, final SharedPreferences sp) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.title_information));
        builder.setMessage(context.getString(R.string.message_information_dev));
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.action_rate_app), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Data.openUrl(context, "https://play.google.com/store/apps/details?id=" + context.getPackageName());
                } catch (Exception e) {}
                sp.edit().putBoolean("rate", true).apply();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(context.getString(R.string.action_donate), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sp.edit().putBoolean("rate", true).apply();
                context.startActivity(new Intent(context, AboutAppActivity.class));
            }
        });
        builder.setNeutralButton(context.getString(R.string.action_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sp.edit().putBoolean("rate", true).apply();
                dialog.dismiss();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
