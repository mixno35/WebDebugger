package com.mixno.web_debugger.model;

public class CookieManagerModel {

    private int id;
    private String name;
    private String value;
    private String cookie;

    public CookieManagerModel(int id, String name, String value, String cookie) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.cookie = cookie;
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
}
