package com.mixno.web_debugger.model;

public class CookieManagerModel {

    private int id;
    private String name;
    private String value;
    private String cookie;
    private String url;

    public CookieManagerModel(int id, String name, String value, String cookie, String url) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.cookie = cookie;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getCookie() {
        return cookie;
    }

    public String getUrl() {
        return url;
    }
}
