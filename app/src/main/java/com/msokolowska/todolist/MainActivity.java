package com.msokolowska.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private boolean sortByTextLengthOrder = true;
    private boolean sortByDateOrder = true;
    EditText item;
    Button button;
    Button sortByTextLength;
    Button sortByDate;
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
        sortByTextLength = findViewById(R.id.sortByTextLength);
        sortByDate = findViewById(R.id.sortByDate);
    // Load the saved items from shared preferences
        Intent intent = new Intent(this, ToDoService.class);
        startService(intent);
        ArrayList<ToDoItem> loadedItems = ToDoService.loadToDoItems(this);
        if (loadedItems == null) {
            itemList = new ArrayList<>();
        } else {
            itemList = loadedItems;
        }
        // Adding LinearLayoutManager to avoid Android skipping the layout
        // and therefore breaking the code
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        if (itemList == null) itemList = new ArrayList<>();
        adapter = new RecyclerAdapter(this, itemList, elements);
        recyclerView.setAdapter(adapter);
        // Add button click behaviour
        button.setOnClickListener(this::onClick);
        // Sort By Text Length button click behaviour
        sortByTextLength.setOnClickListener(view -> {
            sortByTextLengthOrder = !sortByTextLengthOrder;
            if(sortByTextLengthOrder){
                Collections.sort(itemList, (o1, o2) -> Integer.compare(o1.getText().length(), o2.getText().length()));
            }else{
                Collections.sort(itemList, (o1, o2) -> Integer.compare(o2.getText().length(), o1.getText().length()));
            }
            adapter.notifyDataSetChanged();
        });
        // Sort By Date button click behaviour
        sortByDate.setOnClickListener(view -> {
            sortByDateOrder = !sortByDateOrder;
            if(sortByDateOrder){
                Collections.sort(itemList, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
            }else{
                Collections.sort(itemList, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void onClick(View view) {
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
    }
}