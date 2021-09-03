package com.mixno.web_debugger;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mixno.web_debugger.adapter.AboutAppAdapter;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.model.AboutAppModel;

import java.util.ArrayList;

public class DonateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView listDonate;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    private ArrayList<AboutAppModel> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        // Id's
        toolbar = findViewById(R.id.toolbar);
        listDonate = findViewById(R.id.listDonate);

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

        list.add(new AboutAppModel("WebMoney - WMZ", "Z178194978131", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("WebMoney - WME", "E721558707620", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("Payeer", "P1048066896", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("Bitcoin", "3BchmRqDfSxXH7rXaMdUoEkt87UsFTPKc9", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("LiteCoin", "MEtdG9ZYFhH9JhdBZor4cDLNJJcdNhohiV", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel("Ripple", "rshvnxLDE9Jsm8sJxPxct425HhQC2tk5CV", null, true, AboutAppModel.TYPE_COPY));
        list.add(new AboutAppModel(getString(R.string.title_say_thanks), "Telegram", "https://t.me/mixno35", true, AboutAppModel.TYPE_LINK));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listLayoutManager = new LinearLayoutManager(getApplicationContext());
                listDonate.setLayoutManager(listLayoutManager);
                listAdapter = new AboutAppAdapter(list, getApplicationContext());
                listDonate.setAdapter(listAdapter);
            }
        }, 300);
    }
}