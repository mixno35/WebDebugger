package com.mixno.web_debugger;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.mixno.web_debugger.adapter.DevToolsAdapter;
import com.mixno.web_debugger.fragment.dev_tools.ElementsFragment;
import com.mixno.web_debugger.fragment.dev_tools.SourceFragment;
import com.google.android.material.tabs.TabLayout;

public class DevToolsActivity extends AppCompatActivity {

    private DevToolsAdapter adapter;

    private ImageView ivClose, ivSend;
    private TextView title;
    private TabLayout tabs;
    private ViewPager pager;
    private LinearLayout content, load;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_tools);

        ivClose = findViewById(R.id.ivClose);
        ivSend = findViewById(R.id.ivSend);
        title = findViewById(R.id.title);
        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);
        content = findViewById(R.id.content);
        load = findViewById(R.id.load);

        if (load != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            load.setVisibility(View.GONE);
                        }
                    }, 4000);
                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adapter = new DevToolsAdapter(getSupportFragmentManager());

            pager.beginFakeDrag();
            pager.setOffscreenPageLimit(1);

            adapter.addFragment(new ElementsFragment(), "Elements");
            adapter.addFragment(new SourceFragment(), "Source");

            pager.setAdapter(adapter);
            tabs.setupWithViewPager(pager);

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            ivSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendChanges();
                }
            });

            try {
                title.post(new Runnable() {
                    @Override
                    public void run() {
                        title.setText(getString(R.string.action_dev_tools) + " - " + new MainActivity().mWeb.getUrl());
                    }
                });
            } catch (Exception e) {}
        } else {
            Toast.makeText(DevToolsActivity.this, "Your device not supporting. Support Android SDK " + Build.VERSION_CODES.M + "+ (Android 6.0+)", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendChanges() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DevToolsActivity.this);
        builder.setTitle(getString(R.string.title_confirm_action));
        builder.setMessage(getString(R.string.message_confirm_action_send_source_to_home_screen));
        builder.setPositiveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
