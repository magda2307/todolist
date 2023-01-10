package com.msokolowska.todolist;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
// Service responsible for saving and loading of ToDoItems using
// SharedPreferences and Gson.
public class ToDoService extends Service {
    public static final String PREFS_NAME = "ToDoPrefs";
    public static final String ITEMS_KEY = "ToDoItems";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Saves the ToDoItems passed in the Intent (using extra) to SharedPreferences.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ArrayList<ToDoItem> items = intent.getParcelableArrayListExtra(ITEMS_KEY);
        if (items == null) items = new ArrayList<>();
        saveToDoItems(items);
        return super.onStartCommand(intent, flags, startId);
    }
    // Loads ToDoItems that are saved in SharedPreferences.
    public static ArrayList<ToDoItem> loadToDoItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ITEMS_KEY, null);
        Type type = new TypeToken<ArrayList<ToDoItem>>() {}.getType();
        ArrayList<ToDoItem> items = gson.fromJson(json, type);
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
    // Saves given list of ToDoItems to SharedPreferences.
    void saveToDoItems(ArrayList<ToDoItem> items) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString(ITEMS_KEY, json);
        editor.apply();
    }
}