package com.mixno.web_debugger.web;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mixno.web_debugger.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.apache.commons.net.whois.WhoisClient;

public class Whois {

    private BottomSheetDialog dialog;
    private String domain2;

    public Whois(final Context context, String domain) {
        this.domain2 = domain.replace("www.", "");
        this.domain2 = domain.replace("m.", "");

        dialog = new BottomSheetDialog(context);
        View inflate = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_whois, null);
        dialog.setContentView(inflate);
        dialog.setCancelable(true);

        final ImageView ivClose = inflate.findViewById(R.id.ivClose);
        final TextView textWhois = inflate.findViewById(R.id.textWhois);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final WhoisClient whois = new WhoisClient();
        try {
            whois.connect(WhoisClient.DEFAULT_HOST);
            textWhois.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        textWhois.setText(whois.query(domain2));
                    } catch (Exception e) {
                        textWhois.setText("No data 2: " + domain2);
                    }
                }
            });
        } catch (Exception e) {
            textWhois.post(new Runnable() {
                @Override
                public void run() {
                    textWhois.setText("No data: " + domain2);
                }
            });
        }


        if (dialog != null) {
            dialog.show();
        }
    }
}
