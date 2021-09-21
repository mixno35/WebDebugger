package com.mixno.web_debugger;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mixno.web_debugger.adapter.BackHistoryAdapter;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.model.BackHistoryModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar2;
    private LinearLayout noContent;

    private RecyclerView listHistory;
    private BackHistoryAdapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

    public static List<BackHistoryModel> historyList = new ArrayList<>();

    private static final int REQUEST_CODE_PERMISSION = 15;

    private Menu mMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.toolbar);
        listHistory = findViewById(R.id.listHistory);
        progressBar2 = findViewById(R.id.progressBar2);
        noContent = findViewById(R.id.noContent);

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
    }

    private void loadHistory () {
        historyList.clear();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listHistory.setVisibility(View.GONE);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar2.setVisibility(View.VISIBLE);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                noContent.setVisibility(View.GONE);
            }
        });

        try {
            File historyFiles = new File(Data.PATH_HISTORY);
            File[] files = historyFiles.listFiles();

            for (int i =0; i < files.length; i++) {
                if (files[i].getAbsolutePath().endsWith(".eih")) {
                    historyList.add(new BackHistoryModel(files[i].getName(), Data.read(new File(files[i].getAbsolutePath())), files[i].lastModified()));
                }
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Collections.sort(historyList, new Comparator<BackHistoryModel>() {
                        @Override
                        public int compare(BackHistoryModel o1, BackHistoryModel o2) {
                            return Integer.compare((int)o2.getTime(), (int)o1.getTime());
                        }
                    });
                }
            }, 2000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listLayoutManager = new LinearLayoutManager(HistoryActivity.this);
                    listHistory.setLayoutManager(listLayoutManager);
                    listAdapter = new BackHistoryAdapter(historyList, HistoryActivity.this, new MainActivity().mWeb);
                    listHistory.setAdapter(listAdapter);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar2.setVisibility(View.GONE);
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listHistory.setVisibility(View.VISIBLE);
                        }
                    });

                    if (historyList.size() > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // mMenu.getItem(R.id.removeAll).setVisible(true);
                                } catch (Exception e) {}
                            }
                        });
                    }

                    setTitleUpd();

                    if (historyList.size() == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                noContent.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            }, 3000);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listHistory.setVisibility(View.GONE);
                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar2.setVisibility(View.GONE);
                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    noContent.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void setTitleUpd () {
        setTitle(getString(R.string.title_back_history) + " (" + historyList.size() + ")");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case REQUEST_CODE_PERMISSION:
                Data.createDefaultFolders(HistoryActivity.this);
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Data.setGrantPermissionsSettings(HistoryActivity.this);
                return;
            }
            ActivityCompat.requestPermissions(HistoryActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        } else {
            Data.createDefaultFolders(this);

            loadHistory();

            Snackbar.make(findViewById(R.id.content), getString(R.string.message_small_problems_storage), 2000).setAction(getString(R.string.action_help), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HistoryActivity.this, HelpActivity.class));
                }
            }).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.removeAll:
//                removeAllHistory();
                startActivity(new Intent(HistoryActivity.this, ClearDataActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removeAllHistory() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
        builder.setTitle(getString(R.string.title_clear_history));
        builder.setMessage(getString(R.string.message_clear_history));
        builder.setPositiveButton(getString(R.string.action_clear), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                historyList.clear();
                Data.deleteDF(new File(Data.PATH_HISTORY));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // mMenu.getItem(R.id.removeAll).setVisible(false);
                        } catch (Exception e) {}
                        Toast.makeText(getApplicationContext(), getString(R.string.toast_history_cleared), Toast.LENGTH_LONG).show();
                    }
                });
                loadHistory();
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
