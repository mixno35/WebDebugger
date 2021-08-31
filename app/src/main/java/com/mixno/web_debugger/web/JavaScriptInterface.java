package com.mixno.web_debugger.web;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.dialog.CodeDialog;
import com.mixno.web_debugger.dialog.CookieManagerDialog;
import com.mixno.web_debugger.model.CookieManagerModel;

public class JavaScriptInterface {
    Context context;

    public JavaScriptInterface(Context c) {
        context = c;
    }

    @JavascriptInterface
    public void processHTML(String html) {
        try {
            new CodeDialog().setCodeDialog(context, "", html, 1, new MainActivity().mWeb);
        } catch (Exception e) {}
    }
    @JavascriptInterface
    public void cookieManagerApp(int id, String name, String value, String cookie) {
//        Toast.makeText(context, "id: "+id+" name: "+name+" value: "+value, Toast.LENGTH_SHORT).show();
//        CookieManagerDialog.list.add(new CookieManagerModel(id, name, value, cookie, ""));
    }
    @JavascriptInterface
    public void toast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
