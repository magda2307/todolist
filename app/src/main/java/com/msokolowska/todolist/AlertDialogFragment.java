package com.msokolowska.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class AlertDialogFragment extends DialogFragment {

    private ArrayList<String> itemList;
    private RecyclerAdapter recyclerAdapter;
    private int position;

    public AlertDialogFragment() {
    // required empty public constructor
}
    public static AlertDialogFragment newInstance(ArrayList<String> itemList,
                                                  RecyclerAdapter recyclerAdapter,
                                                  int position){
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        alertDialogFragment.recyclerAdapter = recyclerAdapter;
        alertDialogFragment.itemList = itemList;
        alertDialogFragment.position = position;
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
                    FileHelper.writeData(itemList, getContext())
                    ;
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setCancelable(false)
                .create();
    }

}
