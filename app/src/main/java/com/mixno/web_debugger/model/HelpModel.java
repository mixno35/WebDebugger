package com.mixno.web_debugger.model;

public class HelpModel {

    private String title;
    private String message;

    public HelpModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }
    public String getMessage() {
        return message;
    }
}
