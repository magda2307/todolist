package com.msokolowska.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final MainActivity mainActivity;
    private final ArrayList<String> itemList;
    private final String[] elements;

    public RecyclerAdapter(MainActivity mainActivity, ArrayList<String> itemList, String[] elements) {
        this.mainActivity = mainActivity;
        this.itemList = itemList;
        this.elements = elements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*checking whether the position if item is even or odd. in case of odd - instead of text, web object
        will be displayed*/
        if(position % 2 == 0) {
            holder.textView.setText(itemList.get(position));
            holder.textView.setVisibility(View.VISIBLE);
            holder.webView.setVisibility(View.GONE);
        } else {
            // Odd position - display webView
            String element = elements[position%3];
            // Setting WebViewClient prevents app from unnecessary opening Google Chrome
            holder.webView.setWebViewClient(new WebViewClient());
            if (URLUtil.isValidUrl(element)) {
                // Element is a URL, load it in the WebView
                holder.webView.loadUrl(element);
            } else {
                // Element is not a URL, assume it is HTML code and load it as a string
                holder.webView.loadData(element, "text/html", "utf-8");
            }
            holder.webView.setVisibility(View.VISIBLE);
            holder.textView.setVisibility(View.GONE);
        }
        // Delete icon applies to both web and text based items
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
        WebView webView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.taskView);
            deleteIcon = itemView.findViewById(R.id.trashcan);
            webView = itemView.findViewById(R.id.webView);

        }
    }
}
