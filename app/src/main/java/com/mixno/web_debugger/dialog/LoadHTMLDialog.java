package com.mixno.web_debugger.dialog;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.widget.WebEI;

public class LoadHTMLDialog {

    private BottomSheetDialog dialog;

    public LoadHTMLDialog(Context context, final WebEI web) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        final View view = inflater.inflate(R.layout.alert_load_html, null);

        final EditText editCode = view.findViewById(R.id.editCode);
        final Button btnSave = view.findViewById(R.id.btnSave);
        final ImageView ivClose = view.findViewById(R.id.ivClose);
        final RadioButton htmlReplace = view.findViewById(R.id.htmlReplace);
        final  RadioButton htmlAppend = view.findViewById(R.id.htmlAppend);

        htmlReplace.setChecked(true);
        htmlAppend.setChecked(false);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1){
                dialog.dismiss();
                if (htmlReplace.isChecked()) {
                    web.loadData(editCode.getText().toString(), "text/html; charset=utf-8", null);
                    return;
                } if (htmlAppend.isChecked()) {
                    web.runJS("(function(){ document.body.innerHTML += '" + Html.fromHtml(editCode.getText().toString()) + "'; })();");
                }
            }
        });

        dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
    }
}
