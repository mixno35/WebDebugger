package com.mixno.web_debugger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.mixno.web_debugger.app.Data;

import java.io.File;

public class ClearDataActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private CheckBox checkboxCache, checkboxSession, checkboxCookies, checkboxHistory;
    private Button buttonGo;

    private CookieManager manager;

    private int sizeHistory = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_data);

        toolbar = findViewById(R.id.toolbar);

        checkboxCache = findViewById(R.id.checkboxCache);
        checkboxSession = findViewById(R.id.checkboxSession);
        checkboxCookies = findViewById(R.id.checkboxCookies);
        checkboxHistory = findViewById(R.id.checkboxHistory);

        buttonGo = findViewById(R.id.buttonGo);

        manager = CookieManager.getInstance();

        final File dirCache = this.getCacheDir();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        try {
            File[] files = new File(Data.PATH_HISTORY).listFiles();
            sizeHistory = files.length;
        } catch (Exception e) {}

        checkboxCache.post(new Runnable() {
            @Override
            public void run() {
                checkboxCache.append(" (" + Data.longFileSize(dirCache.getFreeSpace()) + ")");
            }
        });
        checkboxHistory.post(new Runnable() {
            @Override
            public void run() {
                checkboxHistory.append(" (" + sizeHistory + ")");
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkboxCookies.isChecked()) { // ОЧИЩАЕМ COOKIES
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manager.removeAllCookies(new ValueCallback<Boolean>() {
                            @Override
                            public void onReceiveValue(Boolean aBoolean) {
                                Toast.makeText(ClearDataActivity.this, getString(R.string.title_clear_data_cookies) + ": " + aBoolean, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        manager.removeAllCookie();
                    }
                }
                if (checkboxSession.isChecked()) { // ОЧИЩАЕМ СЕССИИ
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        manager.removeSessionCookies(new ValueCallback<Boolean>() {
                            @Override
                            public void onReceiveValue(Boolean aBoolean) {
                                Toast.makeText(ClearDataActivity.this, getString(R.string.title_clear_data_session) + ": " + aBoolean, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        manager.removeSessionCookie();
                    }
                }
                if (checkboxHistory.isChecked()) {
                    Data.deleteDF(new File(Data.PATH_HISTORY));
                    Data.toast(ClearDataActivity.this, getString(R.string.toast_history_cleared));
                }
                if (checkboxCache.isChecked()) {
                    Data.deleteDF(dirCache);
//                    finishAffinity();
//                    startActivity(new Intent(ClearDataActivity.this, MainActivity.class));
                }
                onBackPressed();
            }
        });
    }
}
