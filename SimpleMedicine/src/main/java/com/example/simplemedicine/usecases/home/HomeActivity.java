package com.example.simplemedicine.usecases.home;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.simplemedicine.R;
import com.example.simplemedicine.databinding.ActivityHomeBinding;
import com.example.simplemedicine.usecases.medication.MedicationRouter;
import com.example.simplemedicine.usecases.today.TodayRouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;

    private MedicationRouter medicationFragment;
    private TodayRouter todayFragment;

    private int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        selectedItem = -1;
        initView();
    }

    private void initView() {
        loadTabs();
        defaultTab();
    }

    private void loadTabs() {

        if(medicationFragment != null)
            medicationFragment.remove(getSupportFragmentManager());
        if(todayFragment != null)
            todayFragment.remove(getSupportFragmentManager());

        medicationFragment = new MedicationRouter();
        todayFragment = new TodayRouter();

        binding.homeBottomNavigationView.setOnItemSelectedListener(item -> {
            if (selectedItem != item.getItemId()) {
                selectedItem = item.getItemId();

                if (item.getItemId() == R.id.home_menu_medication) {
                    todayFragment.hide(getSupportFragmentManager());
                    if (getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.home_menu_medication)) == null) {
                        medicationFragment.add(getSupportFragmentManager(), R.id.homeContainer, String.valueOf(R.id.home_menu_medication));
                    }
                    medicationFragment.show(getSupportFragmentManager());
                } else if (item.getItemId() == R.id.home_menu_today) {
                    medicationFragment.hide(getSupportFragmentManager());
                    if (getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.home_menu_today)) == null) {
                        todayFragment.add(getSupportFragmentManager(), R.id.homeContainer, String.valueOf(R.id.home_menu_today));
                    }
                    todayFragment.show(getSupportFragmentManager());
                }
                return true;
            }
            return false;
        });

    }

    private void defaultTab() {
        binding.homeBottomNavigationView.setSelectedItemId(viewModel.defaultTab());
    }

}