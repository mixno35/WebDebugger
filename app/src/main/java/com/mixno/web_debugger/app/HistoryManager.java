package com.mixno.web_debugger.app;

import android.content.Context;

import java.io.File;

import static com.mixno.web_debugger.app.Data.*;

public class HistoryManager {

    public static void addHistory(Context context, String url) {
        try {
            String ID_TAB = gerRandomString(18);
            if (Data.createFile(PATH_HISTORY + File.separator + ID_TAB + ".eih")) {
                Data.write(new File(PATH_HISTORY + File.separator + ID_TAB + ".eih"), url);
            }
        } catch (Exception e) {}
    }
}
