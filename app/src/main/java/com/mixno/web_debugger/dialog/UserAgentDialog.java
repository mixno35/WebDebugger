package com.mixno.web_debugger.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mixno.web_debugger.R;
import com.mixno.web_debugger.adapter.UserAgentAdapter;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.model.UserAgentModel;
import com.mixno.web_debugger.web.UserAgent;
import com.mixno.web_debugger.widget.WebEI;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class UserAgentDialog {

    public static BottomSheetDialog dialog;

    private ImageView ivClose;
    private Button buttonCreate;

    private RecyclerView listUA;
    public static RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;

    public static List<UserAgentModel> list = new ArrayList<>();

    private SharedPreferences otherUserAgent;

    public static int drDevice = R.drawable.ic_smartphone_black_24dp;

    public UserAgentDialog(final Context context, final WebEI viewWeb) {
        dialog = new BottomSheetDialog(context);
        View inflate = ((Activity)context).getLayoutInflater().inflate(R.layout.alert_useragent, null);
        dialog.setContentView(inflate);
        dialog.setCancelable(false);
        listUA = inflate.findViewById(R.id.recyclerView);
        ivClose = inflate.findViewById(R.id.ivClose);
        buttonCreate = inflate.findViewById(R.id.buttonCreate);

        otherUserAgent = context.getSharedPreferences("kOtherUserAgent", Context.MODE_PRIVATE);

        if (Data.isTablet(context)) {
            drDevice = R.drawable.ic_tablet_android_black_24dp;
        } else {
            drDevice = R.drawable.ic_phone_android_black_24dp;
        }

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UserAgentNewDialog(context);
            }
        });

        if (dialog != null) {
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            list.clear();
            try {
                list.add(new UserAgentModel(drDevice, "Android " + Build.VERSION.RELEASE + " " + context.getString(R.string.app_name) + " - " + Build.MODEL, UserAgent.MY_PHONE, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_android_black_24dp, context.getString(R.string.user_agent_galaxy_nexus), UserAgent.GALAXY_NEXUS, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_android_black_24dp, context.getString(R.string.user_agent_nexus_s), UserAgent.NEXUS_S, false));
                list.add(new UserAgentModel(R.drawable.ic_smartphone_black_24dp, context.getString(R.string.user_agent_bb110), UserAgent.BB110, false));
                list.add(new UserAgentModel(R.drawable.ic_tablet_black_24dp, context.getString(R.string.user_agent_playbook2_1), UserAgent.PLAYBOOK_2_1, false));
                list.add(new UserAgentModel(R.drawable.ic_smartphone_black_24dp, context.getString(R.string.user_agent_9900), UserAgent.BB9900, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_android_black_24dp, context.getString(R.string.user_agent_chrome_android_mobile), UserAgent.CHROME_ANDROID_MOBILE, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_android_black_24dp, context.getString(R.string.user_agent_chrome_android_mobile_high_end), UserAgent.CHROME_ANDROID_MOBILE_HIGH_END, false));
                list.add(new UserAgentModel(R.drawable.ic_tablet_android_black_24dp, context.getString(R.string.user_agent_chrome_android_tablet), UserAgent.CHROME_ANDROID_TABLET, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_iphone_black_24dp, context.getString(R.string.user_agent_chrome_iphone), UserAgent.CHROME_IPHONE, false));
                list.add(new UserAgentModel(R.drawable.ic_tablet_mac_black_24dp, context.getString(R.string.user_agent_chrome_ipad), UserAgent.CHROME_IPAD, false));
                list.add(new UserAgentModel(R.drawable.ic_laptop_chromebook_black_24dp, context.getString(R.string.user_agent_chrome_chrome_os), UserAgent.CHROME_CHROME_OS, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_mac_black_24dp, context.getString(R.string.user_agent_chrome_mac), UserAgent.CHROME_MAC, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_chrome_windows), UserAgent.CHROME_WINDOWS, true));
                list.add(new UserAgentModel(R.drawable.ic_phone_android_black_24dp, context.getString(R.string.user_agent_firefox_android_mobile), UserAgent.FIREFOX_ANDROID_MOBILE, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_android_black_24dp, context.getString(R.string.user_agent_firefox_android_tablet), UserAgent.FIREFOX_ANDROID_TABLET, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_iphone_black_24dp, context.getString(R.string.user_agent_firefox_iphone), UserAgent.FIREFOX_IPHONE, false));
                list.add(new UserAgentModel(R.drawable.ic_tablet_mac_black_24dp, context.getString(R.string.user_agent_firefox_ipad), UserAgent.FIREFOX_IPAD, false));
                list.add(new UserAgentModel(R.drawable.ic_desktop_mac_black_24dp, context.getString(R.string.user_agent_firefox_mac), UserAgent.FIREFOX_MAC, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_firefox_windows), UserAgent.FIREFOX_WINDOWS, true));
                list.add(new UserAgentModel(R.drawable.ic_laptop_chromebook_black_24dp, context.getString(R.string.user_agent_googlebot), UserAgent.GOOGLEBOT, true));
                list.add(new UserAgentModel(R.drawable.ic_laptop_chromebook_black_24dp, context.getString(R.string.user_agent_googlebot_desktop), UserAgent.GOOGLEBOT_DESKTOP, true));
                list.add(new UserAgentModel(R.drawable.ic_smartphone_black_24dp, context.getString(R.string.user_agent_googlebot_smartphone), UserAgent.GOOGLEBOT_SMARTPHONE, false));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_ie11), UserAgent.IE11, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_ie10), UserAgent.IE10, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_ie9), UserAgent.IE9, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_ie8), UserAgent.IE8, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_ie7), UserAgent.IE7, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_edge_chromium_windows), UserAgent.EDGE_CHROMIUM_WINDOWS, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_mac_black_24dp, context.getString(R.string.user_agent_edge_chromium_mac), UserAgent.EDGE_CHROMIUM_MAC, true));
                list.add(new UserAgentModel(R.drawable.ic_phone_iphone_black_24dp, context.getString(R.string.user_agent_edge_iphone), UserAgent.EDGE_IPHONE, false));
                list.add(new UserAgentModel(R.drawable.ic_tablet_mac_black_24dp, context.getString(R.string.user_agent_edge_ipad), UserAgent.EDGE_IPAD, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_android_black_24dp, context.getString(R.string.user_agent_edge_android_mobile), UserAgent.EDGE_ANDROID_MOBILE, false));
                list.add(new UserAgentModel(R.drawable.ic_tablet_android_black_24dp, context.getString(R.string.user_agent_edge_android_tablet), UserAgent.EDGE_ANDROID_TABLET, false));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_edge_html_windows), UserAgent.EDGE_HTML_WINDOWS, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_edge_html_xbox), UserAgent.EDGE_HTML_XBOX, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_mac_black_24dp, context.getString(R.string.user_agent_opera_mac), UserAgent.OPERA_MAC, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_opera_windows), UserAgent.OPERA_WINDOWS, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_mac_black_24dp, context.getString(R.string.user_agent_opera_presto_mac), UserAgent.OPERA_PRESTO_MAC, true));
                list.add(new UserAgentModel(R.drawable.ic_desktop_windows_black_24dp, context.getString(R.string.user_agent_opera_presto_windows), UserAgent.OPERA_PRESTO_WINDOWS, true));
                list.add(new UserAgentModel(R.drawable.ic_phone_android_black_24dp, context.getString(R.string.user_agent_opera_m_android_mobile), UserAgent.OPERA_M_ANDROID_MOBILE, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_iphone_black_24dp, context.getString(R.string.user_agent_opera_m_ios), UserAgent.OPERA_M_IOS, false));
                list.add(new UserAgentModel(R.drawable.ic_tablet_mac_black_24dp, context.getString(R.string.user_agent_safari_ipad), UserAgent.SAFARI_IPAD, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_iphone_black_24dp, context.getString(R.string.user_agent_safari_iphone), UserAgent.SAFARI_IPHONE, false));
                list.add(new UserAgentModel(R.drawable.ic_desktop_mac_black_24dp, context.getString(R.string.user_agent_safari_mac), UserAgent.SAFARI_MAC, true));
                list.add(new UserAgentModel(R.drawable.ic_phone_android_black_24dp, context.getString(R.string.user_agent_uc_android_mobile), UserAgent.UC_ANDROID_MOBILE, false));
                list.add(new UserAgentModel(R.drawable.ic_phone_iphone_black_24dp, context.getString(R.string.user_agent_uc_ios), UserAgent.UC_IOS, false));
                list.add(new UserAgentModel(R.drawable.ic_smartphone_black_24dp, context.getString(R.string.user_agent_uc_windows_phone), UserAgent.UC_WINDOWS_PHONE, false));

                if (otherUserAgent.getString("kOtherUserAgent", "").length() > 10) {
                    list.add(new UserAgentModel(drDevice, context.getString(R.string.title_useragent_new), otherUserAgent.getString("kOtherUserAgent", ""), false));
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listLayoutManager = new LinearLayoutManager(context);
                        listUA.setLayoutManager(listLayoutManager);
                        listAdapter = new UserAgentAdapter(list, context);
                        listUA.setAdapter(listAdapter);
                    }
                }, 300);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if (dialog != null) {
            dialog.show();
        }
    }
}
