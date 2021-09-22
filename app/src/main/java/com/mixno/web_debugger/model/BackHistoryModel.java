package com.mixno.web_debugger.model;

import android.graphics.Bitmap;

public class BackHistoryModel {

    private String name;
    private String url;
    private String title;
    private Bitmap icon;
    private long time;

    public BackHistoryModel(String name, String url, String title, long time) {
        this.name = name;
        this.url = url;
        this.title = title;
//        this.icon = icon;
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    public String getTitle() {
        return title;
    }

    /* public Bitmap getIcon() {
            return icon;
        }*/
    public long getTime() {
        return time;
    }
}
