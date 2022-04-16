package com.example.simplemedicine.usecases.addmedication.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simplemedicine.databinding.FragmentAddMedicationPage1Binding;
import com.example.simplemedicine.model.medication.Medication;

public class AddMedicationPage1Fragment extends Fragment {

    FragmentAddMedicationPage1Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        binding = FragmentAddMedicationPage1Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected boolean setData(Medication medication) {
        if(binding.name.getText().toString().equals("")){
            return false;
        }
        medication.setName(binding.name.getText().toString());
        medication.setDescription(binding.description.getText().toString());
        return true;
    }

}
