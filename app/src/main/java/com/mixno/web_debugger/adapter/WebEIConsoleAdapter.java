package com.mixno.web_debugger.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.mixno.web_debugger.R;
import com.mixno.web_debugger.model.ConsoleModel;

public class WebEIConsoleAdapter extends RecyclerView.Adapter<WebEIConsoleAdapter.WebEIConsoleHolder> {

    private ArrayList<ConsoleModel> list;
    private Context context;

    public WebEIConsoleAdapter(ArrayList<ConsoleModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public WebEIConsoleAdapter.WebEIConsoleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_console, parent, false);
        WebEIConsoleHolder holder = new WebEIConsoleHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final WebEIConsoleAdapter.WebEIConsoleHolder holder, int position) {
        final ConsoleModel model = list.get(position);
        holder.textMessage.post(new Runnable() {
            @Override
            public void run() {
                holder.textMessage.setText(model.getMessage());
            }
        });
        holder.textSourceID.post(new Runnable() {
            @Override
            public void run() {
                holder.textSourceID.setText(model.getSourceID());
            }
        });
        holder.textLinNumber.post(new Runnable() {
            @Override
            public void run() {
                holder.textLinNumber.setText("(" + model.getType() + ") " + model.getLineNumber());
            }
        });

        if (model.getSourceID().equals("")) {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    holder.linSourceID.setVisibility(View.GONE);
                }
            });
        }
        if (model.getMessage().equals("")) {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    holder.linMessage.setVisibility(View.GONE);
                }
            });
        }
        if (model.getType() == "error") {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    holder.linMessage.setBackgroundColor(context.getResources().getColor(R.color.consoleErrorBack));
                    holder.imageType.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.imageType.setImageResource(R.drawable.ic_console_error);
                        }
                    });
                    holder.textMessage.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.textMessage.setTextColor(context.getResources().getColor(R.color.consoleError));
                        }
                    });
                }
            });
        }
        if (model.getType() == "warning") {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    holder.linMessage.setBackgroundColor(context.getResources().getColor(R.color.consoleWarningBack));
                    holder.imageType.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.imageType.setImageResource(R.drawable.ic_console_warning);
                        }
                    });
                    holder.textMessage.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.textMessage.setTextColor(context.getResources().getColor(R.color.consoleWarning));
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

    public static class WebEIConsoleHolder extends RecyclerView.ViewHolder {
        protected TextView textMessage;
        protected LinearLayout linMessage;
        protected TextView textSourceID;
        protected LinearLayout linSourceID;
        protected TextView textLinNumber;
        protected ImageView imageType;

        public WebEIConsoleHolder(View item) {
            super(item);
            textMessage = item.findViewById(R.id.textMessage);
            linMessage = item.findViewById(R.id.linMessage);
            textSourceID = item.findViewById(R.id.textSourceID);
            linSourceID = item.findViewById(R.id.linSourceID);
            textLinNumber = item.findViewById(R.id.textLinNumber);
            imageType = item.findViewById(R.id.imageType);
        }
    }
}
