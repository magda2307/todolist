package com.msokolowska.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText item;
    Button button;
    RecyclerView recyclerView;
    ArrayList<String> itemList = new ArrayList<>();
    RecyclerAdapter adapter;
    String[] elements;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        elements = getResources().getStringArray(R.array.elements);
        item = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recycleView);
        itemList = FileHelper.readData(this);

        // Adding LinearLayoutManager to avoid Android skipping the layout and
        // therefore breaking the code
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(this, itemList, elements);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(view -> {
            String itemName = item.getText().toString();
            itemList.add(itemName);
            adapter.notifyDataSetChanged();
            item.setText("");
            // Using my ToDoService class and starting the service
            Intent serviceIntent = new Intent(this, ToDoService.class);
            serviceIntent.putExtra("modified_data", itemList);
            startService(serviceIntent);
        });
    }
}
