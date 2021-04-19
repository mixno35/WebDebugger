package com.mixno.web_debugger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.app.DataAnim;
import com.mixno.web_debugger.model.HelpModel;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpHolder> {

    private ArrayList<HelpModel> list;
    private Context context;

    public HelpAdapter(ArrayList<HelpModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public HelpAdapter.HelpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_help, parent, false);
        HelpHolder holder = new HelpHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HelpAdapter.HelpHolder holder, int position) {
        final HelpModel model = list.get(position);

        holder.textTitle.post(new Runnable() {
            @Override
            public void run() {
                holder.textTitle.setText(model.getTitle());
            }
        });
        holder.textMessage.post(new Runnable() {
            @Override
            public void run() {
                holder.textMessage.setText(model.getMessage());
            }
        });

        DataAnim.setFadeAnimation(holder.itemView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HelpHolder extends RecyclerView.ViewHolder {
        protected TextView textTitle;
        protected LinearLayout linTitle;
        protected TextView textMessage;
        protected LinearLayout linMessage;

        public HelpHolder(View item) {
            super(item);
            textTitle = item.findViewById(R.id.textTitle);
            linTitle = item.findViewById(R.id.linTitle);
            textMessage = item.findViewById(R.id.textMessage);
            linMessage = item.findViewById(R.id.linMessage);
        }
    }
}


