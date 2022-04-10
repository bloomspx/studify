package com.example.studify.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studify.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>{
    Context context;
    String[] localDataset;
    public RoomAdapter(Context ct, String[] dataset) {
        localDataset = dataset;
        context = ct;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public TextView getTextView() {
            return textView;
        }

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.groupName);
        }

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup viewGroup;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.getTextView().setText(localDataset[position]);
    }

    @Override
    public int getItemCount() {
        return localDataset.length;
    }
}
