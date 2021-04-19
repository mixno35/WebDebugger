package com.mixno.web_debugger.model;

public class UserAgentModel {

    public String name;
    public String userAgent;
    public int drawable;
    public boolean desktop;

    public UserAgentModel(int drawable, String name, String userAgent, boolean desktop) {
        this.drawable = drawable;
        this.name = name;
        this.userAgent = userAgent;
        this.desktop = desktop;
    }

    public String getName() {
        return name;
    }
    public String getUserAgent() {
        return userAgent;
    }
    public int getDrawable() {
        return drawable;
    }
    public boolean isDesktop() {
        return desktop;
    }
}
