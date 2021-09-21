package com.mixno.web_debugger;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.EditSearchManager;
import com.mixno.web_debugger.app.HistoryManager;
import com.mixno.web_debugger.app.JSManager;
import com.mixno.web_debugger.code.request.GET;
import com.mixno.web_debugger.code.request.POST;
import com.mixno.web_debugger.dialog.AddonsDialog;
import com.mixno.web_debugger.dialog.CodeDialog;
import com.mixno.web_debugger.dialog.CodeStyleDialog;
import com.mixno.web_debugger.dialog.CookieManagerDialog;
import com.mixno.web_debugger.dialog.LoadHTMLDialog;
import com.mixno.web_debugger.dialog.RateAppDialog;
import com.mixno.web_debugger.dialog.UserAgentDialog;
import com.mixno.web_debugger.dialog.WelcomeDialog;
import com.mixno.web_debugger.menu.ImageMenu;
import com.mixno.web_debugger.model.BackHistoryModel;
import com.mixno.web_debugger.model.ConsoleModel;
import com.mixno.web_debugger.web.JavaScriptInterface;
import com.mixno.web_debugger.web.SSL;
import com.mixno.web_debugger.web.WebEIConsole;
import com.mixno.web_debugger.widget.WebEI;
import com.google.android.material.appbar.AppBarLayout;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static WebEI mWeb;
    private static Toolbar toolbar;
    private AppBarLayout appbar;
    private EditText mEditSearch;
    private ProgressBar mProgress;
    private ImageView imageSsl;
    private TextView textUrl;
    private LinearLayout linBottomMenu;

    private ImageView actionHome, actionMore;
    private ImageView menuBack, menuForward, menuRefresh;

    public PopupMenu popupMore;

    public static ArrayList<ConsoleModel> consoleList = new ArrayList<>();
    public static ArrayList<BackHistoryModel> backHistoryList = new ArrayList<>();

    private static SharedPreferences shared;
    private SharedPreferences saveSESSION;
    private SharedPreferences rateSP;
    private SharedPreferences rateSPNum;
    private SharedPreferences welcomeApp;

    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};

    // ПОИСК ПО СТРАНИЦЕ
    private LinearLayout linMainIncludeFindToPage;
    private EditText editFind;
    private ImageView imageFindBack, imageFindNext, imageFindClose;
    private TextView textFind;

    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private static final int FILECHOODER_RESULTCODE = 1;
    private static final int REQUEST_CODE_PERMISSION = 15;


    // URL TEXT
    private String url_host;
    private String url_path;

    class WebJavaScriptInterface {
        //log
        class Class1 implements Runnable {
            private String url;
            private String arg;
            private String content;

            Class1(String str1, String str2, String str3) {
                this.url = str1;
                this.arg = str2;
                this.content = str3;
            }
            public WebJavaScriptInterface access(Class1 cn) {
                return null;
            }
            @Override
            public void run() {
                try {
                    //consoleMessage = consoleMessage + (String.format("<font style="+"max-height: 100dp;"+" color="+"#DC1A3B"+"><code>%s</code></font><br>"+"<font color="+"#EC778B"+">"+sRight+" </font>"+"<font color="+"#E6405C"+">"+"%s</font><br>"+"<font color="+"#464646"+">"+sRight+" </font>"+"<font color="+"#EC778B"+"><code>"+"%s</code></font>", new Object[]{url, arg, content}));
                } catch (Exception e) {}
            }
        }

        //print
        class Class2 implements Runnable {
            private String content;
            private String contentparent;

            Class2(String str1, String str2) {
                this.content = str2;
                this.contentparent = str1;
            }
            public WebJavaScriptInterface access(Class2 cn) {
                return null;
            }
            @Override
            public void run() {
                try {
                    new CodeDialog().setCodeDialog(MainActivity.this, this.contentparent, this.content, 0, mWeb);
                } catch (Exception e) {}
            }
        }

        //blocktoogle
        class Class3 implements Runnable {
            private String val;

            Class3(String str1) {
                this.val = str1;
            }
            public WebJavaScriptInterface access(Class3 cn) {
                return null;
            }
            @Override
            public void run() {
                //Data.showMessage(MainActivity.this, val.matches("(1|true)") ? String.format(getString(R.string.message_edit_on), getString(R.string.action_edit_source)) : String.format(getString(R.string.message_edit_off), getString(R.string.action_edit_source)));
                //modEditSource.setText(val.matches("(1|true)") ? getString(R.string.on) : getString(R.string.off));
            }
        }

        //processHTML
        class Class4 implements Runnable {
            private String ht;

            Class4(String ht1) {
                this.ht = ht1;
            }
            public WebJavaScriptInterface access(Class3 cn) {
                return null;
            }
            @Override
            public void run() {
                try {
                    //code html text
                    new CodeDialog().setCodeDialog(MainActivity.this, "", this.ht, 1, mWeb);
                } catch (Exception e) {}
            }
        }

        //showToast
        class Class5 implements Runnable {
            private String text;

            Class5(String text) {
                this.text = text;
            }
            public WebJavaScriptInterface access(Class5 cn) {
                return null;
            }
            @Override
            public void run() {
                try {
                    //code theme-color meta
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {}
            }
        }

        //showAlert
        class Class6 implements Runnable {
            private String title;
            private String message;

            Class6(String title, String message) {
                this.title = title;
                this.message = message;
            }
            public WebJavaScriptInterface access(Class6 cn) {
                return null;
            }
            @Override
            public void run() {
                try {
                    final AlertDialog.Builder bAlertWD = new AlertDialog.Builder(MainActivity.this);
                    bAlertWD.setTitle(title);
                    bAlertWD.setMessage(message);
                    bAlertWD.setPositiveButton(getString(R.string.action_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    final AlertDialog alertWD = bAlertWD.create();
                    alertWD.show();
                } catch (Exception e) {}
            }
        }

        //processCSS
        class Class7 implements Runnable {
            private String style;

            Class7(String str1) {
                this.style = str1;
            }
            public WebJavaScriptInterface access(Class7 cn) {
                return null;
            }
            @Override
            public void run() {
//                try {
//                    new CodeStyleDialog().setCodeDialog(MainActivity.this, this.style, mWeb);
//                } catch (Exception e) {}
                Toast.makeText(MainActivity.this, this.style, Toast.LENGTH_SHORT).show();
            }
        }

        //cookieManager
        class Class8 implements Runnable {
            private int id;
            private String name;
            private String value;
            private String cookie;

            Class8(int id, String name, String value, String cookie) {
                this.id = id;
                this.name = name;
                this.value = value;
                this.cookie = cookie;
            }
            public WebJavaScriptInterface access(Class8 cn) {
                return null;
            }
            @Override
            public void run() {

            }
        }



        @JavascriptInterface
        @SuppressWarnings("unused")
        public void log(String str, String str2, String str3) {
            mWeb.post(new Class1(str, str2, str3));
//            mWeb.runJS("console.log('1:"+str+" 2:"+str2+" 3:"+str3+"');");
        }
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void print(String str, String str2) {
            mWeb.post(new Class2(str, str2));
        }
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void blocktoogle(String str) {
            mWeb.post(new Class3(str));
        }
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            mWeb.post(new Class4(html));
        }
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void toast(String text) {
            mWeb.post(new Class5(text));
        }
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void alert(String title, String message) {
            mWeb.post(new Class6(title, message));
        }
        @SuppressWarnings("unused")
        public void processCSS(String style) {
            mWeb.post(new Class7(style));
        }
        @JavascriptInterface
        public void cookieManagerApp(int id, String name, String value, String cookie) {
            mWeb.post(new Class8(id, name, value, cookie));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Id's
        mWeb = findViewById(R.id.web);
        toolbar = findViewById(R.id.toolbar);
        appbar = findViewById(R.id.appbar);
        mEditSearch = findViewById(R.id.edit_search);
        mProgress = findViewById(R.id.progressBar);
        imageSsl = findViewById(R.id.imageSsl);
        textUrl = findViewById(R.id.textUrl);
        linBottomMenu = findViewById(R.id.linBottomMenu);

        actionHome = findViewById(R.id.actionHome);
        actionMore = findViewById(R.id.actionMore);

        menuBack = findViewById(R.id.menuBack);
        menuForward = findViewById(R.id.menuForward);
        menuRefresh = findViewById(R.id.menuRefresh);

        // ПОИСК ПО СТРАНИЦЕ-------------------------------------------------------------------------------------
        linMainIncludeFindToPage = findViewById(R.id.mainIncludeFindToPage);
        editFind = findViewById(R.id.editFind);
        imageFindBack = findViewById(R.id.actionFindBack);
        imageFindNext = findViewById(R.id.actionFindNext);
        imageFindClose = findViewById(R.id.actionFindClose);
        textFind = findViewById(R.id.textFind);
        // ПОИСК ПО СТРАНИЦЕ-------------------------------------------------------------------------------------

        shared = PreferenceManager.getDefaultSharedPreferences(this);
        saveSESSION = getSharedPreferences("session_url", Context.MODE_PRIVATE);
        rateSP = getSharedPreferences("rate", Context.MODE_PRIVATE);
        rateSPNum = getSharedPreferences("rateNum", Context.MODE_PRIVATE);
        welcomeApp = getSharedPreferences("welcomeApp", Context.MODE_PRIVATE);

        if (!rateSP.getBoolean("rate", false) && rateSPNum.getInt("rateNum", 0) >= 5) {
            new RateAppDialog(this, rateSP);
        }
        closeFindToPage();

        if (!welcomeApp.getBoolean("welcomeApp", false)) {
            new WelcomeDialog(this);
        }

        rateSPNum.edit().putInt("rateNum", rateSPNum.getInt("rateNum", 0) + 1).apply();

        menuRefresh.post(new Runnable() {
            @Override
            public void run() {
                menuRefresh.setImageResource(R.drawable.ic_menu_refresh);
            }
        });

        popupMore = new PopupMenu(MainActivity.this, actionMore);
        popupMore.inflate(R.menu.main_more);
        popupMore.setGravity(Gravity.END);
        popupMore.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.actionDevTools) {
//                    mWeb.runJS(JSManager.DEVTOOLS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        startActivity(new Intent(getApplicationContext(), DevToolsActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Your device not supporting. Support Android SDK " + Build.VERSION_CODES.M + "+ (Android 6.0+)", Toast.LENGTH_SHORT).show();
                    }
                }
                if (item.getItemId() == R.id.actionConsole) {
                    new WebEIConsole(MainActivity.this, mWeb, consoleList);
                }
//                if (item.getItemId() == R.id.actionConsoleEruda) {
//                    mWeb.runJS(JSManager.ERUDA_EI);
//                }
//                if (item.getItemId() == R.id.actionConsoleFireBug) {
//                    mWeb.runJS(JSManager.FIREBUG_EI);
//                }
                if (item.getItemId() == R.id.actionContenteditable) {
                    mWeb.runJS(JSManager.CONTENTEDITABLE);
                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                    }
                }
                if (item.getItemId() == R.id.actionHighlightBorder) {
                    mWeb.runJS(JSManager.HIGHLIGHT_BORDER_V2);
                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                    }
                }
                if (item.getItemId() == R.id.actionElementEditor) {
                    // mWeb.runJS(JSManager.ELEMENT_EDITOR);
                    mWeb.runJS(JSManager.SOURCE_CODE_ELEMENT_EI);
                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                    }
                }
                if (item.getItemId() == R.id.actionStyleEditor) {
                    mWeb.runJS(JSManager.SOURCE_CODE_STYLE_EI);
                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                    }
                }
                if (item.getItemId() == R.id.actionSourcePage) {
                    // mWeb.runJS(JSManager.SOURCE);
                    mWeb.runJS(JSManager.SOURCE_PAGE_EI);
                }
                if (item.getItemId() == R.id.actionViewSource) {
                    mWeb.loadUrl("view-source:" + mWeb.getUrl());
                }
                if (item.getItemId() == R.id.actionRequestGET) {
                    GET.alert(MainActivity.this, mWeb);
                }
                if (item.getItemId() == R.id.actionRequestPOST) {
                    POST.alert(MainActivity.this, mWeb);
                }
                if (item.getItemId() == R.id.actionUserAgent) {
                    new UserAgentDialog(MainActivity.this, mWeb);
                }
                if (item.getItemId() == R.id.actionDesktopVersion) {
                    if (item.isChecked()) {
                        item.setChecked(false);
                        mWeb.setDesktopMode(false);
                    } else {
                        item.setChecked(true);
                        mWeb.setDesktopMode(true);
                    }
                }
                if (item.getItemId() == R.id.actionSettings) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                }
//                if (item.getItemId() == R.id.actionFeedback) {
//                    startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
//                }
//                if (item.getItemId() == R.id.actionHelp) {
//                    startActivity(new Intent(MainActivity.this, HelpActivity.class));
//                }
//                if (item.getItemId() == R.id.actionDonate) {
//                    startActivity(new Intent(MainActivity.this, AboutAppActivity.class));
//                }
                if (item.getItemId() == R.id.actionCookieManager) {
//                    mWeb.runJS(JSManager.COOKIES_MANAGER);
//                    new CookieManagerDialog(MainActivity.this, mWeb);
                    startActivity(new Intent(MainActivity.this, CookieManagerActivity.class));
                }
                if (item.getItemId() == R.id.actionExit) {
                    finish();
                }
                if (item.getItemId() == R.id.actionJavaScript) {
                    // startActivity(new Intent(MainActivity.this, AdditionsActivity.class));
                    new AddonsDialog(MainActivity.this, mWeb);
                }
                if (item.getItemId() == R.id.actionFindToPage) {
                    if (linMainIncludeFindToPage.getVisibility() == View.GONE) {
                        openFindToPage();
                    } else {
                        closeFindToPage();
                    }
                }
                if (item.getItemId() == R.id.actionHistoryM) {
                    // new BackHistory(MainActivity.this, mWeb, backHistoryList);
                    startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                }
                if (item.getItemId() == R.id.actionLoadHTML) {
                    new LoadHTMLDialog(MainActivity.this, mWeb);
                }
                return true;
            }
        });




        // Content
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        if (mWeb != null) {
            try {
                final String urlOpened = getIntent().getData().toString();
                mWeb.loadUrl(urlOpened);
            } catch (Exception e) {
                setSessionSaved();
            }

            mWeb.setWebViewClient(new WebClient());
            mWeb.setWebChromeClient(new WebChrome());
            mWeb.addJavascriptInterface(new WebJavaScriptInterface(), "$$");
            mWeb.addJavascriptInterface(new JavaScriptInterface(this), "Android");

            registerForContextMenu(mWeb);

            mWeb.setFindListener(new WebView.FindListener() {
                @Override
                public void onFindResultReceived(final int i, final int i1, final boolean b) {
                    if (i1 != 0) {
                        textFind.post(new Runnable() {
                            @Override
                            public void run() {
                                textFind.setText(i + 1 + " / " + i1);
                            }
                        });
                    } else {
                        textFind.post(new Runnable() {
                            @Override
                            public void run() {
                                textFind.setText("0 / 0");
                            }
                        });
                    }
                }
            });

            mWeb.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(final String url, final String userAgent, final String contentDisposition, final String mimetype, final long contentLength) {
                    final AlertDialog.Builder builderD = new AlertDialog.Builder(MainActivity.this);

                    Uri source = Uri.parse(url);
                    DownloadManager.Request request = new DownloadManager.Request(source);

                    final String filename_one = Data.getFileName(url, contentDisposition, mimetype);

                    // final String filename = URLUtil.guessFileName(url, contentDisposition, mimetype);
                    final String cookies = CookieManager.getInstance().getCookie(url);

                    builderD.setTitle(getString(R.string.title_download));
                    builderD.setMessage(String.format(getString(R.string.message_download), filename_one, filename_one, Data.longFileSize(contentLength)));
                    builderD.setPositiveButton(getString(R.string.action_download), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                                request.setMimeType(mimetype);

                                request.addRequestHeader("cookie", cookies);
                                request.addRequestHeader("User-Agent", userAgent);
                                request.setDescription(getString(R.string.download_description));
                                request.setTitle(filename_one);
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename_one);

                                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                dm.enqueue(request);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Unknown error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builderD.setNegativeButton(getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    final AlertDialog alertD = builderD.create();
                    alertD.show();
                }
            });
        }
        if (mProgress != null) {
            hideProgress();
            mProgress.setMax(100);
        }
        if (mEditSearch != null) {
            mEditSearch.post(new Runnable() {
                @Override
                public void run() {
                    mEditSearch.setText(mWeb.getUrlHome());
                }
            });
            /* mEditSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, MainSearchActivity.class).putExtra("title", mWeb.getTitle()).putExtra("url", mWeb.getUrl()).putExtra("favicon", mWeb.getFavicon()));
                    overridePendingTransition(0, 0);
                }
            }); */
            mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handler = false;
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        mWeb.runWeb(mEditSearch.getText().toString());
                        EditSearchManager.hideKeyboard(MainActivity.this);
                        handler = true;
                    }
                    return handler;
                }
            });
            mEditSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasfocus) {
                    if (hasfocus) {
                        editSearchChange(mWeb.getUrl());
                        try {
                            mEditSearch.setSelection(mEditSearch.getText().length());
                        } catch (Exception e) {}
                    } else {
                        changeTextUrl(mWeb.getUrl());
                    }
                }
            });
        }
        if (textUrl != null) {
            textUrl.post(new Runnable() {
                @Override
                public void run() {
                    textUrl.setText(getString(R.string.loading_page));
                }
            });
        }
        if (editFind != null) {
            editFind.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && ((keyCode == KeyEvent.KEYCODE_ENTER))) {
                        searchFindToPage();
                    }
                    return false;
                }
            });
        }


        if (menuBack != null) {
            TooltipCompat.setTooltipText(menuBack, getString(R.string.tooltip_back_page));
        }
        if (menuForward != null) {
            TooltipCompat.setTooltipText(menuForward, getString(R.string.tooltip_forward_page));
        }
        if (menuRefresh != null) {
            TooltipCompat.setTooltipText(menuRefresh, getString(R.string.tooltip_refresh_page));
        }


        if (imageFindBack != null) {
            TooltipCompat.setTooltipText(imageFindBack, getString(R.string.tooltip_back_find));
        }
        if (imageFindNext != null) {
            TooltipCompat.setTooltipText(imageFindNext, getString(R.string.tooltip_next_find));
        }



        imageSsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SSL().checkSSL(MainActivity.this, mWeb, mWeb.getUrl());
            }
        });

        // Content other
        actionHome.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mWeb.runHome();
                }
            });
        actionMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (popupMore != null) {
                            popupMore.show();
                        }
                    }
                });
            }
        });

        actionHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                switch (shared.getString("keyLongPressHome", "3")) {
                    case "0": // Console
                        new WebEIConsole(MainActivity.this, mWeb, consoleList);
                        break;
                    case "1": // Source page
                        mWeb.runJS(JSManager.SOURCE_PAGE_EI);
                        break;
                    case "2": // View source
                        mWeb.loadUrl("view-source:" + mWeb.getUrl());
                        break;
                    case "3": // JavaScript
                        new AddonsDialog(MainActivity.this, mWeb);
                        break;
                    case "4": // UserAgent
                        new UserAgentDialog(MainActivity.this, mWeb);
                        break;
                    case "5": // Cookie Manager
//                        mWeb.runJS(JSManager.COOKIES_MANAGER);
//                        new CookieManagerDialog(MainActivity.this, mWeb);
                        startActivity(new Intent(MainActivity.this, CookieManagerActivity.class));
                        break;
                }
                return true;
            }
        });






        // Menu bottom
        menuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWeb.canGoBack()) {
                    mWeb.goBack();
                }
            }
        });
        menuForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWeb.canGoForward()) {
                    mWeb.goForward();
                }
            }
        });
        menuRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shared.getBoolean("keyFakeReloadPage", false)) {
                    setFakeReloadPage();
                } else {
                    if (mWeb.isLoading()) {
                        mWeb.stopLoading();
                    } else {
                        mWeb.reload();
                    }
                }
            }
        });



        imageFindBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeb.post(new Runnable() {
                    @Override
                    public void run() {
                        mWeb.findNext(false);
                    }
                });
            }
        });
        imageFindNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeb.post(new Runnable() {
                    @Override
                    public void run() {
                        mWeb.findNext(true);
                    }
                });
            }
        });
        imageFindClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFindToPage();
                EditSearchManager.hideKeyboard(MainActivity.this);
            }
        });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mWeb.saveState(outState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        mWeb.restoreState(savedInstanceState);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final WebView.HitTestResult result = mWeb.getHitTestResult();
        // MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.context_web, menu);

        if (result.getType() == WebView.HitTestResult.IMAGE_TYPE || result.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            ImageMenu.alert(this, result);
        }
    }

    /* @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.actionDownloadImage:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    } */

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOODER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else {
            Toast.makeText(this, "Failed to upload file!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case REQUEST_CODE_PERMISSION:
                Data.createDefaultFolders(MainActivity.this);
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				Data.setGrantPermissionsSettings(MainActivity.this);
                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        } else {
            Data.createDefaultFolders(MainActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        if (linMainIncludeFindToPage.getVisibility() == View.GONE) {
            if (mWeb.canGoBack()) {
                mWeb.post(new Runnable() {
                    @Override
                    public void run() {
                        mWeb.goBack();
                    }
                });
            } else {
                super.onBackPressed();
            }
        } else {
            closeFindToPage();
        }
    }

    @Override
    protected void onResume() {
        try {
            setLoadSettings();
        } catch (Exception e) {}
        mWeb.post(new Runnable() {
            @Override
            public void run() {
                mWeb.onResume();
            }
        });
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWeb.post(new Runnable() {
            @Override
            public void run() {
                mWeb.onPause();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // On key's
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK: // On long click back button
//                new BackHistory(MainActivity.this, mWeb, backHistoryList);
//                return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU: // Open popup menu
                popupMore.show();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    // Check session saved
    public void setSessionSaved() {
        try {
            if (shared.getBoolean("keySaveSession", true)) {
                mWeb.loadUrl(saveSESSION.getString("session_url", mWeb.getUrlHome()).toString());
                // Toast.makeText(getApplicationContext(), saveSESSION.getString("session_url", mWeb.getUrlHome()).toString(), Toast.LENGTH_LONG).show();
            } else {
                mWeb.runHome();
            }
        } catch (Exception e) {
            mWeb.runHome();
        }
    }




    // ЦВЕТ ПРИЛОЖЕНИЯ ОТ САЙТА------------------------------------------------------------------------------
    public static void setThemeColorApp(Context context, String color, String color2) { // TESTING...
        if (Data.isColorHex(color)) {
            try {
                new MainActivity().toolbar.setBackgroundColor(Color.parseColor(color));
            } catch (Exception e) {}
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((Activity)context).getWindow().setStatusBarColor(Color.parseColor(color2));
                }
            } catch (Exception e) {}

//            Toast.makeText(context, color, Toast.LENGTH_SHORT).show();

            if (Data.isDarkColor(Color.parseColor(color))) {
                setDarkApp(context);
//                Toast.makeText(context, "Color dark", Toast.LENGTH_SHORT).show();
            } else {
                setLightApp(context);
//                Toast.makeText(context, "Color light", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static void setDarkApp(Context context) {
        Data.clearLightStatusBar(((Activity)context));
    }
    private static void setLightApp(Context context) {
        Data.setLightStatusBar(((Activity)context));
    }
    // ЦВЕТ ПРИЛОЖЕНИЯ ОТ САЙТА------------------------------------------------------------------------------


    // ПОИСК ПО СТРАНИЦЕ-------------------------------------------------------------------------------------
    private void openFindToPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (linMainIncludeFindToPage.getVisibility() == View.GONE) {
                    linMainIncludeFindToPage.setVisibility(View.VISIBLE);
                    appbar.setVisibility(View.GONE);
                    linBottomMenu.setVisibility(View.INVISIBLE);
                    textUrl.setVisibility(View.GONE);
                }
            }
        });
    }
    private void closeFindToPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (linMainIncludeFindToPage.getVisibility() == View.VISIBLE) {
                    linMainIncludeFindToPage.setVisibility(View.GONE);
                    appbar.setVisibility(View.VISIBLE);
                    if (shared.getBoolean("keyViewBottomMenuBar", true) == true) {
                        linBottomMenu.setVisibility(View.VISIBLE);
                    } else {
                        linBottomMenu.setVisibility(View.GONE);
                    }
                    if (shared.getBoolean("keyLinkText", true) == false) {
                        textUrl.setVisibility(View.GONE);
                    } else {
                        textUrl.setVisibility(View.VISIBLE);
                    }
                    editFind.post(new Runnable() {
                        @Override
                        public void run() {
                            editFind.setText("");
                            searchFindToPage();
                        }
                    });
                }
            }
        });
    }
    private void searchFindToPage() {
        mWeb.post(new Runnable() {
            @Override
            public void run() {
                mWeb.findAll(editFind.getText().toString());
            }
        });
        try {
            Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
            m.invoke(mWeb, true);
        } catch (Exception e) {}
    }
    // ПОИСК ПО СТРАНИЦЕ-------------------------------------------------------------------------------------



    // ФЕЙКОВОЕ ОБНОВЛЕНИЕ СТРАНИЦЫ -------------------------------------------------------------------------
    private void setFakeReloadPage() {
        menuRefresh.setImageResource(R.drawable.ic_menu_cancel);
        TooltipCompat.setTooltipText(menuRefresh, getString(R.string.tooltip_cancel_page));
        showProgress();
        mProgress.post(new Runnable() {
            @Override
            public void run() {
                mProgress.setProgress(0);
                changeTextUrl(getString(R.string.loading_page) + " " + 0 + " %");
            }
        });
        mProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress.setProgress(10);
                changeTextUrl(getString(R.string.loading_page) + " " + 10 + " %");
            }
        }, 400);
        mProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress.setProgress(40);
                changeTextUrl(getString(R.string.loading_page) + " " + 40 + " %");
            }
        }, 600);
        mProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress.setProgress(80);
                changeTextUrl(getString(R.string.loading_page) + " " + 80 + " %");
            }
        }, 1200);
        mProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress.setProgress(87);
                changeTextUrl(getString(R.string.loading_page) + " " + 87 + " %");
            }
        }, 1600);

        mWeb.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWeb.setVisibility(View.INVISIBLE);
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                menuRefresh.setImageResource(R.drawable.ic_menu_refresh);
                TooltipCompat.setTooltipText(menuRefresh, getString(R.string.tooltip_refresh_page));
                changeTextUrl(mWeb.getUrl());

                mWeb.post(new Runnable() {
                    @Override
                    public void run() {
                        mWeb.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, Data.getRandomNum(1700, 3600));
    }
    // ФЕЙКОВОЕ ОБНОВЛЕНИЕ СТРАНИЦЫ -------------------------------------------------------------------------



    // ПОЛНОЭКРАННЫЙ РЕЖИМ-------------------------------------------------------------------------------------

    // ПОЛНОЭКРАННЫЙ РЕЖИМ-------------------------------------------------------------------------------------



    // WebView client's
    private class WebClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().startsWith("http://") || request.getUrl().toString().startsWith("https://") || request.getUrl().toString().startsWith("file://") || request.getUrl().toString().startsWith("www.")) {
                view.loadUrl(request.getUrl().toString());
                changeTextUrl(request.getUrl().toString());
            } else {
                try {
                    Data.openUrl(MainActivity.this, request.getUrl().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error open custom url: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("file://") || url.startsWith("www.")) {
                view.loadUrl(url);
                changeTextUrl(url);
            } else {
                try {
                    Data.openUrl(MainActivity.this, url);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error open custom url: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            String message = getString(R.string.ssl_error);
            imageSsl.post(new Runnable() {
                @Override
                public void run() {
                    imageSsl.setImageResource(R.drawable.ic_ssl_error);
                }
            });
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = getString(R.string.ssl_error_untrusted);
                    break;
                case SslError.SSL_EXPIRED:
                    message = getString(R.string.ssl_error_expired);
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = getString(R.string.ssl_error_idmismatch);
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = getString(R.string.ssl_error_notyetvalid);
                    break;
            }
            builder.setTitle(getString(R.string.title_ssl_error));
            builder.setMessage(String.format(getString(R.string.message_ssl_error), message));
            builder.setCancelable(false);
            builder.setPositiveButton(getString(R.string.action_continue), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton(getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            final AlertDialog alert = builder.create();
            alert.show();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            if (!isReload) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // backHistoryList.add(new BackHistoryModel(mWeb.getTitle(), mWeb.getUrl(), mWeb.getFavicon(), String.valueOf(backHistoryList.size() + 1)));
                        if (shared.getBoolean("keySaveHistory", true) == true) {
                            HistoryManager.addHistory(MainActivity.this, mWeb.getUrl());
                        }
                        }
                }, 2000);
            }
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            if (mWeb.DESKTOP_VERSION) {
                mWeb.runJS(JSManager.PC_MODE);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mWeb.LOADING_PAGE = true;
            showProgress();
            editSearchChange(url);
            changeTextUrl(url);
            mWeb.checkSSL(imageSsl, url);
            try {
                if (popupMore != null) {
                    popupMore.getMenu().findItem(R.id.actionContenteditable).setChecked(false);
                    popupMore.getMenu().findItem(R.id.actionHighlightBorder).setChecked(false);
                    popupMore.getMenu().findItem(R.id.actionElementEditor).setChecked(false);
                    popupMore.getMenu().findItem(R.id.actionStyleEditor).setChecked(false);
                }
            } catch (Exception e) {}
            if (consoleList.size() > 0) {
                consoleList.clear();
            }
            menuRefresh.post(new Runnable() {
                @Override
                public void run() {
                    menuRefresh.setImageResource(R.drawable.ic_menu_cancel);
                    if (menuRefresh != null) {
                        TooltipCompat.setTooltipText(menuRefresh, getString(R.string.tooltip_cancel_page));
                    }
                }
            });

            try {
                mEditSearch.post(new Runnable() {
                    @Override
                    public void run() {
//                        mEditSearch.setFocusable(false);
                    }
                });
            } catch (Exception e) {}
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWeb.LOADING_PAGE = false;
            hideProgress();
            editSearchChange(url);
            changeTextUrl(url);
            mWeb.checkSSL(imageSsl, url);
            if (shared.getBoolean("keySaveSession", true)) {
                saveSESSION.edit().putString("session_url", url).apply();
            }
            menuRefresh.post(new Runnable() {
                @Override
                public void run() {
                    menuRefresh.setImageResource(R.drawable.ic_menu_refresh);
                    if (menuRefresh != null) {
                        TooltipCompat.setTooltipText(menuRefresh, getString(R.string.tooltip_refresh_page));
                    }
                }
            });

            mWeb.runJS(JSManager.LOG1_EI);
            mWeb.runJS(JSManager.LOG2_EI);
            mWeb.runJS(JSManager.LOG3_EI);

            if (shared.getBoolean("keyThemeColor", false)) {
                setThemeColorApp(MainActivity.this, Integer.toHexString(R.color.colorActionBar), Integer.toHexString(R.color.colorPrimaryDark));
                mWeb.runJS(JSManager.META_THEME_COLOR);
            }

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mWeb.runJS(JSManager.SYSTEM);
//                }
//            }, 2000);
        }
    }
    private class WebChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullScreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        WebChrome(){}

        @Nullable
        @Override
        public Bitmap getDefaultVideoPoster() {
            return super.getDefaultVideoPoster();
        }

        @Override
        public void onHideCustomView() {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = view;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = callback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File chooser"), FILECHOODER_RESULTCODE);
            Toast.makeText(MainActivity.this, acceptType, Toast.LENGTH_SHORT).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }

            uploadMessage = filePathCallback;

            Intent intent = fileChooserParams.createIntent();
            try {
                startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (Exception e) {
                uploadMessage = null;
                Toast.makeText(MainActivity.this, "Cannot open file chooser", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File chooser"), FILECHOODER_RESULTCODE);
            Toast.makeText(MainActivity.this, acceptType, Toast.LENGTH_SHORT).show();
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File chooser"), FILECHOODER_RESULTCODE);
        }

        @Override
        public void onProgressChanged(WebView view, final int newProgress)  {
            super.onProgressChanged(view, newProgress);
            mProgress.post(new Runnable() {
                @Override
                public void run() {
                    mProgress.setProgress(newProgress);
                }
            });
            if (newProgress > 99) {
                try {
                    changeTextUrl(view.getUrl().toString());
                } catch (Exception e) {}
            } else {
                changeTextUrl(getString(R.string.loading_page) + " " + newProgress + " %");
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            try {
                setTitle(title);
            } catch (Exception e) {}
            try {
                editSearchChange(view.getUrl());
            } catch (Exception e) {}
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            consoleList.add(new ConsoleModel(consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId(), new Long(System.currentTimeMillis()/1000).toString(), consoleMessage.messageLevel().toString()));
            return true;
        }

//        @Override
//        public boolean onJsPrompt(WebView view, String url, final String message, final String defaultValue, final JsPromptResult result) {
//            final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
//            dialog.setTitle(url);
//            View inflate = (MainActivity.this).getLayoutInflater().inflate(R.layout.alert_web_prompt, null);
//            dialog.setView(inflate);
//
//            final TextView messagePrompt = inflate.findViewById(R.id.messagePrompt);
//            final EditText contentPrompt = inflate.findViewById(R.id.contentPrompt);
//            final Button btnPromptOk = inflate.findViewById(R.id.btnPromptOk);
//            final Button btnPromptCancel = inflate.findViewById(R.id.btnPromptCancel);
//
//            messagePrompt.post(new Runnable() {
//                @Override
//                public void run() {
//                    messagePrompt.setText(message);
//                }
//            });
//            contentPrompt.post(new Runnable() {
//                @Override
//                public void run() {
//                    contentPrompt.setText(defaultValue);
//                }
//            });
//
//            btnPromptOk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    result.confirm(contentPrompt.getText().toString());
//                }
//            });
//            btnPromptCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    result.cancel();
//                }
//            });
//
//
//            dialog.show();
//            return true;
//        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 5);
            } else {
                AlertDialog alertGeolocation = new AlertDialog.Builder(MainActivity.this).create();
                alertGeolocation.setTitle(getString(R.string.title_geolocation));
                alertGeolocation.setMessage(String.format(getString(R.string.message_geolocation), origin));
                alertGeolocation.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.invoke(origin, true, true);
                    }
                });
                alertGeolocation.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.invoke(origin, false, false);
                    }
                });
                alertGeolocation.show();
            }
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mWeb.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mWeb.restoreState(savedInstanceState);
    }

    // Progress
    private void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
            }
        });
    }
    private void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.VISIBLE);
            }
        });
    }

    // Edit search
    private void editSearchChange(final String text) {
        url_host = text;
        url_path = "";
        try {
            url_host = new URL(text).getHost();
        } catch (Exception e) {
            url_host = text;
        }
        try {
            url_path = new URL(text).getPath();
        } catch (Exception e) {
            url_path = text;
        }
        url_host = url_host.replace("https://", "").replace("http://", "");
        mEditSearch.post(new Runnable() {
            @Override
            public void run() {
                mEditSearch.setText(url_host + "" + url_path);
            }
        });
    }

    // Text url
    private void changeTextUrl(final String text) {
        textUrl.post(new Runnable() {
            @Override
            public void run() {
                textUrl.setText(text);
            }
        });
    }

    // App shared settings
    private void setLoadSettings() throws Exception {
        if (shared.getBoolean("keyLinkText", true) == false) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textUrl.setVisibility(View.GONE);
                }
            });
        } else {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textUrl.setVisibility(View.VISIBLE);
                }
            });
        }


        if (shared.getBoolean("keyViewBottomMenuBar", true) == true) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    linBottomMenu.setVisibility(View.VISIBLE);
                }
            });
        } else {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    linBottomMenu.setVisibility(View.GONE);
                }
            });
        }


        if (shared.getBoolean("keyRoundedDisplay", false) == true && shared.getBoolean("keyViewBottomMenuBar", true) == false) {
            textUrl.setPadding(50, 3, 3, 7);
        } else {
            textUrl.setPadding(8, 3, 3, 8);
        }
    }
}
