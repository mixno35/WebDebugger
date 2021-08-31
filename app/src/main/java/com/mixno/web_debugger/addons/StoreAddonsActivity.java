package com.mixno.web_debugger.addons;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.adapter.addons.StoreAddonsAdapter;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.fragment.addons.OnlineFragment;
import com.mixno.web_debugger.fragment.addons.ScriptsFragment;

public class StoreAddonsActivity extends AppCompatActivity {

    private StoreAddonsAdapter adapter;

    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_addons);

        toolbar = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);

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

        adapter = new StoreAddonsAdapter(getSupportFragmentManager());

        pager.setOffscreenPageLimit(0);

        adapter.addFragment(new OnlineFragment(), getString(R.string.action_javascript_store_online));
        adapter.addFragment(new ScriptsFragment(), getString(R.string.action_javascript_store_installed));

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
    }
}
