package com.mixno.web_debugger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.widget.WebEI;

public class SearchEngineOtherActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextInputEditText editUrl, editSearch;
    private Button buttonGo;

    private SharedPreferences shared;

    private String resultUrl, resultSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_engine_other);

        toolbar = findViewById(R.id.toolbar);

        editUrl = findViewById(R.id.editUrl);
        editSearch = findViewById(R.id.editSearch);
        buttonGo = findViewById(R.id.buttonGo);

        shared = PreferenceManager.getDefaultSharedPreferences(this);

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

        resultUrl = shared.getString("keySearchEngineOtherHome", "");
        if (shared.getString("keySearchEngineOtherHome", "").equals("")) {
            resultUrl = new WebEI(this).URL_HOME_GOOGLE;
        }

        resultSearch = shared.getString("keySearchEngineOtherSearch", "");
        if (shared.getString("keySearchEngineOtherSearch", "").equals("")) {
            resultSearch = new WebEI(this).URL_SEARCH_GOOGLE;
        }

        editUrl.post(new Runnable() {
            @Override
            public void run() {
                editUrl.setText(resultUrl);
            }
        });
        editSearch.post(new Runnable() {
            @Override
            public void run() {
                editSearch.setText(resultSearch);
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editUrl.getText().toString().trim();
                String search = editSearch.getText().toString().trim();

                if (url.isEmpty()) {
                    editUrl.setError(getString(R.string.hint_error_search_engine_other_empty));
                    return;
                } if (search.isEmpty()) {
                    editSearch.setError(getString(R.string.hint_error_search_engine_other_empty));
                    return;
                } if (!search.contains("%20")) {
                    editSearch.setError(getString(R.string.hint_error_search_engine_other_search_20));
                    return;
                } if (!Data.isValidURL(url)) {
                    editUrl.setError(getString(R.string.hint_error_search_engine_other_url_invalid));
                    return;
                } if (!Data.isValidURL(search)) {
                    editSearch.setError(getString(R.string.hint_error_search_engine_other_url_invalid));
                    return;
                }

                shared.edit().putString("keySearchEngineOtherHome", url).apply();
                shared.edit().putString("keySearchEngineOtherSearch", search).apply();

                Data.toast(SearchEngineOtherActivity.this, getString(R.string.toast_search_engine_other_success));
                onBackPressed();
            }
        });
    }
}
