package com.mixno.web_debugger.fragment.dev_tools;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.widget.WebEICode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SourceFragment extends Fragment {

    private Document doc;
    private WebEICode source;
    private String url;
    public static String SOURCE_RESTORE = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dev_tools_source, container, false);

        source = view.findViewById(R.id.sourceCode);

        try {
            url = new MainActivity().mWeb.getUrl();
        } catch (Exception e) {}

        try {
            new DataSourceJSOUP().execute();
        } catch (Exception e) {}

        return view;
    }

    private class DataSourceJSOUP extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                doc = Jsoup.connect(url).timeout(10000).get();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error parse Source", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (doc != null) {
                source.post(new Runnable() {
                    @Override
                    public void run() {
                        source.loadCode(doc.outerHtml());
                    }
                });
                SOURCE_RESTORE = doc.outerHtml();
            } else {
                source.post(new Runnable() {
                    @Override
                    public void run() {
                        source.loadCode("Fail parse");
                    }
                });
            }
        }
    }
}
