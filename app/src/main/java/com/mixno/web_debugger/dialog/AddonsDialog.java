package com.mixno.web_debugger.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.adapter.AddonsAdapter;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.JSManager;
import com.mixno.web_debugger.model.AddonsModel;
import com.mixno.web_debugger.widget.WebEI;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddonsDialog {

    public static BottomSheetDialog dialog;

    private ProgressBar progressBar;

    private ImageView ivClose;
    private Button buttonCreate, buttonStore, buttonImport;

    private RecyclerView listAddons;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    public static List<AddonsModel> list = new ArrayList<>();

    public AddonsDialog(final Context context, final WebEI viewWeb) {
        dialog = new BottomSheetDialog(context);
        View inflate = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_addons, null);
        dialog.setContentView(inflate);
        dialog.setCancelable(false);
        progressBar = inflate.findViewById(R.id.progressBar);
        listAddons = inflate.findViewById(R.id.recyclerView);
        ivClose = inflate.findViewById(R.id.ivClose);
        buttonCreate = inflate.findViewById(R.id.buttonCreate);
        buttonImport = inflate.findViewById(R.id.buttonImport);
        buttonStore = inflate.findViewById(R.id.buttonStore);


        if (dialog != null) {
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buttonCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (Data.isInstallPackage(context, "com.doctorsteep.ide.web")) {
//                        try {
//                            context.startActivity(new Intent(context, Class.forName("com.mixno.ide.web.MainActivity")));
//                        } catch (Exception e) {
//                            Toast.makeText(context, "Error open class for make JavaScript!", Toast.LENGTH_SHORT).show();
//                            try {
//                                Data.openUrl(context, "https://play.google.com/store/apps/details?id=com.doctorsteep.ide.web");
//                            } catch (Exception e2) {
//                                Toast.makeText(context, "Error open link!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    } else {
//                        try {
//                            Data.openUrl(context, "https://play.google.com/store/apps/details?id=com.doctorsteep.ide.web");
//                        } catch (Exception e) {
//                            Toast.makeText(context, "Error open link!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
                    new CreateJavaScriptDialog(context, "");
                }
            });

            try {

                listUP(context);

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Collections.sort(list, new Comparator<AddonsModel>() {
                            @Override
                            public int compare(AddonsModel o1, AddonsModel o2) {
                                return Integer.compare(Integer.valueOf(o1.getId()), Integer.valueOf(o2.getId()));
                            }
                        });
                    }
                }, 400);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listLayoutManager = new LinearLayoutManager(context);
                        listAddons.setLayoutManager(listLayoutManager);
                        listAdapter = new AddonsAdapter(list, context, new MainActivity().mWeb);
                        listAddons.setAdapter(listAdapter);
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });

                        if (list.size() <= 0) {
                            Toast.makeText(context, context.getString(R.string.toast_no_addons), Toast.LENGTH_LONG).show();
                        }
                    }
                }, 800);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if (dialog != null) {
            dialog.show();
        }
    }

    public static void listUP(Context context) throws Exception {
        list.clear();

        list.add(new AddonsModel(0, context.getString(R.string.action_source_page), "Edit and show full source page", "", "", JSManager.SOURCE_V2, "2.0.0", 2, true, "", 0));
        list.add(new AddonsModel(0, context.getString(R.string.action_element_editor), "Edit and show code page element", "", "", JSManager.ELEMENT_EDITOR, "1.0.0", 1, true, "", 0));
        list.add(new AddonsModel(0, context.getString(R.string.action_console_eruda), "Show console ERUDA", "", "", JSManager.ERUDA_EI, "1.0.0", 1, true, "", 0));
        list.add(new AddonsModel(0, context.getString(R.string.action_console_firebug), "Show console Firefox - (FireBug)", "", "", JSManager.FIREBUG_EI, "1.0.0.beta", 1, true, "", 0));

        File addonsFiles = new File(Data.PATH_ADDONS);
        File[] files = addonsFiles.listFiles();
        for (int i =0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                if (new File(files[i].getAbsolutePath() + File.separator + "manifest.json").isFile()) {
                    String addonManifest = Data.read(new File(files[i].getAbsolutePath() + File.separator + "manifest.json"));
                    JSONObject obj = new JSONObject(addonManifest);

                    if (Data.isJSONValid(addonManifest)) {
                        list.add(new AddonsModel(obj.getInt("id"), obj.getString("name"), obj.getString("description"), files[i].getAbsolutePath(), "", obj.getString("main_source"), obj.getString("version"), obj.getInt("version_code"), true, addonManifest, files[i].lastModified()));
                    }
                }
            }
        }
    }
}
