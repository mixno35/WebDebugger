package com.mixno.web_debugger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.DataAnim;

public class CacheWebViewActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Switch switch1;
    private LinearLayout otherContent;

    private RadioGroup cacheGroup;
    private RadioButton cacheDefault, cacheNoCache, cacheCacheOnly, cacheCacheElseNetwork;

    private SharedPreferences shared;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_webview);

        toolbar = findViewById(R.id.toolbar);
        switch1 = findViewById(R.id.switch1);
        otherContent = findViewById(R.id.otherContent);
        cacheGroup = findViewById(R.id.cacheGroup);
        cacheDefault = findViewById(R.id.cacheDefault);
        cacheNoCache = findViewById(R.id.cacheNoCache);
        cacheCacheOnly = findViewById(R.id.cacheCacheOnly);
        cacheCacheElseNetwork = findViewById(R.id.cacheCacheElseNetwork);

        shared = PreferenceManager.getDefaultSharedPreferences(this);

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

        cacheDefault.setOnClickListener(cacheModeClick);
        cacheNoCache.setOnClickListener(cacheModeClick);
        cacheCacheOnly.setOnClickListener(cacheModeClick);
        cacheCacheElseNetwork.setOnClickListener(cacheModeClick);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    openOtherContent(DataAnim.DURATION_ANIM);
                    switch1.setText(getString(R.string.action_ons));
                } else {
                    closeOtherContent(DataAnim.DURATION_ANIM);
                    switch1.setText(getString(R.string.action_offs));
                }

                shared.edit().putBoolean("keyCacheWebViewBool", b).apply();
            }
        });

        switch1.setChecked(shared.getBoolean("keyCacheWebViewBool", true));

        if (shared.getBoolean("keyCacheWebViewBool", true)) {
            openOtherContent(0);
        } else {
            closeOtherContent(0);
        }

        if (shared.getString("keyCacheWebViewValue", "DEFAULT").equals("DEFAULT")) {
            cacheDefault.setChecked(true);
        } if (shared.getString("keyCacheWebViewValue", "DEFAULT").equals("NO_CACHE")) {
            cacheNoCache.setChecked(true);
        } if (shared.getString("keyCacheWebViewValue", "DEFAULT").equals("CACHE_ONLY")) {
            cacheCacheOnly.setChecked(true);
        } if (shared.getString("keyCacheWebViewValue", "DEFAULT").equals("CACHE_ELSE_NETWORK")) {
            cacheCacheElseNetwork.setChecked(true);
        }
    }

    private void openOtherContent(final long duration) {
        otherContent.setVisibility(View.VISIBLE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                otherContent.animate().alpha(1.0f).setDuration(duration).start();
            }
        });
    }

    private void closeOtherContent(final long duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                otherContent.animate().alpha(0.0f).setDuration(duration).start();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                otherContent.setVisibility(View.INVISIBLE);
            }
        }, duration);
    }

    View.OnClickListener cacheModeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cacheDefault:
                    shared.edit().putString("keyCacheWebViewValue", "DEFAULT").apply();
                    break;
                case R.id.cacheNoCache:
                    shared.edit().putString("keyCacheWebViewValue", "NO_CACHE").apply();
                    break;
                case R.id.cacheCacheOnly:
                    shared.edit().putString("keyCacheWebViewValue", "CACHE_ONLY").apply();
                    break;
                case R.id.cacheCacheElseNetwork:
                    shared.edit().putString("keyCacheWebViewValue", "CACHE_ELSE_NETWORK").apply();
                    break;
                default:
                    break;
            }
        }
    };
}
