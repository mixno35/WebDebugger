package com.mixno.web_debugger.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.DataAnim;
import com.mixno.web_debugger.app.TabManager;
import com.mixno.web_debugger.model.TabModel;
import com.mixno.web_debugger.widget.WebEI;

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.TabsHolder> {

    private ArrayList<TabModel> list;
    private Context context;

    public TabsAdapter(ArrayList<TabModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TabsAdapter.TabsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tab, parent, false);
        TabsHolder holder = new TabsHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TabsAdapter.TabsHolder holder, final int position) {
        final TabModel model = list.get(position);

        DataAnim.setFadeAnimation(holder.itemView);

        holder.textUrl.post(new Runnable() {
            @Override
            public void run() {
                holder.textUrl.setText(model.getUrl());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabManager.openTab(context, model.getTitle());
            }
        });
        holder.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTab(position);
            }
        });

        if (holder.webView != null) {
            holder.webView.post(new Runnable() {
                @Override
                public void run() {
                    holder.webView.loadUrl(model.getUrl());
                    holder.webView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                    holder.webView.setWebChromeClient(new WebChromeClient(){
                        @Override
                        public void onReceivedIcon(WebView view, final Bitmap icon) {
                            holder.imageFavicon.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.imageFavicon.setImageBitmap(icon);
                                }
                            });
                        }
                        @Override
                        public void onReceivedTitle(WebView view, final String title) {
                            holder.textTitle.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.textTitle.setText(title);
                                }
                            });
                        }
                        @Override
                        public void onProgressChanged(WebView view, int newProgress) {
                            if (newProgress >= 100) {
                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        holder.progressBar.setVisibility(View.GONE);
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TabsHolder extends RecyclerView.ViewHolder {
        protected TextView textTitle;
        protected TextView textUrl;
        protected ImageView imageFavicon;
        protected ImageView imageClose;
        protected WebEI webView;
        protected ProgressBar progressBar;

        public TabsHolder(View item) {
            super(item);
            textTitle = item.findViewById(R.id.textViewTitle);
            textUrl = item.findViewById(R.id.textViewUrl);
            imageFavicon = item.findViewById(R.id.imageView);
            imageClose = item.findViewById(R.id.imageViewClose);
            webView = item.findViewById(R.id.webView);
            progressBar = item.findViewById(R.id.progressBar3);
        }
    }

    private void removeTab(int position) {
        final TabModel model = list.get(position);
        Data.deleteDF(new File(Data.PATH_TABS + File.separator + model.getTitle()));
        list.remove(position);
        notifyDataSetChanged();
    }
}



