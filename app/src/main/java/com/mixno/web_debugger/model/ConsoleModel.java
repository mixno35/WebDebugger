package com.mixno.web_debugger.model;

public class ConsoleModel {

    private String message;
    private int lineNumber;
    private String sourceID;
    private String time;
    private String type;

    public ConsoleModel(String message, int lineNumber, String sourceID, String time, String type) {
        this.message = message;
        this.lineNumber = lineNumber;
        this.sourceID = sourceID;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }
    public int getLineNumber() {
        return lineNumber;
    }
    public String getSourceID() {
        return sourceID;
    }
    public String getTime() {
        return time;
    }
    public String getType() {
        return type;
    }
}
