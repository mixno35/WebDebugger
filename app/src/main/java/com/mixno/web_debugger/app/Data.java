package com.mixno.web_debugger.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.mixno.web_debugger.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.IDN;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {

    private static SharedPreferences sharedData;

    private static String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    private static String RESULT_RANDOM = "";

    public static String URL_STORE_ADDONS_FILE = "https://elementinspector.000webhostapp.com/api/store.php";
    public static String URL_WHOIS = "https://elementinspector.000webhostapp.com/api/whois.php?domain=";

    public static String PATH_HOME = Environment.getExternalStorageDirectory().getPath() + File.separator + ".wd";
    public static String PATH_HISTORY = Environment.getExternalStorageDirectory().getPath() + File.separator + ".wd" + File.separator + ".history";
    public static String PATH_TABS = Environment.getExternalStorageDirectory().getPath() + File.separator + ".wd" + File.separator + ".tabs";
    public static String PATH_ADDONS = Environment.getExternalStorageDirectory().getPath() + File.separator + ".wd" + File.separator + ".addons";
    public static String PATH_FAVOURITES = Environment.getExternalStorageDirectory().getPath() + File.separator + ".wd" + File.separator + ".favourites";

    public static void openUrl(Context context, String url) throws Exception {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void theme(Context context) {
        sharedData = PreferenceManager.getDefaultSharedPreferences(context);
        switch (sharedData.getString("keyTheme", "0")) {
            case "0": // System
                break;
            case "1": // Light
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "2": // Night
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }

    public static void createDefaultFolders(Context context) {
        try {
            createDirectory(PATH_HOME);
            createDirectory(PATH_TABS);
            createDirectory(PATH_HISTORY);
            createDirectory(PATH_ADDONS);
            createDirectory(PATH_FAVOURITES);

            Log.e("HOME PATH", PATH_HOME);
            Log.e("HISTORY PATH", PATH_HISTORY);
            Log.e("TABS PATH", PATH_TABS);
            Log.e("ADDONS PATH", PATH_ADDONS);
            Log.e("FAVOURITES PATH", PATH_FAVOURITES);
        } catch (Exception e) {
            Toast.makeText(context, "Error making app directories - " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isJSONValid(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException ex) {
            try {
                new JSONArray(json);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            File file2 = new File(path);
            file2.mkdirs();
        }
    }

    public static boolean createFile(String path) throws Exception {
        return new File(path).createNewFile();
    }

    public static boolean delete(File path) {
        if (path.isDirectory()) {
            String[] children = path.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = delete(new File(path, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return path.delete();
    }

    public static String gerRandomString(int sizeRandomValue) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeRandomValue);
        for (int i = 0; i < sizeRandomValue; ++i) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
            RESULT_RANDOM = sb.toString();
        }
        return RESULT_RANDOM;
    }

    public static String getRootDomain(String url) {
        String host = url;
        try {
            host = new URL(url).getHost();
        } catch (Exception e) {}

        String[] domainKeys = host.split("\\.");
        int sz = domainKeys.length;
        return domainKeys.toString();
    }

    public static int getRandomNum(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static void deleteDF(File path) {
        if (path.isDirectory())
            for (File path2 : path.listFiles())
                deleteDF(path2);

        path.delete();
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    public static long getFileSize(String url) {
        try {
            long size = 0;
            URL url1 = new URL(url);
            URLConnection connection = url1.openConnection();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                size = connection.getContentLengthLong();
            } else {
                size = (long)connection.getContentLength();
            }
            connection.getInputStream().close();
            return size;
        } catch (Exception e) {}
        return 0;
    }

    public static String convertToNormalString(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (Exception e) {
            return text;
        }
    }

    public static String getFileNameFromURL(String url) {
        if (url == null) {
            return "";
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return "";
            }
        }
        catch(Exception e) {
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();

        // find end index for ?
        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        // find end index for #
        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        // calculate the end index
        int endIndex = Math.min(lastQMPos, lastHashPos);
        return url.substring(startIndex, endIndex);
    }

    public static boolean isColorHex(String hex) {
        Pattern colorPattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
        Matcher m = colorPattern.matcher(hex);
        return m.matches();
    }

    public boolean isColorDark(int color) {
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if (darkness < 0.5) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean write(File path, String text) {
        try {
            FileOutputStream stream = new FileOutputStream(path);
            try {
                stream.write(text.getBytes());
            } finally {
                stream.close();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String read(File path) {
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
            return text.toString();
        } catch (Exception e) {
            return "";
        }
    }
    public static String readAssets(Context context, String file) {
        String content = "";

        try {
            InputStream stream = context.getAssets().open(file);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            content = new String(buffer);
        } catch (Exception e) {}

        return content;
    }

    public static void shareText(Context context, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.action_menu_share)));
    }

    public static void clipboard(Context context, String text, boolean toast) {
       ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
       ClipData cd = ClipData.newPlainText("CLIPBOARD_WD", text);
       cm.setPrimaryClip(cd);
       if (toast) {
           Toast.makeText(context, context.getString(R.string.toast_clipboard), Toast.LENGTH_LONG).show();
       }
    }

    public static void toast(Context context, String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    public static boolean isDarkColor(int color) {
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if (darkness < 0.5) {
            return false;
        } else {
            return true;
        }
    }

    public static void setGrantPermissionsSettings(final Context context) {
        final SharedPreferences sp = context.getSharedPreferences("showSGPS", Context.MODE_PRIVATE);
        if (!sp.getBoolean("showSGPS", false)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getString(R.string.title_grant_permissions));
            builder.setMessage(context.getString(R.string.message_grant_permissions));
            builder.setCancelable(false);
            builder.setPositiveButton(context.getString(R.string.action_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goToSettings(context);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(context.getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNeutralButton(context.getString(R.string.action_close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sp.edit().putBoolean("showSGPS", true).apply();
                    dialog.dismiss();
                }
            });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public static void setLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility();  // get current flag
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // add LIGHT_STATUS_BAR to flag
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        }
    }
    public static void clearLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags = flags ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // use XOR here for remove LIGHT_STATUS_BAR from flags
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        }
    }

    public static void goToSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + context.getPackageName()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static boolean isValidURL(String url) {
        try {
            return URLUtil.isValidUrl(url);
            // return URLUtil.isValidUrl(url) && Patterns.WEB_URL.matcher(url).matches();
        } catch (Exception e) {}
        return false;
    }
    public static boolean isInstallPackage(Context context, String pkg) {
        try {
            PackageManager pm = context.getPackageManager();
            return pm.getApplicationInfo(pkg, 0).enabled;
        } catch (Exception e) {}
        return false;
    }
    public static boolean isVerifyGP(Context context) {
        List<String> valid = new ArrayList<>(Arrays.asList("com.android.vending", "com.google.android.feedback"));
        final String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        return installer != null && valid.contains(installer);
    }
    @SuppressLint("MissingPermission")
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connect.getActiveNetworkInfo() != null;
    }

    public static String dateFormat(Date d) {
        return DateFormat.getDateInstance(DateFormat.LONG).format(d);
    }

    public static String longFileSize(long size) {
        try {
            if (size <= 0) {
                return "0 B";
            }
            final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
            int digitsGroup = (int)(Math.log10(size)/Math.log10(1024));
            return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitsGroup)) + " " + units[digitsGroup];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0 B";
    }

    public static String getFileName(final String url, final String contentDisposition, final String mimetype) {
        try {
            String filename[] = contentDisposition.split("filename=");
            return filename[1].replace("inline;", "").replace("filename=", "").replace(".+UTF-8''", "").replace("\"", "").trim();
        } catch (Exception e) {}

        return URLUtil.guessFileName(url, contentDisposition, mimetype);
    }

    public static String getBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public static Bitmap getStringToBitmap(String encoded) {
        try {
            byte[] b = Base64.decode(encoded, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
