package com.mixno.web_debugger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.DataAnim;
import com.mixno.web_debugger.widget.WebEI;

public class SearchEngineOtherActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextInputEditText editUrl, editSearch;
    private Button buttonGo;
    private LinearLayout layoutOther;

    private SharedPreferences shared;

    private String resultUrl, resultSearch;

    private RadioButton searchEngineGoogle, searchEngineYandex, searchEngineBing, searchEngineYahoo, searchEngineDuck, searchEngineOther;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_engine_other);

        toolbar = findViewById(R.id.toolbar);

        editUrl = findViewById(R.id.editUrl);
        editSearch = findViewById(R.id.editSearch);
        buttonGo = findViewById(R.id.buttonGo);
        layoutOther = findViewById(R.id.layoutOther);

        searchEngineGoogle = findViewById(R.id.searchEngineGoogle);
        searchEngineYandex = findViewById(R.id.searchEngineYandex);
        searchEngineBing = findViewById(R.id.searchEngineBing);
        searchEngineYahoo = findViewById(R.id.searchEngineYahoo);
        searchEngineDuck = findViewById(R.id.searchEngineDuck);
        searchEngineOther = findViewById(R.id.searchEngineOther);

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

        searchEngineGoogle.setOnClickListener(searchEngineClick);
        searchEngineYandex.setOnClickListener(searchEngineClick);
        searchEngineBing.setOnClickListener(searchEngineClick);
        searchEngineYahoo.setOnClickListener(searchEngineClick);
        searchEngineDuck.setOnClickListener(searchEngineClick);
        searchEngineOther.setOnClickListener(searchEngineClick);

        unselectedOther();

        if (shared.getString("keySearchEngine", "0").equals("0")) {
            searchEngineGoogle.setChecked(true);
        } if (shared.getString("keySearchEngine", "0").equals("1")) {
            searchEngineYandex.setChecked(true);
        } if (shared.getString("keySearchEngine", "0").equals("2")) {
            searchEngineBing.setChecked(true);
        } if (shared.getString("keySearchEngine", "0").equals("3")) {
            searchEngineYahoo.setChecked(true);
        } if (shared.getString("keySearchEngine", "0").equals("4")) {
            searchEngineDuck.setChecked(true);
        } if (shared.getString("keySearchEngine", "0").equals("99")) {
            searchEngineOther.setChecked(true);
            selectedOther();
        }

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

    private void selectedOther() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutOther.setEnabled(true);
                editUrl.setEnabled(true);
                editSearch.setEnabled(true);
                buttonGo.setEnabled(true);

                layoutOther.animate().alpha(1.0f).setDuration(DataAnim.DURATION_ANIM).start();
            }
        });
    }
    private void unselectedOther() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutOther.setEnabled(false);
                editUrl.setEnabled(false);
                editSearch.setEnabled(false);
                buttonGo.setEnabled(false);

                layoutOther.animate().alpha(0f).setDuration(DataAnim.DURATION_ANIM).start();
            }
        });
    }

    View.OnClickListener searchEngineClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.searchEngineOther) {
                selectedOther();
            } else {
                unselectedOther();
            }

            switch (view.getId()) {
                case R.id.searchEngineGoogle:
                    shared.edit().putString("keySearchEngine", "0").apply();
                    break;
                case R.id.searchEngineYandex:
                    shared.edit().putString("keySearchEngine", "1").apply();
                    break;
                case R.id.searchEngineBing:
                    shared.edit().putString("keySearchEngine", "2").apply();
                    break;
                case R.id.searchEngineYahoo:
                    shared.edit().putString("keySearchEngine", "3").apply();
                    break;
                case R.id.searchEngineDuck:
                    shared.edit().putString("keySearchEngine", "4").apply();
                    break;
                case R.id.searchEngineOther:
                    shared.edit().putString("keySearchEngine", "99").apply();
                    break;
                default:
                    break;
            }
        }
    };
}
