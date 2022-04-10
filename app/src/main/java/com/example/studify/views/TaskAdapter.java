package com.example.studify.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studify.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    Context context;
    ArrayList<String> nameset;
    ArrayList<String> timeset;
    public TaskAdapter(Context ct, ArrayList<String> name, ArrayList<String> time) {
        nameset= name;
        timeset = time;
        context = ct;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskName;
        private final TextView timeValue;
        public TextView getTimeValue() {
            return timeValue;
        }

        public TextView getTaskView() {
            return taskName;
        }

        public ViewHolder(View view) {
            super(view);
            taskName =view.findViewById(R.id.taskName);
            timeValue = view.findViewById(R.id.loopNumber);
        }

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup viewGroup;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.create_task_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.getTaskView().setText(nameset.get(position));
            holder.getTimeValue().setText(timeset.get(position));
    }

    @Override
    public int getItemCount() {
        return nameset.size();
    }
}
