package com.mixno.web_debugger.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.mixno.web_debugger.CookieManagerActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.model.UserAgentModel;

public class UserAgentNewDialog {

    private SharedPreferences otherUserAgent;
    private AlertDialog dialog;

    public UserAgentNewDialog(final Context context)  {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        final View view = inflater.inflate(R.layout.alert_useragent_new, null);

        otherUserAgent = context.getSharedPreferences("kOtherUserAgent", Context.MODE_PRIVATE);

        final TextInputEditText editValue = view.findViewById(R.id.editValue);
        final Button buttonGo = view.findViewById(R.id.buttonGo);

        editValue.post(new Runnable() {
            @Override
            public void run() {
                editValue.setText(otherUserAgent.getString("kOtherUserAgent", ""));
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editValue.getText().toString().trim().isEmpty() || editValue.getText().toString().length() < 10) {
                    editValue.setError("Please enter value or length < 10");
                    return;
                }
                otherUserAgent.edit().putString("kOtherUserAgent", editValue.getText().toString()).apply();
                dialog.dismiss();
                try {
                    UserAgentDialog.list.add(new UserAgentModel(UserAgentDialog.drDevice, context.getString(R.string.title_useragent_new), editValue.getText().toString(), false));
                } catch (Exception e) {}
                try {
                    UserAgentDialog.listAdapter.notifyDataSetChanged();
                } catch (Exception e) {}
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.action_useragent_new));
        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }
}
