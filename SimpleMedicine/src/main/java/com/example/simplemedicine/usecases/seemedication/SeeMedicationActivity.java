package com.example.simplemedicine.usecases.seemedication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.simplemedicine.databinding.ActivitySeeMedicationBinding;
import com.example.simplemedicine.model.medication.Medication;

public class SeeMedicationActivity extends AppCompatActivity {

    private ActivitySeeMedicationBinding binding;
    private SeeMedicationViewModel viewModel;
    private Medication medication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeMedicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            medication = (Medication) intent.getSerializableExtra("medication");
        }

        // View Model
        viewModel = new ViewModelProvider(this).get(SeeMedicationViewModel.class);
        initView();
    }

    private void initView() {
        binding.name.setText(medication.getName());
        binding.description.setText(medication.toString());
    }
}