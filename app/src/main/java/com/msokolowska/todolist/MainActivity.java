package com.msokolowska.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText item;
    Button button;
    RecyclerView recyclerView;
    ArrayList<ToDoItem> itemList = new ArrayList<>();
    RecyclerAdapter adapter;
    String[] elements;
    int nextId = 1;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        elements = getResources().getStringArray(R.array.elements);
        item = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recycleView);

    // Load the saved items from shared preferences
        Intent intent = new Intent(this, ToDoService.class);
        startService(intent);
        ArrayList<ToDoItem> loadedItems = ToDoService.loadToDoItems(this);
        if (loadedItems == null) {
            itemList = new ArrayList<ToDoItem>();
        } else {
            itemList = loadedItems;
        }
        // Adding LinearLayoutManager to avoid Android skipping the layout and
        // therefore breaking the code
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        if (itemList == null) itemList = new ArrayList<>();
        adapter = new RecyclerAdapter(this, itemList, elements);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(view -> {
            String itemName = item.getText().toString();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String dateString = dateFormat.format(new Date());
            ToDoItem toDoItem = new ToDoItem(String.valueOf(nextId), dateString, itemName, false);
            if (itemList == null) {
                itemList = new ArrayList<>();
            }
            itemList.add(toDoItem);
            adapter.notifyDataSetChanged();
            item.setText("");

            // Save the updated list of items to shared preferences using Intent and ToDoService
            Intent serviceIntent = new Intent(this, ToDoService.class);
            serviceIntent.putExtra(ToDoService.ITEMS_KEY, itemList);
            startService(serviceIntent);
            nextId++;
        });
    }
    }