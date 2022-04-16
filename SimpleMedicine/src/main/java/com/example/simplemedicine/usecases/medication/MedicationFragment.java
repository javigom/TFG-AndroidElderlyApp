package com.example.simplemedicine.usecases.medication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

        // View Model
        viewModel = new ViewModelProvider(this).get(MedicationViewModel.class);

        // Setup
        initView();
        initData();
    }

    private void initView() {
        // Recycler view
        recyclerViewAdapter = new MedicationRecyclerViewAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initData() {
        viewModel.getAllMedications().observe(getViewLifecycleOwner(), recyclerViewAdapter::updateMedicationList);
    }
}