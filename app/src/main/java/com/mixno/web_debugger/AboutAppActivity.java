package com.mixno.web_debugger;

import android.content.Intent;
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
    private Button buttonSourceCode, buttonSupportProject;

    private ImageView actionVK, actionTELEGRAM, actionINSTAGRAM, actionYOUTUBE;

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
        buttonSupportProject = findViewById(R.id.buttonSupportProject);
        textAppVersion = findViewById(R.id.textAppVersion);

        actionVK = findViewById(R.id.actionVK);
        actionTELEGRAM = findViewById(R.id.actionTELEGRAM);
        actionINSTAGRAM = findViewById(R.id.actionINSTAGRAM);
        actionYOUTUBE = findViewById(R.id.actionYOUTUBE);

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

        actionVK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Data.openUrl(AboutAppActivity.this, "https://vk.com/mixno35");
                } catch (Exception e) {}
            }
        });
        actionTELEGRAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Data.openUrl(AboutAppActivity.this, "https://t.me/mixno35");
                } catch (Exception e) {}
            }
        });
        actionINSTAGRAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Data.openUrl(AboutAppActivity.this, "https://instagram.com/mixno35");
                } catch (Exception e) {}
            }
        });
        actionYOUTUBE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Data.openUrl(AboutAppActivity.this, "https://youtube.com/channel/UCjtgBPQ3Vzx2qEubvD_mOTQ");
                } catch (Exception e) {}
            }
        });

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

        buttonSupportProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutAppActivity.this, DonateActivity.class));
            }
        });

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
    }
}