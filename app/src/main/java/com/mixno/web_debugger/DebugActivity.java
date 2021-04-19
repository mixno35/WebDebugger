package com.mixno.web_debugger;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DebugActivity extends Activity {

    String[] exceptionType = {
            "StringIndexOutOfBoundsException",
            "IndexOutOfBoundsException",
            "ArithmeticException",
            "NumberFormatException",
            "ActivityNotFoundException"

    };

    String[] errMessage= {
            "Invalid string operation\n",
            "Invalid list operation\n",
            "Invalid arithmetical operation\n",
            "Invalid toNumber block operation\n",
            "Invalid intent operation"
    };

    private Button buttonClose, buttonSend;
    private TextView textDebugError;

    String deviceInfo = "";

    String errMsg, madeErrMsg;

    private String EMAIL_FEEDBACK[] = {"steepdoctor@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        buttonClose = findViewById(R.id.buttonClose);
        buttonSend = findViewById(R.id.buttonSend);
        textDebugError = findViewById(R.id.textDebugError);

        buttonSend.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    buttonSend.setTooltipText(getString(R.string.tooltip_send_app_debug));
                }
            }
        });

        try {
            deviceInfo = "SDK: " + Build.VERSION.SDK_INT + "\n";
        } catch (Exception e) {}
        try {
            deviceInfo = "ANDROID OS: " + Build.VERSION.RELEASE + "\n";
        } catch (Exception e) {}
        try {
            deviceInfo = "BRAND: " + Build.BRAND + "\n";
        } catch (Exception e) {}
        try {
            deviceInfo = "DEVICE: " + Build.MANUFACTURER + " | " + Build.MODEL + "\n";
        } catch (Exception e) {}


        Intent intent = getIntent();
        errMsg = "";
        madeErrMsg = "";
        if(intent != null){
            errMsg = intent.getStringExtra("error");

            String[] spilt = errMsg.split("\n");
            //errMsg = spilt[0];
            try {
                for (int j = 0; j < exceptionType.length; j++) {
                    if (spilt[0].contains(exceptionType[j])) {
                        madeErrMsg = errMessage[j];

                        int addIndex = spilt[0].indexOf(exceptionType[j]) + exceptionType[j].length();

                        madeErrMsg += spilt[0].substring(addIndex, spilt[0].length());
                        break;

                    }
                }

                if(madeErrMsg.isEmpty()) madeErrMsg = errMsg;
            }catch(Exception e){}

        }

        textDebugError.post(new Runnable() {
            @Override
            public void run() {
                textDebugError.append(deviceInfo);
                textDebugError.append("\n\n-----------------------\n\n");
                textDebugError.append(errMsg);
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, EMAIL_FEEDBACK);
                emailIntent.putExtra(Intent.EXTRA_TEXT, textDebugError.getText().toString());
                emailIntent.setType("message/rfc822");
                try {
                    startActivity(Intent.createChooser(emailIntent, getString(R.string.feedback_choose_email_client)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.feedback_error_send), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
