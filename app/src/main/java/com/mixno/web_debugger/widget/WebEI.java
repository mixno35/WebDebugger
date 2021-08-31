package com.mixno.web_debugger.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;

import java.util.Locale;

public class WebEI extends WebView {

    public String URL_HOME_GOOGLE = "https://google.com";
    public String URL_SEARCH_GOOGLE = "https://google.com/search?q=%20";

    public String URL_HOME_YANDEX = "https://yandex.com";
    public String URL_SEARCH_YANDEX = "https://yandex.com/search/touch/?text=%20";

    public String URL_HOME_DSS = "https://search.doctorsteep.ru";
    public String URL_SEARCH_DSS = "https://search.doctorsteep.ru?s=%20";

    public String URL_HOME_BING = "https://bing.com";
    public String URL_SEARCH_BING = "https://bing.com/search?q=%20";

    public String URL_HOME_BAIDU = "https://baidu.com";
    public String URL_SEARCH_BAIDU = "https://baidu.com/from=884b/s?word=%20";

    public String URL_HOME_YAHOO = "https://search.yahoo.com";
    public String URL_SEARCH_YAHOO = "https://search.yahoo.com/search?p=%20";

    public String URL_HOME_SHENMA = "https://m.sm.cn";
    public String URL_SEARCH_SHENMA = "https://m.sm.cn/s?q=%20";

    public String URL_HOME_DUCKDUCKGO = "https://duckduckgo.com";
    public String URL_SEARCH_DUCKDUCKGO = "https://duckduckgo.com/?q=%20";


    public boolean DESKTOP_VERSION = false;
    public boolean LOADING_PAGE = true;

    private SharedPreferences shared;

    public String USERAGENT = "Mozilla/5.0 (Linux; U; Android " + Build.VERSION.RELEASE + "; " + Locale.getDefault().getLanguage() + "; " + Build.MODEL + " Build/" + Build.DISPLAY + ") AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/70.0.3538.110 Mobile Safari/537.36";
    public String USERAGENT_PC = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";

    public WebEI(Context context) {
        super(context);
        init();
    }
    public WebEI(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        shared = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.getSettings().setAllowFileAccess(shared.getBoolean("keyFileUrls", true));
        this.getSettings().setAllowContentAccess(shared.getBoolean("keyFileUrls", true));
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setDisplayZoomControls(false);
        this.getSettings().setBuiltInZoomControls(true);
        this.getSettings().setUserAgentString(USERAGENT);
        this.getSettings().setSerifFontFamily("sans-serif");
        this.getSettings().setSavePassword(true);
        this.getSettings().setSaveFormData(true);
        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setLoadWithOverviewMode(true);
        this.getSettings().setPluginState(WebSettings.PluginState.ON);
        this.getSettings().setMediaPlaybackRequiresUserGesture(true);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setAppCacheEnabled(true);
        this.getSettings().setDatabaseEnabled(true);
        this.getSettings().setGeolocationEnabled(true);
        this.getSettings().setGeolocationDatabasePath(getContext().getFilesDir().getPath());
        this.getSettings().setAllowFileAccessFromFileURLs(true);
        this.getSettings().setAllowUniversalAccessFromFileURLs(true);
        this.setWebContentsDebuggingEnabled(shared.getBoolean("keyDebugMode", false));
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        }
        CookieManager.getInstance().setAcceptCookie(true);
        switch (shared.getString("keyThemeWebView", "0")) {
            case "0": // System
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                        WebSettingsCompat.setForceDark(this.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
                    } else {
                        WebSettingsCompat.setForceDark(this.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
                    }
                }
                break;
            case "1": // Light
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(this.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
                }
                break;
            case "2": // Night
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(this.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
                }
                break;
        }
    }

    public void runWeb(final String text) {
        switch (shared.getString("keyOptionSearch", "0")) {
            case "0": // New
                try {
                    if (URLUtil.isValidUrl(text) || text.startsWith("file://") || text.startsWith("javascript:") || text.startsWith("www.") || text.startsWith("content://") || text.startsWith("view-source:")) {
                        this.post(new Runnable() {
                            @Override
                            public void run() {
                                loadUrl(text);
                            }
                        });
                    } else if (Patterns.WEB_URL.matcher(text).matches()) {
                        this.post(new Runnable() {
                            @Override
                            public void run() {
                                loadUrl("http://" + text);
                            }
                        });
                    } else {
                        this.post(new Runnable() {
                            @Override
                            public void run() {
                                String go = getUrlSearch();
                                go = go.replace("%20", text);
                                loadUrl(go);
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), getContext().getString(R.string.toast_not_supported), Toast.LENGTH_SHORT).show();
                }
                break;
            case "1": // Old
                try {
                    if (text.startsWith("https://") || text.startsWith("http://") || text.startsWith("file://") || text.startsWith("javascript:") || text.startsWith("www.") || text.startsWith("content://") || text.startsWith("view-source:")) {
                        this.post(new Runnable() {
                            @Override
                            public void run() {
                                loadUrl(text);
                            }
                        });
                    } else {
                        this.post(new Runnable() {
                            @Override
                            public void run() {
                                String go = getUrlSearch();
                                go = go.replace("%20", text);
                                loadUrl(go);
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), getContext().getString(R.string.toast_not_supported), Toast.LENGTH_SHORT).show();
                }
                break;
            default: // Old
                try {
                    if (text.startsWith("https://") || text.startsWith("http://") || text.startsWith("file://") || text.startsWith("javascript:") || text.startsWith("www.") || text.startsWith("content://") || text.startsWith("view-source:")) {
                        this.post(new Runnable() {
                            @Override
                            public void run() {
                                loadUrl(text);
                            }
                        });
                    } else {
                        this.post(new Runnable() {
                            @Override
                            public void run() {
                                String go = getUrlSearch();
                                go = go.replace("%20", text);
                                loadUrl(go);
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), getContext().getString(R.string.toast_not_supported), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (URLUtil.isValidUrl(text)) {
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        loadUrl(text);
                    }
                });
            } else if (Patterns.WEB_URL.matcher(text).matches()) {
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        loadUrl("http://" + text);
                    }
                });
            } else {
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        String go = getUrlSearch();
                        go = go.replaceAll("%20", text);
                        loadUrl(go);
                    }
                });
            }
        } else {
            if (text.startsWith("https://") || text.startsWith("http://") || text.startsWith("file://") || text.startsWith("javascript:") || text.startsWith("www.") || text.startsWith("content://") || text.startsWith("view-source:")) {
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        loadUrl(text);
                    }
                });
            } else {
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        String go = getUrlSearch();
                        go = go.replaceAll("%20", text);
                        loadUrl(go);
                    }
                });
            }
        } */

        /* if (text.startsWith("https://") || text.startsWith("http://") || text.startsWith("file://") || text.startsWith("javascript:") || text.startsWith("www.") || text.startsWith("content://") || text.startsWith("view-source:")) {
            this.post(new Runnable() {
                @Override
                public void run() {
                    loadUrl(text);
                }
            });
        } else {
            this.post(new Runnable() {
                @Override
                public void run() {
                    String go = getUrlSearch();
                    go = go.replaceAll("%20", text);
                    loadUrl(go);
                }
            });
        } */

        /* if (Data.isValidURL(text)) {
            this.post(new Runnable() {
                @Override
                public void run() {
                    loadUrl(text);
                }
            });
        } else {
            this.post(new Runnable() {
                @Override
                public void run() {
                    String go = getUrlSearch();
                    go = go.replaceAll("%20", text);
                    loadUrl(go);
                }
            });
        } */
    }
    public void runHome() {
        this.post(new Runnable() {
            @Override
            public void run() {
                loadUrl(getUrlHome());
                clearHistory();
            }
        });
    }
    public void runJavascript(EditText view) {
        String rAll = view.getText().toString().replaceAll(";$", "");
        String js = "javascript:%s%s%s";
        Object[] oArr = new Object[3];

        oArr[0] = rAll.startsWith("console.") ? "" : "console.log(";
        oArr[1] = rAll;
        oArr[2] = rAll.startsWith("console.") ? "" : ");";
        runWeb(String.format(js, oArr));
        view.setText("");
    }
    public void runJS(final EditText view) {
        if (view.getText().toString().startsWith("javascript:")) {
            this.post(new Runnable() {
                @Override
                public void run() {
                    loadUrl(view.getText().toString());
                }
            });
        } else {
            this.post(new Runnable() {
                @Override
                public void run() {
                    evaluateJavascript(view.getText().toString(), null);
                }
            });
        }

        view.post(new Runnable() {
            @Override
            public void run() {
                view.setText("");
            }
        });
    }
    public void runJS(final String source) {
        if (source.startsWith("javascript:")) {
            loadUrl(source);
        } else {
            this.evaluateJavascript(source, null);
        }
    }

    public String getUrlHome() {
        String result = URL_HOME_GOOGLE;
        switch (shared.getString("keySearchEngine", "0")) {
            case "0":
                result = URL_HOME_GOOGLE;
                break;
            case "1":
                result = URL_HOME_YANDEX;
                break;
            case "2":
                result = URL_HOME_BING;
                break;
            case "3":
                result = URL_HOME_YAHOO;
                break;
            case "4":
                result = URL_HOME_DUCKDUCKGO;
                break;
            case "99":
                if (shared.getString("keySearchEngineOtherHome", URL_HOME_GOOGLE).trim().equals("")) {
                    result = URL_HOME_GOOGLE;
                } else {
                    result = shared.getString("keySearchEngineOtherHome", URL_HOME_GOOGLE);
                }
                break;
        }
        return result;
    }
    public String getUrlSearch() {
        String result = URL_SEARCH_GOOGLE;
        switch (shared.getString("keySearchEngine", "0")) {
            case "0":
                result = URL_SEARCH_GOOGLE;
                break;
            case "1":
                result = URL_SEARCH_YANDEX;
                break;
            case "2":
                result = URL_SEARCH_BING;
                break;
            case "3":
                result = URL_SEARCH_YAHOO;
                break;
            case "4":
                result = URL_SEARCH_DUCKDUCKGO;
                break;
            case "99":
                if (shared.getString("keySearchEngineOtherSearch", URL_SEARCH_GOOGLE).trim().equals("")) {
                    result = URL_SEARCH_GOOGLE;
                } else {
                    result = shared.getString("keySearchEngineOtherSearch", URL_SEARCH_GOOGLE);
                }
                break;
        }
        return result;
    }

    public boolean isLoading() {
        return LOADING_PAGE;
    }

    public void checkSSL(ImageView view, String url) {
        if (url.startsWith("https://")) {
            view.setImageResource(R.drawable.ic_ssl_ok);
        } else {
            view.setImageResource(R.drawable.ic_ssl_error);
        }
    }
    public boolean checkSSL(String url) {
        if (url.startsWith("https://")) {
            return true;
        } else {
            return false;
        }
    }

    public void setDesktopVersion(boolean mode) {
        DESKTOP_VERSION = mode;
        if (mode) {
            this.getSettings().setUserAgentString(USERAGENT_PC);
        } else {
            this.getSettings().setUserAgentString(USERAGENT);
        }
        this.reload();
    }
    public void setDesktopMode(boolean mode) {
        DESKTOP_VERSION = mode;
        String newUserAgent = this.getSettings().getUserAgentString();
        if (mode) {
            try {
                String ua = this.getSettings().getUserAgentString();
                String androidOS = this.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
                newUserAgent = this.getSettings().getUserAgentString().replace(androidOS, "(X11; Linux x86_64)");
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            newUserAgent = null;
        }

        this.getSettings().setUserAgentString(newUserAgent);
        this.getSettings().setUseWideViewPort(mode);
        this.getSettings().setLoadWithOverviewMode(mode);
        if (shared.getBoolean("keyNewVersionPC", false)) {
            this.loadUrl(this.getUrl().replace("m.", "").replace("mobile.", ""));
        } else {
            this.reload();
        }
    }
    public void setChangeUserAgent(String userAgent, boolean desktop) {
        this.USERAGENT = userAgent;
        this.getSettings().setUserAgentString(userAgent);
        setDesktopMode(desktop);
        this.reload();
        try {
            new MainActivity().popupMore.getMenu().findItem(R.id.actionDesktopVersion).setChecked(desktop);
        } catch (Exception E) {}
    }
    public boolean isDesktopVersion() {
        return DESKTOP_VERSION;
    }
}
