package com.mixno.web_debugger.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;

import java.io.File;

public class CreateJavaScriptDialog {

    public CreateJavaScriptDialog(final Context context, final String script) {
        final AlertDialog dialog = new AlertDialog.Builder(((Activity)context)).create();
        View inflate = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_create_js, null);
        dialog.setView(inflate);
        dialog.setCancelable(false);
        dialog.setTitle(context.getString(R.string.action_create_javascript));

        final EditText id = inflate.findViewById(R.id.editID);
        final EditText name = inflate.findViewById(R.id.editNAME);
        final EditText description = inflate.findViewById(R.id.editDESCRIPTION);
        final EditText source = inflate.findViewById(R.id.editSOURCE);
        final EditText version = inflate.findViewById(R.id.editVERSION);
        final EditText versionCode = inflate.findViewById(R.id.editVERSIONCODE);

        final Button btnCreate = inflate.findViewById(R.id.btnCREATE);
        final Button btnCancel = inflate.findViewById(R.id.btnCANCEL);


        id.post(new Runnable() {
            @Override
            public void run() {
                id.setText("1");
            }
        });
        name.post(new Runnable() {
            @Override
            public void run() {
                name.setText("Source");
            }
        });
        description.post(new Runnable() {
            @Override
            public void run() {
                description.setText("Description source");
            }
        });
        source.post(new Runnable() {
            @Override
            public void run() {
                source.setText("alert('Hello World!');");
            }
        });
        version.post(new Runnable() {
            @Override
            public void run() {
                version.setText("1.0.0");
            }
        });
        versionCode.post(new Runnable() {
            @Override
            public void run() {
                versionCode.setText("1");
            }
        });

        if (!script.trim().isEmpty()) {
            source.post(new Runnable() {
                @Override
                public void run() {
                    source.setText(script);
                }
            });
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.getText().toString().trim().isEmpty()) {
                    id.setError("Error ID. Please check value ID");
                    return;
                } if (name.getText().toString().trim().isEmpty()) {
                    name.setError("Error NAME. Please check value NAME");
                    return;
                } if (source.getText().toString().trim().isEmpty()) {
                    source.setError("Error SOURCE. Please check value SOURCE");
                    return;
                } if (version.getText().toString().trim().isEmpty()) {
                    version.setError("Error VERSION. Please check value VERSION");
                    return;
                } if (versionCode.getText().toString().trim().isEmpty()) {
                    versionCode.setError("Error VERSION CODE. Please check value VERSION CODE");
                    return;
                }
                try {
                    if (new File(Data.PATH_ADDONS + File.separator + name.getText().toString()).isDirectory()) {
                        Toast.makeText(context, "This NAME is used! Please, enter other NAME.", Toast.LENGTH_SHORT).show();
                    } else {
                        Data.createDirectory(Data.PATH_ADDONS + File.separator + name.getText().toString());
                        if (Data.createFile(Data.PATH_ADDONS + File.separator + name.getText().toString() + File.separator + "manifest.json")) {
                            String manifest = "{id:"+id.getText().toString()+", name: \""+name.getText().toString()+"\", description: \""+description.getText().toString()+"\", icon: \"\", main_source: \"script.js\", version: \""+version.getText().toString()+"\", version_code: "+versionCode.getText().toString()+"}";
                            if (Data.write(new File(Data.PATH_ADDONS + File.separator + name.getText().toString() + File.separator + "manifest.json"), manifest)) {
                                if (Data.createFile(Data.PATH_ADDONS + File.separator + name.getText().toString() + File.separator + "script.js")) {
                                    if (Data.write(new File(Data.PATH_ADDONS + File.separator + name.getText().toString() + File.separator + "script.js"), source.getText().toString())) {
                                        dialog.dismiss();
                                        AddonsDialog.dialog.dismiss();
                                        new AddonsDialog(context, null);
                                        Toast.makeText(context, "New JS source added!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Error write content to file \"script.js\"", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "Error create file \"script.js\"", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Error write content to file \"manifest.json\"", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Error create file \"manifest.json\"", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Error exception make JS: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}
