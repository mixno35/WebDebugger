package com.mixno.web_debugger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LocalhostActivity extends AppCompatActivity {

    private Switch switchEnable;
    private TextView textDesc;

    private SharedPreferences sharedLocalhost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localhost);
        switchEnable = findViewById(R.id.switch1);
        textDesc = findViewById(R.id.textDesc);

        sharedLocalhost = getSharedPreferences("kLocalhost", Context.MODE_PRIVATE);

        textDesc.post(new Runnable() {
            @Override
            public void run() {
                textDesc.setText(getString(R.string.message_warning_localhost) + "\n\n" + getString(R.string.message_localhost));
                textDesc.setMaxLines((int)2);
            }
        });

        textDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDesc.post(new Runnable() {
                    @Override
                    public void run() {
                        if (textDesc.getMaxLines() == 2) {
                            textDesc.setMaxLines((int)20);
                        } else {
                            textDesc.setMaxLines((int)2);
                        }
                    }
                });
            }
        });

        switchEnable.post(new Runnable() {
            @Override
            public void run() {
                switchEnable.setChecked(sharedLocalhost.getBoolean("kLocalhost", false));

                if (switchEnable.isChecked()) {
                    switchEnable.setText(getString(R.string.action_ons));
                } else {
                    switchEnable.setText(getString(R.string.action_offs));
                }
            }
        });

       switchEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   switchEnable.setText(getString(R.string.action_ons));
               } else {
                   switchEnable.setText(getString(R.string.action_offs));
               }
               sharedLocalhost.edit().putBoolean("kLocalhost", isChecked).apply();
           }
       });
    }
}
