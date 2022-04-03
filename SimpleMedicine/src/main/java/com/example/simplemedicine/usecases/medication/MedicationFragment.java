package com.example.simplemedicine.usecases.medication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.simplemedicine.databinding.FragmentMedicationBinding;
import com.example.simplemedicine.usecases.common.rows.MedicationRecyclerViewAdapter;

public class MedicationFragment extends Fragment {

    private FragmentMedicationBinding binding;
    private MedicationViewModel viewModel;
    private MedicationRecyclerViewAdapter recyclerViewAdapter;

    public MedicationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        binding = FragmentMedicationBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this).get(MedicationViewModel.class);

        // Recycler view
        recyclerViewAdapter = new MedicationRecyclerViewAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(recyclerViewAdapter);
        viewModel.getMedicationList().observe(getViewLifecycleOwner(), recyclerViewAdapter::updateMedicationList);


        return binding.getRoot();
    }
}