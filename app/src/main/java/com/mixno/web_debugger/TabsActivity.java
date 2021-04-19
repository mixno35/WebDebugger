package com.mixno.web_debugger;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mixno.web_debugger.adapter.TabsAdapter;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.TabManager;
import com.mixno.web_debugger.model.TabModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TabsActivity extends AppCompatActivity {

    private ImageView actionAddTab;
    private ImageView actionMore;

    private PopupMenu popupMore;

    private RecyclerView listTabs;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

    public static ArrayList<TabModel> tabList = new ArrayList<>();

    private static final int REQUEST_CODE_PERMISSION = 15;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        actionAddTab = findViewById(R.id.actionAddTab);
        actionMore = findViewById(R.id.actionMore);
        listTabs = findViewById(R.id.listTabs);

        popupMore = new PopupMenu(TabsActivity.this, actionMore);
        popupMore.inflate(R.menu.tabs_more);
        popupMore.setGravity(Gravity.END);
        popupMore.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.actionNewTab) {
                    setCreateNewTab();
                }
                if (item.getItemId() == R.id.actionRemoveAllTabs) {
                    removeAllTabs();
                }
                return true;
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final TabModel model = tabList.get(position);
                Data.deleteDF(new File(Data.PATH_TABS + File.separator + model.getTitle()));
                tabList.remove(position);
                listAdapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(listTabs);

        if (actionAddTab != null) {
            TooltipCompat.setTooltipText(actionAddTab, getString(R.string.tooltip_new_tab));
        }

        actionAddTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCreateNewTab();
            }
        });
        actionMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMore.show();
            }
        });

        tabList.clear();

        try {
            File tabFiles = new File(Data.PATH_TABS);
            File[] files = tabFiles.listFiles();

            for (int i =0; i < files.length; i++) {
                if (files[i].getAbsolutePath().endsWith(".eit")) {
                    tabList.add(new TabModel(files[i].getName(), TabManager.readTab(TabsActivity.this, files[i].getName()), files[i].lastModified()));
                }
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Collections.sort(tabList, new Comparator<TabModel>() {
                        @Override
                        public int compare(TabModel o1, TabModel o2) {
                            return Integer.compare((int)o2.getTime(), (int)o1.getTime());
                        }
                    });
                }
            }, 200);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listLayoutManager = new LinearLayoutManager(TabsActivity.this);
                    listTabs.setLayoutManager(listLayoutManager);
                    listAdapter = new TabsAdapter(tabList, TabsActivity.this);
                    listTabs.setAdapter(listAdapter);

                }
            }, 300);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case REQUEST_CODE_PERMISSION:
                Data.createDefaultFolders(TabsActivity.this);
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Data.setGrantPermissionsSettings(TabsActivity.this);
                return;
            }
            ActivityCompat.requestPermissions(TabsActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        } else {
            Data.createDefaultFolders(this);
            Snackbar.make(findViewById(R.id.content), getString(R.string.message_small_problems_storage), 2000).setAction(getString(R.string.action_help), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(TabsActivity.this, HelpActivity.class));
                }
            }).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private void setCreateNewTab() {
        try {
            String ID_TAB = TabManager.newTab(getApplicationContext(), MainActivity.mWeb.getUrlHome());
            TabManager.openTab(TabsActivity.this, ID_TAB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeAllTabs() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(TabsActivity.this);
        builder.setTitle(getString(R.string.title_remove_all_tabs));
        builder.setMessage(getString(R.string.message_remove_all_tabs));
        builder.setPositiveButton(getString(R.string.action_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tabList.clear();
                Data.deleteDF(new File(Data.PATH_TABS));
                listTabs.setAdapter(listAdapter);
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
