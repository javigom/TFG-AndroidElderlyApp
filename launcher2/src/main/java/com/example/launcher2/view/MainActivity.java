package com.example.launcher2.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.launcher2.R;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        button = findViewById(R.id.button);
        button.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AppListActivity.class)));

    }
}