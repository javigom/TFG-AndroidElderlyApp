package com.example.simpleLauncher.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.simpleLauncher.R;
import com.example.simpleLauncher.data.AppListDataSource;
import com.example.simpleLauncher.databinding.ActivityAppListBinding;
import com.example.simpleLauncher.event.RecyclerViewAppListInterface;
import com.example.simpleLauncher.model.AppModel;
import com.example.simpleLauncher.util.OrderTypeAppModel;
import com.example.simpleLauncher.view.adapter.RecyclerViewAppListAdapter;
import com.example.simpleLauncher.viewmodel.AppListViewModel;
import com.google.android.material.snackbar.Snackbar;

public class AppListActivity extends AppCompatActivity implements RecyclerViewAppListInterface, ActivityCompat.OnRequestPermissionsResultCallback {

    // ATTRIBUTES

    private ActivityAppListBinding binding;
    private AppListViewModel appViewModel;
    private RecyclerViewAppListAdapter recyclerViewAppListAdapter;
    private static final int PERMISSION_REQUEST_STORAGE = 100;
    private Menu menu;

    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> appViewModel.setWallpaper(this, result));


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

    @Override
    protected void onResume(){
        super.onResume();
        recyclerViewAppListAdapter.setOrderOption(false);
        recyclerViewAppListAdapter.setEditOption(false);
        appViewModel.updateAppList(OrderTypeAppModel.ORDER_BY_NAME);
        if(this.menu != null)
            updateMenuOptions();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();

        try {
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Aplicaciones instaladas");
        } catch(NullPointerException e) {
            e.printStackTrace();
        }

        recyclerViewAppListAdapter = new RecyclerViewAppListAdapter(this, getApplicationContext());
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
    }

    @Override
    public void onUpButtonClick(AppModel app1, AppModel app2) {
        appViewModel.swapAppsButton(app1, app2);
        appViewModel.updateShortcutAppList(OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME);
    }

    @Override
    public void onDownButtonClick(AppModel app1, AppModel app2) {
        appViewModel.swapAppsButton(app2, app1);
        appViewModel.updateShortcutAppList(OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean orderOption = recyclerViewAppListAdapter.isOrderOption();
        boolean editOption = recyclerViewAppListAdapter.isEditOption();

        switch (item.getItemId()) {
            case android.R.id.home:
                showToastMessage(editOption, orderOption);
                super.onBackPressed();
                return true;
            case R.id.edit:
                showToastMessage(editOption, orderOption);
                recyclerViewAppListAdapter.setEditOption(!editOption);
                appViewModel.updateAppList(editOption? OrderTypeAppModel.ORDER_BY_NAME:  OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME);
                updateMenuOptions();
                return true;
            case R.id.order:
                showToastMessage(editOption, orderOption);
                recyclerViewAppListAdapter.setOrderOption(!orderOption);
                appViewModel.updateShortcutAppList(orderOption? OrderTypeAppModel.ORDER_BY_NAME:  OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME);
                updateMenuOptions();
                return true;
            case R.id.wallpaper:
                showToastMessage(editOption, orderOption);
                showGalleryPreview();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showToastMessage(boolean editOption, boolean orderOption) {
        if(editOption)
            Toast.makeText(getApplicationContext(), "Edición actualizada correctamente", Toast.LENGTH_SHORT).show();
        if(orderOption)
            Toast.makeText(getApplicationContext(), "Orden actualizado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void updateMenuOptions() {
        this.menu.findItem(R.id.order).setTitle((R.string.order_mode));
        this.menu.findItem(R.id.edit).setTitle((R.string.edit_mode));

        if(recyclerViewAppListAdapter.isEditOption())
            this.menu.findItem(R.id.edit).setTitle((R.string.exit_edit_mode));
        else if(recyclerViewAppListAdapter.isOrderOption())
            this.menu.findItem(R.id.order).setTitle((R.string.exit_order_mode));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,  menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGallery();
            } else {
                Snackbar.make(binding.recyclerView, "Se ha prohibido el acceso a la galería", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void showGalleryPreview() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            startGallery();
        } else {
            ActivityCompat.requestPermissions(AppListActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }
    }

    private void startGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }

}
