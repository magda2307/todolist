package com.msokolowska.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final MainActivity mainActivity;
    private final ArrayList<String> itemList;

    public RecyclerAdapter(MainActivity mainActivity, ArrayList<String> itemList) {
        this.mainActivity = mainActivity;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(itemList.get(position));
        holder.deleteIcon.setOnClickListener(view -> {
            AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(itemList, this, position);
            alertDialogFragment.show(mainActivity.getSupportFragmentManager(), "alert");
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView deleteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.taskView);
            deleteIcon = itemView.findViewById(R.id.trashcan);

        }
    }
}
