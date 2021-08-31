package com.mixno.web_debugger.fragment.addons;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OnlineFragment extends Fragment {

    private ProgressBar progressBar;

    private TextView listAddons;

    public String txt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_addons, container, false);

        listAddons = view.findViewById(R.id.message);
        progressBar = view.findViewById(R.id.progressBar2);

        if (Data.isNetworkConnected(getActivity())) {
            ReadFileTask task = new ReadFileTask();
            task.execute(Data.URL_STORE_ADDONS_FILE);
        } else {
            Data.toast(getActivity(), getString(R.string.toast_no_connect_internet));
        }

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

        return view;
    }

    private class ReadFileTask extends AsyncTask<String, Integer, Void> {

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
            listAddons.post(new Runnable() {
                @Override
                public void run() {
                    listAddons.setText(txt);
                }
            });
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
        }
    }
}
