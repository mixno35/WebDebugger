package com.mixno.web_debugger.fragment.dev_tools;

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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ElementsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
            Document doc = Jsoup.connect(new MainActivity().mWeb.getUrl()).timeout(10000).get();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error parse Elements", Toast.LENGTH_SHORT).show();
        }

        return inflater.inflate(R.layout.fragment_dev_tools_elements, container, false);
    }
}