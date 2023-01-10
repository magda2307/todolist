package com.msokolowska.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class AlertDialogFragment extends DialogFragment {

    private ArrayList<ToDoItem> itemList;
    private RecyclerAdapter recyclerAdapter;
    private int position;
    private Context context;
    public AlertDialogFragment() {
        // required empty public constructor
    }

    public static AlertDialogFragment newInstance(ArrayList<ToDoItem> itemList,
                                                  RecyclerAdapter recyclerAdapter,
                                                  int position, Context context) {
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        alertDialogFragment.recyclerAdapter = recyclerAdapter;
        alertDialogFragment.itemList = itemList;
        alertDialogFragment.position = position;
        alertDialogFragment.context = context;
        return alertDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setMessage(getString(R.string.delete_question))
                .setPositiveButton("Yes", (dialog, which) -> {
                    itemList.remove(position);
                    recyclerAdapter.notifyDataSetChanged();

                    // Save the updated list of items to shared preferences
                    Intent intent = new Intent(getContext(), ToDoService.class);
                    intent.putExtra(ToDoService.ITEMS_KEY, itemList);
                    context.startService(intent);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setCancelable(false)
                .create();
    }
}