package com.mixno.web_debugger.model;

public class AboutAppModel {

    public static String TYPE_COPY = "copy";
    public static String TYPE_LINK = "link";

    private String name;
    private String subname;
    private String url;
    private boolean clickable;
    private String typeClickable;

    public AboutAppModel(String name, String subname, String url, boolean clickable, String typeClickable) {
        this.name = name;
        this.subname = subname;
        this.url = url;
        this.clickable = clickable;
        this.typeClickable = typeClickable;
    }

    public String getName() {
        return name;
    }
    public String getSubname() {
        return subname;
    }
    public String getUrl() {
        return url;
    }
    public boolean isClickable() {
        return clickable;
    }
    public String getTypeClickable() {
        return typeClickable;
    }
}
