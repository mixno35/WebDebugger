package com.mixno.web_debugger.web;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.widget.WebEI;

import java.io.DataInputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.URL;

public class SSL {

    private static String title, message, host, htp, htp2, htp3;
    private static URL hostURL;

    private static String textColorTheme = "black";

    private static long durationAnim = 200;

    private static String o = "";
    private static String c = "";
    private static String d = "";
    private static String u = "";
    private static String dateValid = "";
    private static String xHash = "";

    public static void checkSSL(final Context context, final WebEI web, final String url) {
        try {
            hostURL = new URL(url);
            host = hostURL.getHost();
        } catch (Exception e) {}

        if (url.startsWith("http://")) {
            htp = "<strong><font color=\"#BB0000\">http</font></strong>";
            htp2 = "http";
            htp3 = "http://";
        } if (url.startsWith("https://")) {
            htp = "<strong><font color=\"#008D07\">https</font></strong>";
            htp2 = "https";
            htp3 = "https://";
        }

        message = String.format(context.getString(R.string.ssl_check_warning_message), "<strong>" + htp3 + host + "</strong>");
        title = context.getString(R.string.ssl_check_warning_title);

        if (web.checkSSL(url)) {
            message = String.format(context.getString(R.string.ssl_check_ok_message), "<strong>" + htp3 + host + "</strong>");
            title = context.getString(R.string.ssl_check_ok_title);
        } else {
            message = String.format(context.getString(R.string.ssl_check_warning_message), "<strong>" + htp3 + host + "</strong>");
            title = context.getString(R.string.ssl_check_warning_title);
        }

        final AlertDialog dialog = new AlertDialog.Builder(((Activity)context)/*, R.style.AlertDialogThemeSSL*/).create();
        View inflate = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_ssl_check, null);
        dialog.setView(inflate);

        final TextView title_d = inflate.findViewById(R.id.title);
        final TextView title_c = inflate.findViewById(R.id.title_connect);
        final TextView message_d = inflate.findViewById(R.id.message);

        // Certificate
        final LinearLayout itemLinCertificate = inflate.findViewById(R.id.itemLinCertificate);
        final LinearLayout contentLinCertificate = inflate.findViewById(R.id.contentLinCertificate);
        final ImageView imageCertificate = inflate.findViewById(R.id.imageCertificate);
        final TextView textMessageCertificate = inflate.findViewById(R.id.textMessageCertificate);

        // Whois
        final LinearLayout itemLinWhois = inflate.findViewById(R.id.itemLinWhois);
        final LinearLayout contentLinWhois = inflate.findViewById(R.id.contentLinWhois);
        final ImageView imageWhois = inflate.findViewById(R.id.imageWhois);
        final TextView textMessageWhois = inflate.findViewById(R.id.textMessageWhois);


        // Certificate
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contentLinCertificate.setVisibility(View.GONE);
                contentLinCertificate.setAlpha(0f);
                imageCertificate.animate().rotation(0f).setDuration(durationAnim).start();
            }
        });

        textMessageCertificate.post(new Runnable() {
            @Override
            public void run() {
                textMessageCertificate.setText(Html.fromHtml(getCertificate(context, web)));
            }
        });

        itemLinCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentLinCertificate.getVisibility() == View.GONE) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contentLinCertificate.setVisibility(View.VISIBLE);
                            contentLinCertificate.animate().alpha(1f).setDuration(durationAnim).start();
                            imageCertificate.animate().rotation(90f).setDuration(durationAnim).start();
                        }
                    });
                } else {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    contentLinCertificate.setVisibility(View.GONE);
                                }
                            }, durationAnim);
                            contentLinCertificate.animate().alpha(0f).setDuration(durationAnim).start();
                            imageCertificate.animate().rotation(0f).setDuration(durationAnim).start();
                        }
                    });
                }
            }
        });


        //Whois
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contentLinWhois.setVisibility(View.GONE);
                contentLinWhois.setAlpha(0f);
                imageWhois.animate().rotation(0f).setDuration(durationAnim).start();
            }
        });

        textMessageWhois.post(new Runnable() {
            @Override
            public void run() {
//                textMessageWhois.setText(getWhois(host, "br"));
            }
        });

        itemLinWhois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (contentLinWhois.getVisibility() == View.GONE) {
//                    ((Activity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            contentLinWhois.setVisibility(View.VISIBLE);
//                            contentLinWhois.animate().alpha(1f).setDuration(durationAnim).start();
//                            imageWhois.animate().rotation(90f).setDuration(durationAnim).start();
//                        }
//                    });
//                } else {
//                    ((Activity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    contentLinWhois.setVisibility(View.GONE);
//                                }
//                            }, durationAnim);
//                            contentLinWhois.animate().alpha(0f).setDuration(durationAnim).start();
//                            imageWhois.animate().rotation(0f).setDuration(durationAnim).start();
//                        }
//                    });
//                }
                new Whois(context, host);
            }
        });

        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            textColorTheme = "#FFFFFF";
        } else {
            textColorTheme = "black";
        }

        title_d.post(new Runnable() {
            @Override
            public void run() {
                String fullResult = url;
                fullResult = fullResult.replaceAll(htp2, htp);
                fullResult = fullResult.replaceAll(host, "<font color=\"" + textColorTheme + "\">" + host + "</font>");
                title_d.setText(Html.fromHtml(fullResult));
            }
        });
        title_c.post(new Runnable() {
            @Override
            public void run() {
                title_c.setText(title);
            }
        });
        message_d.post(new Runnable() {
            @Override
            public void run() {
                message_d.setText(Html.fromHtml(message));
            }
        });

        dialog.show();

    }

    private static String getCertificate(final Context context, final WebEI view) {
        // OName
        try {
            o = "<strong>" + context.getString(R.string.certificate_issued_to_o) + "</strong>: " + view.getCertificate().getIssuedTo().getOName() + "<br>" +
                "<strong>" + context.getString(R.string.certificate_issued_by_o) + "</strong>: " + view.getCertificate().getIssuedBy().getOName() + "<br>";
        } catch (Exception e) {}

        // CName
        try {
            c = "<strong>" + context.getString(R.string.certificate_issued_to_c) + "</strong>: " + view.getCertificate().getIssuedTo().getCName() + "<br>" +
                "<strong>" + context.getString(R.string.certificate_issued_by_c) + "</strong>: " + view.getCertificate().getIssuedBy().getCName() + "<br>";
        } catch (Exception e) {}

        // DName
        try {
            d = "<strong>" + context.getString(R.string.certificate_issued_to_d) + "</strong>: " + view.getCertificate().getIssuedTo().getDName() + "<br>" +
                "<strong>" + context.getString(R.string.certificate_issued_by_d) + "</strong>: " + view.getCertificate().getIssuedBy().getDName() + "<br>";
        } catch (Exception e) {}

        // UName
        try {
            // u = "<strong>" + context.getString(R.string.certificate_issued_to_u) + "</strong>: " + view.getCertificate().getIssuedTo().getUName() + "<br>" +
            //     "<strong>" + context.getString(R.string.certificate_issued_by_u) + "</strong>: " + view.getCertificate().getIssuedBy().getUName() + "<br>";
        } catch (Exception e) {}

        // Date
        try {

            dateValid = "<strong>" + context.getString(R.string.certificate_before_date) + "</strong>: " + Data.dateFormat(view.getCertificate().getValidNotBeforeDate()) + "<br>" +
                        "<strong>" + context.getString(R.string.certificate_after_date) + "</strong>: " + Data.dateFormat(view.getCertificate().getValidNotAfterDate()) + "<br>";
        } catch (Exception e) {}
        try {
            return  "" +
                    o + c + d + dateValid +
                    "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String getWhois(final String domain, final String tld) {
        String domquest = domain + "." + tld;
        String resultado = "No data";
        Socket theSocket;
        String hostname = "whois.internic.net";
        int port = 43;
        try {
            theSocket = new Socket(hostname, port, true);
            Writer out = new OutputStreamWriter(theSocket.getOutputStream());
            out.write("=" + domain + "\r\n");
            out.flush();
            DataInputStream theWhoisStream;
            theWhoisStream = new DataInputStream(theSocket.getInputStream());
            String s;
            while ((s = theWhoisStream.readLine()) != null) {
                resultado = resultado + s + "\n";
            }
            return resultado;
        } catch (Exception e2) {
            return e2.getMessage();
        }
    }
}
