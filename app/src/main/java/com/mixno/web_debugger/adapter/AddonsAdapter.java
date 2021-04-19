package com.mixno.web_debugger.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.Data;
import com.mixno.web_debugger.dialog.AddonsDialog;
import com.mixno.web_debugger.model.AddonsModel;
import com.mixno.web_debugger.widget.WebEI;

public class AddonsAdapter extends RecyclerView.Adapter<AddonsAdapter.AddonsHolder> {

    private List<AddonsModel> list;
    private Context context;
    private WebEI web;

   public AddonsAdapter(List<AddonsModel> list, Context context, WebEI web) {
        this.list = list;
        this.context = context;
        this.web = web;
    }

    @Override
    public AddonsAdapter.AddonsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_addons, parent, false);
        AddonsHolder holder = new AddonsHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AddonsAdapter.AddonsHolder holder, final int position) {
        final AddonsModel model = list.get(position);

        holder.textName.post(new Runnable() {
            @Override
            public void run() {
                holder.textName.setText(model.getName());
            }
        });
        holder.textDescription.post(new Runnable() {
            @Override
            public void run() {
                holder.textDescription.setText(model.getDescription() + " | " + model.getVersion() + "-" + model.getVersionCode() + "");
            }
        });

        if (model.getId() > 0) {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    holder.buttonDelete.setVisibility(View.VISIBLE);
                }
            });
        } else {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    holder.buttonDelete.setVisibility(View.GONE);
                }
            });
        }

        try {
            holder.imageView.post(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imageView.setClipToOutline(true);
                    }
                }
            });
        } catch (Exception e) {}
        try {
            if (!model.getIcon().trim().equals("")) {
                File imgFile = new File(model.getPath() + File.separator + model.getIcon());
                if (imgFile.exists()) {
                    final Bitmap icon = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    holder.imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.imageView.setImageBitmap(icon);
                        }
                    });
                }
            }
        } catch (Exception e) {}

        holder.button.post(new Runnable() {
            @Override
            public void run() {
                holder.button.setClickable(false);
                holder.button.setEnabled(false);
                holder.button.setAlpha(0.2f);
            }
        });

        final File sourceFile = new File(model.getPath() + File.separator + model.getSource());
        if (model.getSource() != null || model.getSource() != "") {
            holder.button.post(new Runnable() {
                @Override
                public void run() {
                    holder.button.setClickable(true);
                    holder.button.setEnabled(true);
                    holder.button.setAlpha(1.0f);
                }
            });
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getSource().endsWith(".js") || model.getSource().endsWith(".javascript")) {
                    if (sourceFile.exists()) {
                        MainActivity.mWeb.runJS(Data.read(sourceFile));
                    }
                } else {
                    MainActivity.mWeb.runJS(model.getSource());
                }
                if (model.isHideAddons()) {
                    AddonsDialog.dialog.cancel();
                }
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.deleteDF(new File(model.getPath()));
                try {
                    AddonsDialog.list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, AddonsDialog.list.size());
                } catch (Exception e) {}
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AddonsHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textName;
        protected TextView textDescription;
        protected ImageView button, buttonDelete;

        public AddonsHolder(View item) {
            super(item);
            imageView = item.findViewById(R.id.imageView2);
            textName = item.findViewById(R.id.textView);
            textDescription = item.findViewById(R.id.textView2);
            button = item.findViewById(R.id.imageRun);
            buttonDelete = item.findViewById(R.id.imageDelete);
        }
    }
}



