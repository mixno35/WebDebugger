package com.mixno.web_debugger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.EditSearchManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WhoisActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText textWhois, mEditSearch;
    private ProgressBar progressBar;

    private String txt = "";
    private String url = "";
    private String host = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whois);
        // Id's
        toolbar = findViewById(R.id.toolbar);
        textWhois = findViewById(R.id.textWhois);
        progressBar = findViewById(R.id.progressBar2);
        mEditSearch = findViewById(R.id.edit_search);

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

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handler = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    loadWhois(mEditSearch.getText().toString());
                    handler = true;
                }
                return handler;
            }
        });

//        Toast.makeText(this, Data.getRootDomain(url), Toast.LENGTH_SHORT).show();

        try {
            url = getIntent().getStringExtra("url");
            loadWhois(url);
        } catch (Exception e) {}
    }

    private void loadWhois(String urlUp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textWhois.setVisibility(View.GONE);
            }
        });

        try {
            host = new URL(urlUp).getHost();
        } catch (Exception e) {}

        txt = "";
        url = host;

        if (Data.isNetworkConnected(getApplicationContext())) {
            ReadWhoisFile task = new ReadWhoisFile();
            task.execute(Data.URL_WHOIS + host);
        } else {
            Data.toast(getApplicationContext(), getString(R.string.toast_no_connect_internet));
        }
    }

    private class ReadWhoisFile extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url;
            try {
                url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    txt += line;
                }

                bufferedReader.close();
            } catch (Exception e) {

            }

            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            textWhois.post(new Runnable() {
                @Override
                public void run() {
                    textWhois.setText(Html.fromHtml(txt));
                }
            });
            mEditSearch.post(new Runnable() {
                @Override
                public void run() {
                    mEditSearch.setText(url);
                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textWhois.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}