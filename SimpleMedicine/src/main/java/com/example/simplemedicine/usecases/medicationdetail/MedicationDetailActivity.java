package com.example.simplemedicine.usecases.medicationdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.example.simplemedicine.R;
import com.example.simplemedicine.databinding.ActivityHomeBinding;
import com.example.simplemedicine.databinding.ActivityMedicationDetailBinding;
import com.example.simplemedicine.model.MedicationModel;
import com.example.simplemedicine.usecases.home.HomeViewModel;
import com.example.simplemedicine.usecases.medication.MedicationRouter;

public class MedicationDetailActivity extends AppCompatActivity {

    private ActivityMedicationDetailBinding binding;
    private MedicationDetailViewModel viewModel;
    private MedicationModel medication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            medication = (MedicationModel) intent.getSerializableExtra("medication");
        }

        // View Model
        viewModel = ViewModelProviders.of(this).get(MedicationDetailViewModel.class);
        initView();
    }

    private void initView() {
        binding.name.setText(medication.getName());
        binding.description.setText(medication.getDescription());
    }
}