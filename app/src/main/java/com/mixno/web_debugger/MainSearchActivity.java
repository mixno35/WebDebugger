package com.mixno.web_debugger;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mixno.web_debugger.app.Data;

public class MainSearchActivity extends AppCompatActivity {

    private ImageView imageFavicon;
    private EditText mEditSearch;
    private TextView textTitle, textUrl;

    private ImageView imageClear;

    private ImageView actionEdit, actionCopy, actionShare;

    private String title, url;
    private Bitmap favicon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        imageFavicon = findViewById(R.id.imageFavicon);
        mEditSearch = findViewById(R.id.edit_search);
        textTitle = findViewById(R.id.titleSite);
        textUrl = findViewById(R.id.urlSite);

        imageClear = findViewById(R.id.imageClear);

        actionEdit = findViewById(R.id.actionEditUrl);
        actionCopy = findViewById(R.id.actionCopyUrl);
        actionShare = findViewById(R.id.actionShareUrl);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageClear.setVisibility(View.GONE);
            }
        });

        mEditSearch.post(new Runnable() {
            @Override
            public void run() {
                if (mEditSearch.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                }
            }
        });

        mEditSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mEditSearch.getText().toString().length() == 0) {
                    if (hasFocus) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    }
                }
            }
        });

        try {
            title = getIntent().getStringExtra("title");
        } catch (Exception e) {}
        try {
            url = getIntent().getStringExtra("url");
        } catch (Exception e) {}
        try {
            // favicon = Data.getStringToBitmap(getIntent().getStringExtra("favicon"));
        } catch (Exception e) {}

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handler = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    MainActivity.mWeb.runWeb(mEditSearch.getText().toString());
                    onBackPressed();
                    handler = true;
                }
                return handler;
            }
        });
        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageClear.setVisibility(View.GONE);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageClear.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageClear.setVisibility(View.GONE);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageClear.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textTitle.post(new Runnable() {
            @Override
            public void run() {
                textTitle.setText(title);
            }
        });
        textUrl.post(new Runnable() {
            @Override
            public void run() {
                textUrl.setText(url);
            }
        });

        /* imageFavicon.post(new Runnable() {
            @Override
            public void run() {
                imageFavicon.setImageBitmap(favicon);
            }
        }); */

        imageClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditSearch.post(new Runnable() {
                    @Override
                    public void run() {
                        mEditSearch.setText("");
                    }
                });
            }
        });

        actionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditSearch.post(new Runnable() {
                    @Override
                    public void run() {
                        mEditSearch.setText(url);
                        mEditSearch.setSelection(mEditSearch.getText().length());
                    }
                });
            }
        });
        actionCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.clipboard(MainSearchActivity.this, url, true);
            }
        });
        actionShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.shareText(MainSearchActivity.this, url);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
