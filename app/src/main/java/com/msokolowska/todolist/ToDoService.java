package com.msokolowska.todolist;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ToDoService extends Service {

    public ToDoService() {
    }

    /* Get the data passed using intent and
     use SharedPreferences to save the modified data. */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ArrayList<String> modifiedData = intent.getStringArrayListExtra("modified_data");
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ToDoList", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Using HashSet<> because sharedPreferences Editor accepts Sets
        Set<String> set = new HashSet<>(modifiedData);
        editor.putStringSet("items", set);
        editor.apply();
        // Return START_NOT_STICKY to indicate that the service should not be restarted
        // if it's stopped for some reason
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
