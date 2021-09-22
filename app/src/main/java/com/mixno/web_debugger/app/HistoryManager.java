package com.mixno.web_debugger.app;

import android.content.Context;

import java.io.File;

import static com.mixno.web_debugger.app.Data.*;

public class HistoryManager {

    public static void addHistory(Context context, String url, String title) {
        try {
            String ID_TAB = gerRandomString(32) + getRandomNum(100, 9999);
            if (Data.createFile(PATH_HISTORY + File.separator + ID_TAB + ".eih")) {
                String makeJSON = "{\"url\":\""+url+"\", \"title\": \""+title+"\"}";
                Data.write(new File(PATH_HISTORY + File.separator + ID_TAB + ".eih"), makeJSON);
            }
        } catch (Exception e) {}
    }
}
