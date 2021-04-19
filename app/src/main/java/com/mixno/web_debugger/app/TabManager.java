package com.mixno.web_debugger.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.widget.WebEI;

import java.io.File;

import static com.mixno.web_debugger.app.Data.PATH_TABS;

public class TabManager {

    public static String newTab(Context context, String url) throws Exception {
        String ID_TAB = Data.gerRandomString(10) + ".eit";
        if (Data.createFile(PATH_TABS + File.separator + ID_TAB)) {
            // context.startActivity(new Intent(context, MainActivity.class).putExtra("tab", ID_TAB + ".eit"));
            try {
                saveTab(context, ID_TAB, new WebEI(context).getUrlHome());
            } catch (Exception e) {}
        }
        return ID_TAB;
    }

    public static void saveTab(Context context, String id, String url) {
        if (new File(PATH_TABS + File.separator + id).exists()) {
            Data.write(new File(PATH_TABS + File.separator + id), url);
        }
    }

    public static String readTab(Context context, String id) {
        return Data.read(new File(PATH_TABS + File.separator + id));
    }

    public static void openTab(Context context, String id) {
        ((Activity)context).finishAffinity();
        ((Activity)context).startActivity(new Intent(context, MainActivity.class).putExtra("tab", id));
    }
}
