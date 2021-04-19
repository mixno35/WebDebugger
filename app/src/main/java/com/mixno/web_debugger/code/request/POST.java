package com.mixno.web_debugger.code.request;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.mixno.web_debugger.R;
import com.mixno.web_debugger.widget.WebEI;

public class POST {

    private static String result = "req=ok";

    public static void alert(final Context context, final WebEI web) {
        final AlertDialog dialog = new AlertDialog.Builder(((Activity)context)).create();
        View inflate = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_request_post, null);
        dialog.setView(inflate);
        dialog.setTitle(String.format(context.getString(R.string.title_emulation_a_request), context.getString(R.string.title_post)));

        final EditText editText = inflate.findViewById(R.id.editText);
        final EditText editTextUrl = inflate.findViewById(R.id.editTextUrl);
        final TextView textHelp = inflate.findViewById(R.id.textHelp);
        final Button buttonGo = inflate.findViewById(R.id.buttonGo);

        textHelp.post(new Runnable() {
            @Override
            public void run() {
                textHelp.setText(Html.fromHtml(context.getString(R.string.message_requesr_help)));
            }
        });

        editText.post(new Runnable() {
            @Override
            public void run() {
                editText.setHint("req=ok");
            }
        });
        editTextUrl.post(new Runnable() {
            @Override
            public void run() {
                editTextUrl.setHint(web.getUrlHome());
                editTextUrl.setText(web.getUrl());
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextUrl.getText().toString().trim().isEmpty()) {
                    editTextUrl.setError("Text empty!");
                    return;
                }
                if (editText.getText().toString().trim().isEmpty()) {
                    editText.setError("Text empty!");
                    return;
                }
                if (editText.getText().toString().startsWith("?")) {
                    editText.setError("Remove symbol with start \"?\"!");
                    return;
                }
                if (editText.getText().toString().startsWith("\n") || editText.getText().toString().endsWith("\n")) {
                    editText.setError("Remove enter start or end!");
                    return;
                }
                if (editText.getText().toString().contains("&")) {
                    editText.setError("Remove symbol's \"&\"!");
                    return;
                }
                result = editText.getText().toString().trim();
                result = result.replaceAll(" ", "%20");
                result = result.replaceAll("\n", "&");
                // Toast.makeText(context, web.getUrl() + "?" + result, Toast.LENGTH_SHORT).show();
                try {
                    web.postUrl(editTextUrl.getText().toString(), result.getBytes());
                } catch (Exception e) {
                    Toast.makeText(context, "Error send POST request: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
