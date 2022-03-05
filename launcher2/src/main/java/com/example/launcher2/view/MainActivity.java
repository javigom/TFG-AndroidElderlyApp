package com.example.launcher2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.launcher2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // ATTRIBUTES

    private ActivityMainBinding binding;

    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initView();
    }

    private void initView() {
        binding.button.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AppListActivity.class)));
    }
}