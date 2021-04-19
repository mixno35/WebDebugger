package com.mixno.web_debugger.model;

public class TabModel {

    public String title;
    public String url;
    public long time;

    public TabModel(String title, String url, long time) {
        this.title = title;
        this.url = url;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }
    public String getUrl() {
        return url;
    }
    public long getTime() {
        return time;
    }

}
