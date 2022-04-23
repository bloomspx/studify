package com.example.studify.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.studify.R;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<String> tasks;
    public List<String>selectedValues;
    private Context mContext1;
    private RecyclerView mRecyclerV1;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView cbactivitieslistreg;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            cbactivitieslistreg = v.findViewById(R.id.textView);
            selectedValues = new ArrayList<>();
        }

    }
    public Adapter(List<String> myDataset, Context context, RecyclerView recyclerView) {
        tasks = myDataset;
        mContext1 = context;
        mRecyclerV1 = recyclerView;
    }
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType){
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.row_values, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public void onBindViewHolder(final Adapter.ViewHolder holder, final int position){
        final String al = tasks.get(position);
        holder.cbactivitieslistreg.setText(al);

        holder.cbactivitieslistreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbactivitieslistreg.getBackground().getConstantState() != v.getResources().getDrawable(R.drawable.boxes1).getConstantState()){
                    selectedValues.remove(al);
                    holder.cbactivitieslistreg.setBackgroundResource(R.drawable.boxes1);
                }
                else{
                    selectedValues.add(al);
                    holder.cbactivitieslistreg.setBackgroundResource(R.drawable.boxes);
                }
            }});
    }
    public int getItemCount(){
        return  tasks.size();}
    public List<String> listofselectedactivites(){
        return selectedValues;
    }
}
