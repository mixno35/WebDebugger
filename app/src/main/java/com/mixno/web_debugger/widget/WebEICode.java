package com.mixno.web_debugger.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class WebEICode extends WebView {

    public WebEICode(Context context) {
        super(context);
        init();
    }
    public WebEICode(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.getSettings().setJavaScriptEnabled(true);
    }

    public void loadCode(String code) {
        this.loadData(code, "text/html", "UTF-8");
    }
}
