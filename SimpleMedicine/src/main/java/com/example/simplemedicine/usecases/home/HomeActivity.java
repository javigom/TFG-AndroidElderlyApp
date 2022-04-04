package com.example.simplemedicine.usecases.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.simplemedicine.R;
import com.example.simplemedicine.databinding.ActivityHomeBinding;
import com.example.simplemedicine.usecases.medication.MedicationRouter;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    private MedicationRouter medicationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        initView();
    }

    private void initView() {
        medicationFragment = new MedicationRouter();

        // AÑADIR TAG VALIDO
        if(getSupportFragmentManager().findFragmentByTag("medicationFragment") == null) {
            medicationFragment.add(getSupportFragmentManager(), R.id.homeContainer, "medicationFragment");
        }
        medicationFragment.show(getSupportFragmentManager());
        //fragmentManager.beginTransaction().add(R.id.fragmentContainer, medicationFragment).commit();
    }

}