package com.mixno.web_debugger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.mixno.web_debugger.MainActivity;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.dialog.CookieManagerDialog;
import com.mixno.web_debugger.model.CookieManagerModel;
import com.mixno.web_debugger.widget.WebEI;

public class CookieManagerAdapter extends RecyclerView.Adapter<CookieManagerAdapter.CookieManagerHolder> {

    private List<CookieManagerModel> list;
    private Context context;
    private WebEI web;

    public CookieManagerAdapter(List<CookieManagerModel> list, Context context, WebEI web) {
        this.list = list;
        this.context = context;
        this.web = web;
    }

    @Override
    public CookieManagerAdapter.CookieManagerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cookie_manager, parent, false);
        CookieManagerHolder holder = new CookieManagerHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CookieManagerAdapter.CookieManagerHolder holder, final int position) {
        final CookieManagerModel model = list.get(position);

        holder.textId.post(new Runnable() {
            @Override
            public void run() {
                holder.textId.setText(String.valueOf(model.getId()));
            }
        });
        holder.textName.post(new Runnable() {
            @Override
            public void run() {
                holder.textName.setText(model.getName());
            }
        });
        holder.textMessage.post(new Runnable() {
            @Override
            public void run() {
                holder.textMessage.setText(model.getValue());
            }
        });

        holder.imageRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookieManagerDialog.removeCookie(context, model.getName(), MainActivity.mWeb);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CookieManagerHolder extends RecyclerView.ViewHolder {
        protected TextView textId, textName, textMessage;
        protected ImageView imageRemove;

        public CookieManagerHolder(View item) {
            super(item);
            textId = item.findViewById(R.id.textViewId);
            textName = item.findViewById(R.id.textViewName);
            textMessage = item.findViewById(R.id.textViewMessage);
            imageRemove = item.findViewById(R.id.imageViewRemove);
        }
    }
}



