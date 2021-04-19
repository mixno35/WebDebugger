package com.mixno.web_debugger.web;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.TooltipCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.adapter.WebEIConsoleAdapter;
import com.mixno.web_debugger.model.ConsoleModel;
import com.mixno.web_debugger.widget.WebEI;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class WebEIConsole {

    private BottomSheetDialog dialog;
    private TextView textNoLog;
    private EditText editSourceJS;
    private ImageView imageConsoleClear, imageConsoleRefresh;
    private Button buttonCloseConsole, buttonRunConsole;
    private CheckBox checkOpenConsole;
    private RecyclerView listConsole;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    private boolean CHECK_DEFAULT_CONSOLE = false;

    public WebEIConsole(final Context context, final WebEI viewWeb, final ArrayList<ConsoleModel> list) {
        dialog = new BottomSheetDialog(context);
        View inflate = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_console, null);
        dialog.setContentView(inflate);
        dialog.setCancelable(false);

        if (dialog != null) {
            listConsole = inflate.findViewById(R.id.listConsole);
            textNoLog = inflate.findViewById(R.id.textNoLog);
            editSourceJS = inflate.findViewById(R.id.editSourceJS);
            imageConsoleClear = inflate.findViewById(R.id.imageConsoleClear);
            imageConsoleRefresh = inflate.findViewById(R.id.imageConsoleRefresh);
            buttonCloseConsole = inflate.findViewById(R.id.buttonCloseConsole);
            buttonRunConsole = inflate.findViewById(R.id.buttonRunConsole);
            checkOpenConsole = inflate.findViewById(R.id.checkOpenConsole);

            listLayoutManager = new LinearLayoutManager(context);
            listConsole.setLayoutManager(listLayoutManager);
            listAdapter = new WebEIConsoleAdapter(list, context);
            listConsole.setAdapter(listAdapter);

            TooltipCompat.setTooltipText(imageConsoleClear, context.getString(R.string.tooltip_console_clear));
            TooltipCompat.setTooltipText(imageConsoleRefresh, context.getString(R.string.tooltip_console_refresh));
            TooltipCompat.setTooltipText(checkOpenConsole, context.getString(R.string.tooltip_open_console_script_execute));

            if (list.size() > 0) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textNoLog.setVisibility(View.GONE);
                        listConsole.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textNoLog.setVisibility(View.VISIBLE);
                        listConsole.setVisibility(View.GONE);
                    }
                });
            }

            if (editSourceJS != null) {
                editSourceJS.post(new Runnable() {
                    @Override
                    public void run() {
                        editSourceJS.setVerticalScrollBarEnabled(true);
                    }
                });
            }
            if (checkOpenConsole != null) {
                checkOpenConsole.setChecked(CHECK_DEFAULT_CONSOLE);
            }

            buttonCloseConsole.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            buttonRunConsole.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    viewWeb.runJS(editSourceJS);
                    dialog.dismiss();
                    if (checkOpenConsole.isChecked()) {
                        new WebEIConsole(context, viewWeb, MainActivity.consoleList);
                    }
                }
            });

            imageConsoleClear.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    viewWeb.runWeb("javascript:console.clear();");
                    if (list.size() > 0) {
                        MainActivity.consoleList.clear();
                    }
                    dialog.dismiss();
                    new WebEIConsole(context, viewWeb, MainActivity.consoleList);
                }
            });
            imageConsoleRefresh.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                    new WebEIConsole(context, viewWeb, MainActivity.consoleList);
                }
            });
        }

        if (dialog != null) {
            dialog.show();
        }
    }
}
