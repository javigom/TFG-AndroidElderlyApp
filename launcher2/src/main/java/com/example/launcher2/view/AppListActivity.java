package com.example.launcher2.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.launcher2.R;
import com.example.launcher2.data.AppListDataSource;
import com.example.launcher2.model.AppModel;
import com.example.launcher2.viewmodel.AppListViewModel;

public class AppListActivity extends AppCompatActivity {

    // ATTRIBUTES
    private AppListDataSource appListDataSource;
    private AppListViewModel appViewModel;

    private RecyclerView recyclerView;
    private RecyclerViewAppListAdapter adapter;

    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        appListDataSource = new AppListDataSource(getApplicationContext());
        initView();
    }

    private void initView() {
        appViewModel = ViewModelProviders.of(this).get(AppListViewModel.class);

        recyclerView = findViewById(R.id.recyclerViewAppListActivity);

        adapter = new RecyclerViewAppListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        //appViewModel = ViewModelProviders.of(this).get(AppListViewModel.class);
        //appViewModel.getAppList().observe(this, userListUpdateObserver);
        //appViewModel.getAppList().observe(this, (Observer<List<AppModel>>) appModelList -> textView.setText(appModelList.toString()));

        appViewModel.getAppList().observe(this, appModelList -> adapter.updateAppList(appModelList));
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
