package com.example.simpleLauncher.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.simpleLauncher.data.AppListDataSource;
import com.example.simpleLauncher.databinding.ActivityMainBinding;
import com.example.simpleLauncher.event.RecyclerViewAppListInterface;
import com.example.simpleLauncher.model.AppModel;
import com.example.simpleLauncher.view.adapter.RecyclerViewMainAdapter;
import com.example.simpleLauncher.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements RecyclerViewAppListInterface {

    // ATTRIBUTES

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppListDataSource.updateContext(getApplicationContext());
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        initView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        AppListDataSource.updateContext(getApplicationContext());
        mainViewModel.updateShortcutAppList();
    }

    private void initView() {
        binding.buttonApps.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AppListActivity.class)));
        RecyclerViewMainAdapter adapter = new RecyclerViewMainAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        binding.recyclerView.setAdapter(adapter);
        mainViewModel.getShortcutAppList().observe(this, adapter::updateAppList);
        mainViewModel.updateShortcutAppList();
    }

    @Override
    public void onItemClick(AppModel app) {
        mainViewModel.launchApp(app, this);
    }
    @Override
    public void onButtonClick(AppModel app) {}
    @Override
    public void onUpButtonClick(AppModel app1, AppModel app2) {}
    @Override
    public void onDownButtonClick(AppModel app1, AppModel app2) {}

}