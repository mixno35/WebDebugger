package com.mixno.web_debugger.fragment.addons;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.adapter.AddonsAdapter;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.JSManager;
import com.mixno.web_debugger.model.AddonsModel;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptsFragment extends Fragment {

    private ProgressBar progressBar;
    private LinearLayout noContent;

    private RecyclerView listAddons;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    public static List<AddonsModel> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scripts_addons, container, false);

        progressBar = view.findViewById(R.id.progressBar2);
        listAddons = view.findViewById(R.id.listRecycler);
        noContent = view.findViewById(R.id.noContent);

        listUP();

        return view;
    }

    private void listUP() {
        list.clear();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listAddons.setVisibility(View.GONE);
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                noContent.setVisibility(View.GONE);
            }
        });

        try {
            File addonsFiles = new File(Data.PATH_ADDONS);
            File[] files = addonsFiles.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    if (new File(files[i].getAbsolutePath() + File.separator + "manifest.json").isFile()) {
                        String addonManifest = Data.read(new File(files[i].getAbsolutePath() + File.separator + "manifest.json"));
                        JSONObject obj = new JSONObject(addonManifest);

                        if (Data.isJSONValid(addonManifest)) {
                            list.add(new AddonsModel(obj.getInt("id"), obj.getString("name"), obj.getString("description"), files[i].getAbsolutePath(), "", obj.getString("main_source"), obj.getString("version"), obj.getInt("version_code"), true, addonManifest, files[i].lastModified(), false));
                        }
                    }
                }
            }
        } catch (Exception e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listAddons.setVisibility(View.GONE);
                }
            });
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    noContent.setVisibility(View.VISIBLE);
                }
            });
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listLayoutManager = new LinearLayoutManager(getActivity());
                listAddons.setLayoutManager(listLayoutManager);
                listAdapter = new AddonsAdapter(list, getActivity(), new MainActivity().mWeb);
                listAddons.setAdapter(listAdapter);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listAddons.setVisibility(View.VISIBLE);
                    }
                });
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        noContent.setVisibility(View.GONE);
                    }
                });

                if (list.size() == 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listAddons.setVisibility(View.GONE);
                        }
                    });
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            noContent.setVisibility(View.VISIBLE);
                        }
                    });
                }


            }
        }, 800);
    }
}
