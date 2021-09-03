package com.mixno.web_debugger;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public static boolean RUNNED_HISTORY = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Id's
        toolbar = findViewById(R.id.toolbar);

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
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.frameSettings, new SettingsFragment()).commit();
        }
    }

    @Override
    protected void onStart() {
        if (RUNNED_HISTORY) {
            RUNNED_HISTORY = false;
            onBackPressed();
        }
        super.onStart();
    }
}
