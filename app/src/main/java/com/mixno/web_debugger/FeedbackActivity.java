package com.mixno.web_debugger;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.EmailManager;
import com.google.android.material.textfield.TextInputEditText;

public class FeedbackActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText editMessage, editEmail;
    private Button buttonSend, buttonHelp;

    private String EMAIL_FEEDBACK = "steepdoctor@gmail.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Data.theme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        // Id's
        toolbar = findViewById(R.id.toolbar);
        editMessage = findViewById(R.id.editMessage);
        editEmail = findViewById(R.id.editEmail);
        buttonSend = findViewById(R.id.buttonSendFeedback);
        buttonHelp = findViewById(R.id.buttonHelp);

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

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMessage.getText().length() > 20) {
                    // ok
                    if (EmailManager.isEmailValid(editEmail.getText().toString())) {
                        // ok
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_FEEDBACK});
                        emailIntent.putExtra(Intent.EXTRA_TEXT, editMessage.getText().toString());
                        emailIntent.setType("message/rfc822");
                        try {
                            startActivity(Intent.createChooser(emailIntent, getString(R.string.feedback_choose_email_client)));
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(getApplicationContext(), getString(R.string.feedback_error_send), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // error
                        editEmail.setError(getString(R.string.feedback_email_invalid));
                    }
                } else {
                    // error
                    editMessage.setError(getString(R.string.feedback_message_error));
                }
            }
        });

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackActivity.this, HelpActivity.class));
            }
        });
    }
}
