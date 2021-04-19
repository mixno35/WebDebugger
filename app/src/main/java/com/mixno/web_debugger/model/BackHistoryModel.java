package com.mixno.web_debugger.model;

import android.graphics.Bitmap;

public class BackHistoryModel {

    private String name;
    private String url;
    private Bitmap icon;
    private long time;

    public BackHistoryModel(String name, String url, long time) {
        this.name = name;
        this.url = url;
//        this.icon = icon;
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    /* public Bitmap getIcon() {
        return icon;
    }*/
    public long getTime() {
        return time;
    }
}
