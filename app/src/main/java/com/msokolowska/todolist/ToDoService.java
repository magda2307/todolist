package com.msokolowska.todolist;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ToDoService extends Service {

    public ToDoService() {
    }
    /* Get the data passed using intend and
     use FileHelper class to save the modified data to the file. */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ArrayList<String> modifiedData = intent.getStringArrayListExtra("modified_data");
        FileHelper.writeData(modifiedData, getApplicationContext());
        // Return START_NOT_STICKY to indicate that the service should not be restarted
        // if its stopped for some reason
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}