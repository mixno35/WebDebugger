package com.mixno.web_debugger;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.mixno.web_debugger.adapter.CookieManagerAdapter;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.model.CookieManagerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CookieManagerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar2;
    private LinearLayout noContent;

    private RecyclerView listRecycler;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    private String url = null;

    public static List<CookieManagerModel> list = new ArrayList<>();

    private CookieManager manager;

    private AlertDialog dialogSetCookie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie_manager);

        toolbar = findViewById(R.id.toolbar);
        listRecycler = findViewById(R.id.listRecycler);
        progressBar2 = findViewById(R.id.progressBar2);
        noContent = findViewById(R.id.noContent);

        url = MainActivity.mWeb.getUrl();

        try {
            url = getIntent().getStringExtra("url");
        } catch (Exception e) {}

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setSubtitle("> " + url);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        manager = CookieManager.getInstance();

        try {
            loadCookies();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listRecycler.setVisibility(View.GONE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cookie_manager_more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newCookie:
                setCookieVoid("", "", url);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadCookies() throws Exception {
        list.clear();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listRecycler.setVisibility(View.GONE);
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

        String cookies = manager.getCookie(url);

        String[] temp = cookies.split(";");

        for (String result : temp) {
            String[] temp1 = result.split("=");
            list.add(new CookieManagerModel(0, temp1[0].trim(), temp1[1].trim(), result, url));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setTitle(getString(R.string.title_cookie_manager) + " (" + list.size() + ")");

                listLayoutManager = new LinearLayoutManager(CookieManagerActivity.this);
                listRecycler.setLayoutManager(listLayoutManager);
                listAdapter = new CookieManagerAdapter(list, CookieManagerActivity.this, new MainActivity().mWeb);
                listRecycler.setAdapter(listAdapter);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar2.setVisibility(View.GONE);
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listRecycler.setVisibility(View.VISIBLE);
                    }
                });

                if (list.size() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            noContent.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        }, 200);
    }

    public void setCookieVoid(final String name, final String value, final String urlBase) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        final View view = inflater.inflate(R.layout.alert_cookie_set, null);

        final TextInputEditText editName = view.findViewById(R.id.editName);
        final TextInputEditText editValue = view.findViewById(R.id.editValue);
        final Button buttonGo = view.findViewById(R.id.buttonGo);

        editName.post(new Runnable() {
            @Override
            public void run() {
                editName.setText(name);
            }
        });
        editValue.post(new Runnable() {
            @Override
            public void run() {
                editValue.setText(value);
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cookieString = editName.getText()+"="+editValue.getText()+"; path=/";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    manager.setCookie(urlBase, cookieString, new ValueCallback<Boolean>() {
                        @Override
                        public void onReceiveValue(Boolean aBoolean) {
                            if (aBoolean) {
                                dialogSetCookie.dismiss();
                            }
                        }
                    });
                } else {
                    manager.setCookie(urlBase, cookieString);
                    dialogSetCookie.dismiss();
                }
                try {
                    loadCookies();
                } catch (Exception e) {
                    Toast.makeText(CookieManagerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.action_cookie_new));
        builder.setView(view);

        dialogSetCookie = builder.create();
        dialogSetCookie.show();
    }
}
