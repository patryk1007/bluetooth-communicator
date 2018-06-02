package com.patryk1007.bluetoothtestapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patryk1007.bluetoothtestapp.R;

import java.util.ArrayList;
import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    private List<String> logs = new ArrayList<>();

    public void addLog(String message) {
        if (message != null && !message.isEmpty()) {
            logs.add(message);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new ViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.logMessage.setText(logs.get(position));
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView logMessage;

        ViewHolder(View mainView) {
            super(mainView);
            logMessage = mainView.findViewById(R.id.log_message);
        }
    }

}
