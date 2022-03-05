package com.example.launcher2.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.launcher2.data.AppListDataSource;
import com.example.launcher2.databinding.ActivityAppListBinding;
import com.example.launcher2.viewmodel.AppListViewModel;

public class AppListActivity extends AppCompatActivity {

    // ATTRIBUTES

    private ActivityAppListBinding binding;
    private AppListDataSource appListDataSource;
    private AppListViewModel appViewModel;

    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        appListDataSource = new AppListDataSource(getApplicationContext());
        appViewModel = ViewModelProviders.of(this).get(AppListViewModel.class);

        initView();
    }

    private void initView() {
        RecyclerViewAppListAdapter adapter = new RecyclerViewAppListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerView.setAdapter(adapter);
        appViewModel.getAppList().observe(this, adapter::updateAppList);
        appViewModel.updateAppList(appListDataSource);
    }

    /*
    @Override
    public void selectedApp(AppModel appModel) {


        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        System.out.println(appModel);


        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(appModel.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setClassName(appModel.getPackageName(), className);
        startActivity(intent);


        //Intent intent = getPackageManager().getLaunchIntentForPackage(appModel.getPackageName());
        //startActivity(intent);
    }*/
}
