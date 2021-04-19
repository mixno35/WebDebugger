package com.mixno.web_debugger.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.TooltipCompat;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.code.ParentInner;
import com.mixno.web_debugger.code.UndoRedo;
import com.mixno.web_debugger.widget.WebEI;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CodeStyleDialog {

    private static BottomSheetDialog dialog;

    public static void setCodeDialog(final Context context, final String style, final WebEI web) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.alert_code_style, null);

        final EditText editCode = view.findViewById(R.id.editCode);
        final Button btnSave = view.findViewById(R.id.btnSave);
        final Button btnDownload = view.findViewById(R.id.btnDownload);
        final ImageView ivClose = view.findViewById(R.id.ivClose);
        final ImageView ivUndo = view.findViewById(R.id.ivUndo);
        final ImageView ivRedo = view.findViewById(R.id.ivRedo);

        final UndoRedo editSetting = new UndoRedo(editCode);

        editCode.setText(context.getString(R.string.toast_loading));
        editCode.setEnabled(false);

        TooltipCompat.setTooltipText(ivClose, context.getString(R.string.tooltip_alert_close));
        TooltipCompat.setTooltipText(ivUndo, context.getString(R.string.tooltip_alert_undo));
        TooltipCompat.setTooltipText(ivRedo, context.getString(R.string.tooltip_alert_redo));

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                dialog.dismiss();
            }
        });
        ivUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                if(editSetting.getCanUndo()) {
                    editSetting.undo();
                }
            }
        });
        ivRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                if(editSetting.getCanRedo()) {
                    editSetting.redo();
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                new Thread(new Runnable() {
                    public void run() {
                        editCode.setTag(new Boolean(true));
                        editCode.post(new Runnable() {
                            @Override
                            public void run() {
                                editCode.setText(style);
                                editCode.setTag(new Boolean(false));
                                editSetting.clearHistory();
                                editCode.setEnabled(true);
                                btnDownload.setEnabled(true);
                                btnDownload.setAlpha(1.0f);
                                ivUndo.setEnabled(true);
                                ivRedo.setEnabled(true);
                                ivUndo.setAlpha(1.0f);
                                ivRedo.setAlpha(1.0f);
                                btnSave.setEnabled(true);
                                btnSave.setAlpha(1.0f);
                            }
                        });
                    }
                }).start();
            }
        }, 1000);

        btnSave.setAlpha(0.5f);
        btnDownload.setAlpha(0.5f);
        btnSave.setEnabled(false);
        btnDownload.setEnabled(false);
        ivUndo.setEnabled(false);
        ivRedo.setEnabled(false);
        ivUndo.setAlpha(0.5f);
        ivRedo.setAlpha(0.5f);

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1){
                dialog.dismiss();
                new MainActivity().mWeb.runJS("");
            }
        });

        dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
    }
}

