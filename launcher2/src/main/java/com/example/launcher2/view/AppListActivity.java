package com.example.launcher2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.launcher2.data.AppListDataSource;
import com.example.launcher2.databinding.ActivityAppListBinding;
import com.example.launcher2.event.RecyclerViewAppListInterface;
import com.example.launcher2.model.AppModel;
import com.example.launcher2.viewmodel.AppListViewModel;

public class AppListActivity extends AppCompatActivity implements RecyclerViewAppListInterface {

    // ATTRIBUTES

    private ActivityAppListBinding binding;
    private AppListViewModel appViewModel;

    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        AppListDataSource.updateContext(getApplicationContext());
        appViewModel = ViewModelProviders.of(this).get(AppListViewModel.class);

        initView();
    }

    private void initView() {
        RecyclerViewAppListAdapter adapter = new RecyclerViewAppListAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerView.setAdapter(adapter);
        appViewModel.getAppList().observe(this, adapter::updateAppList);
        appViewModel.updateAppList();
    }

    @Override
    public void onItemClick(AppModel  app) {
        appViewModel.launchApp(app, this);
    }

    @Override
    public void onButtonClick(AppModel app) {
        appViewModel.clickShortcut(app);
        startActivity(new Intent(AppListActivity.this, MainActivity.class));
    }

}
