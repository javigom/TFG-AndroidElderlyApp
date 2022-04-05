package com.example.simplemedicine.usecases.medicationdetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.simplemedicine.databinding.ActivityMedicationDetailBinding;
import com.example.simplemedicine.model.Medication;

public class MedicationDetailActivity extends AppCompatActivity {

    private ActivityMedicationDetailBinding binding;
    private MedicationDetailViewModel viewModel;
    private Medication medication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            medication = (Medication) intent.getSerializableExtra("medication");
        }

        // View Model
        viewModel = ViewModelProviders.of(this).get(MedicationDetailViewModel.class);
        initView();
    }

    private void initView() {
        binding.name.setText(medication.getName());
        binding.description.setText(medication.toString());
    }
}