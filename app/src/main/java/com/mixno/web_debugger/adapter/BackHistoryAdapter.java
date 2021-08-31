package com.mixno.web_debugger.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Handler;

import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.app.DataAnim;
import com.mixno.web_debugger.model.BackHistoryModel;
import com.mixno.web_debugger.widget.WebEI;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import static com.mixno.web_debugger.app.Data.PATH_HISTORY;

public class BackHistoryAdapter extends RecyclerView.Adapter<BackHistoryAdapter.BackHistoryHolder> {

    private List<BackHistoryModel> list;
    private Context context;
    private WebEI web;

    public BackHistoryAdapter(List<BackHistoryModel> list, Context context, WebEI web) {
        this.list = list;
        this.context = context;
        this.web = web;
    }

    @Override
    public BackHistoryAdapter.BackHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_back_history, parent, false);
        BackHistoryHolder holder = new BackHistoryHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BackHistoryAdapter.BackHistoryHolder holder, final int position) {
        final BackHistoryModel model = list.get(position);

        final String url = model.getUrl();
        final String name = model.getName();
        String host = "";
        try {
            host = new URL(model.getUrl()).getHost();
        } catch (Exception e) {
            host = "";
        }

        final String finalHost = host;

        holder.iconBitmap.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Picasso.with(context)
                            .load("https://www.google.com/s2/favicons?sz=64&domain_url="+ finalHost)
                            .placeholder(R.drawable.ic_public_placeholder)
                            .error(R.drawable.ic_public_placeholder)
                            .into(holder.iconBitmap);
                } catch (Exception e) {}
            }
        });

        holder.textTitle.post(new Runnable() {
            @Override
            public void run() {
                if (!model.getUrl().equals("")) {
                    holder.textTitle.setText(url);
                }
            }
        });

        // DataAnim.setFadeAnimation(holder.itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl(url);
                ((Activity)context).finish();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alertMenu(name, url, position, holder.itemView);
                return false;
            }
        });
//
//        try {
//            holder.iconBitmap.post(new Runnable() {
//                @Override
//                public void run() {
//                    holder.iconBitmap.setImageBitmap(model.getIcon());
//                }
//            });
//        } catch (Exception e) {
//            holder.iconBitmap.post(new Runnable() {
//                @Override
//                public void run() {
//                    holder.iconBitmap.setImageResource(R.drawable.ic_favicon_default);
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BackHistoryHolder extends RecyclerView.ViewHolder {
        protected TextView textTitle;
        protected ImageView iconBitmap;

        public BackHistoryHolder(View item) {
            super(item);
            textTitle = item.findViewById(R.id.textTitle);
            iconBitmap = item.findViewById(R.id.iconBitmap);
        }
    }

    private void alertMenu(final String name, final String url, final int position, final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(url);

        String[] menu = {context.getString(R.string.action_menu_open), context.getString(R.string.action_menu_share), context.getString(R.string.action_menu_copy_url), context.getString(R.string.action_menu_remove)};
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Open
                        dialog.dismiss();
                        web.loadUrl(url);
                        ((Activity)context).finish();
                        break;
                    case 1: // Share
                        dialog.dismiss();
                        Data.shareText(context, url);
                        break;
                    case 2: // Copy URL
                        Data.clipboard(context, url, true);
                        break;
                    case 3: // Remove
                        DataAnim.setAnimationDelete(context, view);
                        dialog.dismiss();
                        Data.deleteDF(new File(PATH_HISTORY + File.separator + name));
                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(position);
                                notifyDataSetChanged();
                            }
                        }, DataAnim.DURATION_ANIM);
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

