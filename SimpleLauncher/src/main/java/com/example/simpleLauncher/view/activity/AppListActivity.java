package com.example.simpleLauncher.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.simpleLauncher.R;
import com.example.simpleLauncher.databinding.ActivityAppListBinding;
import com.example.simpleLauncher.data.AppListDataSource;
import com.example.simpleLauncher.event.RecyclerViewAppListInterface;
import com.example.simpleLauncher.model.AppModel;
import com.example.simpleLauncher.util.OrderTypeAppModel;
import com.example.simpleLauncher.view.adapter.RecyclerViewAppListAdapter;
import com.example.simpleLauncher.viewmodel.AppListViewModel;

public class AppListActivity extends AppCompatActivity implements RecyclerViewAppListInterface {

    // ATTRIBUTES

    private ActivityAppListBinding binding;
    private AppListViewModel appViewModel;
    private RecyclerViewAppListAdapter recyclerViewAppListAdapter;

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
        ActionBar actionBar = getSupportActionBar();

        try {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Aplicaciones instaladas");
        } catch(NullPointerException e) {
            e.printStackTrace();
        }

        recyclerViewAppListAdapter = new RecyclerViewAppListAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerView.setAdapter(recyclerViewAppListAdapter);
        appViewModel.getAppList().observe(this, recyclerViewAppListAdapter::updateAppList);
        appViewModel.updateAppList(OrderTypeAppModel.ORDER_BY_NAME);
    }

    @Override
    public void onItemClick(AppModel app) {
        appViewModel.launchApp(app, this);
    }

    @Override
    public void onButtonClick(AppModel app) {
        appViewModel.clickShortcut(app);
        //startActivity(new Intent(AppListActivity.this, MainActivity.class));
    }

    @Override
    public void onUpButtonClick(AppModel app1, AppModel app2) {
        appViewModel.swapAppsButton(app1, app2);
        appViewModel.updateShortcutAppLit(OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME);
    }

    @Override
    public void onDownButtonClick(AppModel app1, AppModel app2) {
        appViewModel.swapAppsButton(app2, app1);
        appViewModel.updateShortcutAppLit(OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.edit:
                boolean editable = recyclerViewAppListAdapter.updateEditButtonVisibility();
                appViewModel.updateAppList(editable? OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME: OrderTypeAppModel.ORDER_BY_NAME);
                return true;
            case R.id.order:
                boolean order = recyclerViewAppListAdapter.updateOrderButtonVisibility();
                appViewModel.updateShortcutAppLit(order? OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME: OrderTypeAppModel.ORDER_BY_NAME);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,  menu);
        return true;
    }

}
