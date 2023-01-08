package com.msokolowska.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class AlertDialogFragment extends DialogFragment {

    private ArrayList<String> itemList;
    private ArrayAdapter<String> arrayAdapter;
    private int position;

    public AlertDialogFragment() {
    // required empty public constructor
}
    public static AlertDialogFragment newInstance(ArrayList<String> itemList,
                                                  ArrayAdapter<String> arrayAdapter,
                                                   int position){
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        alertDialogFragment.arrayAdapter = arrayAdapter;
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
                    arrayAdapter.notifyDataSetChanged();
                    FileHelper.writeData(itemList, getContext())
                    ;
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setCancelable(false)
                .create();
    }

}
