package com.example.simplemedicine.usecases.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.simplemedicine.R;
import com.example.simplemedicine.databinding.ActivityHomeBinding;
import com.example.simplemedicine.usecases.medication.MedicationFragment;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    private Fragment medicationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        initView();
    }

    private void initView() {
        medicationListFragment = new MedicationFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, medicationListFragment).commit();
    }

}