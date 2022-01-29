package com.example.launcher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AppModel> appList;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.rvAppListAM);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        appList = new ArrayList<AppModel>();

        appList.add(new AppModel("Llamar", "com.example.call", R.drawable.ic_call,
                ResourcesCompat.getColor(getResources(), R.color.call, null)));

        appList.add(new AppModel("SMS", null, R.drawable.ic_message,
                ResourcesCompat.getColor(getResources(), R.color.sms, null)));

        appList.add(new AppModel("Cámara", "com.android.camera2", R.drawable.ic_camera,
                ResourcesCompat.getColor(getResources(), R.color.camera, null)));

        appList.add(new AppModel("Galería", null, R.drawable.ic_gallery,
                ResourcesCompat.getColor(getResources(), R.color.gallery, null)));

        appList.add(new AppModel("Videollamada", null, R.drawable.ic_videocall,
                ResourcesCompat.getColor(getResources(), R.color.videocall, null)));

        appList.add(new AppModel("Mensajería", null, R.drawable.ic_message,
                ResourcesCompat.getColor(getResources(), R.color.telegram, null)));

        appList.add(new AppModel("Otros", null, R.drawable.ic_extra,
                ResourcesCompat.getColor(getResources(), R.color.others, null)));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(appList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
}