package com.mixno.web_debugger.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.webkit.WebViewFeature;

import com.mixno.web_debugger.AboutAppActivity;
import com.mixno.web_debugger.FeedbackActivity;
import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;

public class SettingsFragment extends PreferenceFragment {

    private SharedPreferences sharedLocalhost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        sharedLocalhost = getActivity().getSharedPreferences("kLocalhost", Context.MODE_PRIVATE);

        Preference keyAboutApp = findPreference("keyAboutApp");
        Preference keyTheme = findPreference("keyTheme");
        Preference keyGrantPermissions = findPreference("keyGrantPermissions");
        Preference keyThemeWebView = findPreference("keyThemeWebView");
//        Preference keyHistory = findPreference("keyHistory");
        Preference keyFeedback = findPreference("keyFeedback");
//        Preference keyRoundedDisplay = findPreference("keyRoundedDisplay");
//        Preference keyLocalhost = findPreference("keyLocalhost");

//        keyRoundedDisplay.setEnabled(false);

        keyAboutApp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().startActivity(new Intent(getActivity(), AboutAppActivity.class));
                return false;
            }
        });
        keyGrantPermissions.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Data.setGrantPermissionsSettings(getActivity());
                return false;
            }
        });
//        keyHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                getActivity().startActivity(new Intent(getActivity(), HistoryActivity.class));
//                return false;
//            }
//        });
        keyFeedback.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().startActivity(new Intent(getActivity(), FeedbackActivity.class));
                return false;
            }
        });
//        keyLocalhost.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                getActivity().startActivity(new Intent(getActivity(), LocalhostActivity.class));
//                return false;
//            }
//        });
        keyTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                alertThemeApplied();
                return true;
            }
        });
        keyThemeWebView.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                alertThemeApplied();
                return true;
            }
        });

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            keyTheme.setEnabled(false);
            keyTheme.setSummary(getString(R.string.message_not_supported));
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            keyGrantPermissions.setEnabled(false);
            keyGrantPermissions.setSummary(getString(R.string.message_not_supported));
        }

        if (!WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            keyThemeWebView.setEnabled(false);
            keyThemeWebView.setSummary(getString(R.string.message_not_supported));
        }

//        if (sharedLocalhost.getBoolean("kLocalhost", false)) {
//            keyLocalhost.setSummary(getString(R.string.action_ons));
//        } else {
//            keyLocalhost.setSummary(getString(R.string.action_offs));
//        }
    }

    private void alertThemeApplied() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(((Activity)getActivity()));
        builder.setTitle(getString(R.string.title_applied_theme));
        builder.setMessage(getString(R.string.message_applied_theme));
        builder.setPositiveButton(getString(R.string.action_restart), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity)getActivity()).finishAffinity();
                ((Activity)getActivity()).startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        builder.setNegativeButton(getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
