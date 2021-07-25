package com.mixno.web_debugger;

import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mixno.web_debugger.adapter.AboutAppAdapter;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.model.AboutAppModel;

import java.util.ArrayList;

public class AboutAppActivity extends AppCompatActivity {

    // private Toolbar toolbar;
    private ImageView imageIcon;
    private TextView textAppName, textAppVersion;
    private Button buttonSourceCode;
    private RecyclerView listAboutApp;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    private ArrayList<AboutAppModel> list = new ArrayList<>();

    private PackageInfo pInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        // Id's
        // toolbar = findViewById(R.id.toolbar);
        imageIcon = findViewById(R.id.imageApp);
        textAppName = findViewById(R.id.textAppName);
        buttonSourceCode = findViewById(R.id.buttonSourceCode);
        textAppVersion = findViewById(R.id.textAppVersion);
        listAboutApp = findViewById(R.id.listAboutApp);

        try {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Exception e) {}

        /* if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } */

        buttonSourceCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Data.openUrl(AboutAppActivity.this, "https://github.com/mixno35/WebDebugger");
                } catch (Exception e) {
                    Toast.makeText(AboutAppActivity.this, "Error open link: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (imageIcon != null) {
            imageIcon.setImageResource(R.mipmap.ic_launcher);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageIcon.setClipToOutline(true);
                }
            } catch (Exception e) {}
        }
        if (textAppName != null) {
            textAppName.post(new Runnable() {
                @Override
                public void run() {
                    textAppName.setText(getString(R.string.app_name_full));
                }
            });
        }
        if (textAppVersion != null) {
            textAppVersion.post(new Runnable() {
                @Override
                public void run() {
                    textAppVersion.setText(pInfo.versionName + " (" + pInfo.versionCode + ")");
                }
            });
        }

        list.add(new AboutAppModel("WebMoney - WMZ", "Z178194978131", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("Payeer", "P1048066896", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("Bitcoin", "3BchmRqDfSxXH7rXaMdUoEkt87UsFTPKc9", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel(getString(R.string.title_say_thanks), "Telegram", "https://t.me/mixno35", true, AboutAppModel.TYPE_LINK));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listLayoutManager = new LinearLayoutManager(getApplicationContext());
                listAboutApp.setLayoutManager(listLayoutManager);
                listAdapter = new AboutAppAdapter(list, getApplicationContext());
                listAboutApp.setAdapter(listAdapter);
            }
        }, 300);
    }
}