package com.mixno.web_debugger.model;

public class AddonsModel {

    public int id;
    public String name;
    public String description;
    public String path;
    public String icon;
    public String source;
    public String version;
    public int version_code;
    public boolean hideAddons;
    public String manifest;
    public long time;

    public AddonsModel(int id, String name, String description, String path, String icon, String source, String version, int version_code, boolean hideAddons, String manifest, long time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.path = path;
        this.icon = icon;
        this.source = source;
        this.version = version;
        this.version_code = version_code;
        this.hideAddons = hideAddons;
        this.manifest = manifest;
        this.time = time;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getPath() {
        return path;
    }
    public String getIcon() {
        return icon;
    }
    public String getSource() {
        return source;
    }
    public String getVersion() {
        return version;
    }
    public int getVersionCode() {
        return (int)version_code;
    }
    public boolean isHideAddons() {
        return hideAddons;
    }
    public String getManifest() {
        return manifest;
    }
    public long getTime() {
        return time;
    }
}
