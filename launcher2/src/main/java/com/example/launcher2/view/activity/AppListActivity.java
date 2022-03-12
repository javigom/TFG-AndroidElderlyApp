package com.example.launcher2.view.activity;

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

import com.example.launcher2.R;
import com.example.launcher2.data.AppListDataSource;
import com.example.launcher2.databinding.ActivityAppListBinding;
import com.example.launcher2.event.RecyclerViewAppListInterface;
import com.example.launcher2.model.AppModel;
import com.example.launcher2.util.OrderTypeAppModel;
import com.example.launcher2.view.adapter.RecyclerViewAppListAdapter;
import com.example.launcher2.viewmodel.AppListViewModel;

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
    public void onItemClick(AppModel  app) {
        appViewModel.launchApp(app, this);
    }

    @Override
    public void onButtonClick(AppModel app) {
        appViewModel.clickShortcut(app);
        //startActivity(new Intent(AppListActivity.this, MainActivity.class));
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.edit:
                boolean editable = recyclerViewAppListAdapter.updateButtonVisibility();
                appViewModel.updateAppList(editable? OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME: OrderTypeAppModel.ORDER_BY_NAME);
                return true;
            case R.id.order:
                System.out.println("has pulsado ordenar");
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
