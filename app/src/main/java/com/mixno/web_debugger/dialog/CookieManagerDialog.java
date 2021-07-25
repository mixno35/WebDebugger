package com.mixno.web_debugger.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mixno.web_debugger.HistoryActivity;
import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.adapter.AddonsAdapter;
import com.mixno.web_debugger.adapter.CookieManagerAdapter;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.JSManager;
import com.mixno.web_debugger.model.AddonsModel;
import com.mixno.web_debugger.model.CookieManagerModel;
import com.mixno.web_debugger.widget.WebEI;

import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CookieManagerDialog {

    public static BottomSheetDialog dialog;

    private ImageView ivClose;

    private RecyclerView listAddons;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    public static List<CookieManagerModel> list = new ArrayList<>();

    public CookieManagerDialog(final Context context, WebEI web) {
        dialog = new BottomSheetDialog(context);
        View inflate = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_cookie_manager, null);
        dialog.setContentView(inflate);
        dialog.setCancelable(false);
        listAddons = inflate.findViewById(R.id.recyclerView);
        ivClose = inflate.findViewById(R.id.ivClose);

        if (dialog != null) {
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    list.clear();
                }
            });

            try {

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Collections.sort(list, new Comparator<CookieManagerModel>() {
                            @Override
                            public int compare(CookieManagerModel o1, CookieManagerModel o2) {
                                return Integer.compare(Integer.valueOf(o1.getId()), Integer.valueOf(o2.getId()));
                            }
                        });
                    }
                }, 400);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listLayoutManager = new LinearLayoutManager(context);
                        listAddons.setLayoutManager(listLayoutManager);
                        listAdapter = new CookieManagerAdapter(list, context, new MainActivity().mWeb);
                        listAddons.setAdapter(listAdapter);
                    }
                }, 600);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if (dialog != null) {
            dialog.show();
        }
    }

    public static void removeCookie(final Context context, final String name, final WebEI web) {
        String host = "*";
        try {
            final URL hostURL = new URL(web.getUrl());
            host = hostURL.getHost();
        } catch (Exception e) {}

        final String finalHost = host;

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.title_cookie_remove));
        builder.setMessage(String.format(context.getString(R.string.message_cookie_remove), name));
        builder.setPositiveButton(context.getString(R.string.action_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                web.runJS("document.cookie = "+name+"=; path=/; domain="+ finalHost +"; expires=Tue, 01 Jan 1970 00:00:00 GMT;");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(context.getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
