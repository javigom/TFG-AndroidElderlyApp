package com.example.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> appList;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.rvAppListAM);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        appList = new ArrayList<String>();
        appList.add("com.example.tfg");

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(appList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
}