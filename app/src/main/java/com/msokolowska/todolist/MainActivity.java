package com.msokolowska.todolist;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText item;
    Button button;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item=findViewById(R.id.editText);
        button=findViewById(R.id.button);
        listView=findViewById(R.id.list);
        itemList = FileHelper.readData(this);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
        listView.setAdapter(arrayAdapter);
        button.setOnClickListener(view -> {
            String itemName = item.getText().toString();
            itemList.add(itemName);
            item.setText("");
            FileHelper.writeData(itemList, getApplicationContext());
            arrayAdapter.notifyDataSetChanged();
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(itemList, arrayAdapter, position);
            alertDialogFragment.show(getSupportFragmentManager(), "alert");
        });

        }
    }

