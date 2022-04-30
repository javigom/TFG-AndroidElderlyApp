package com.example.simplemedicine.usecases.today;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.simplemedicine.databinding.FragmentTodayBinding;
import com.example.simplemedicine.usecases.common.rows.TodayRecyclerViewAdapter;

public class TodayFragment extends Fragment {

    // ATTRIBUTES

    private FragmentTodayBinding binding;
    private TodayViewModel viewModel;
    private TodayRecyclerViewAdapter recyclerViewAdapter;


    // CONSTRUCTOR

    public TodayFragment() {
    }


    // METHODS

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        binding = FragmentTodayBinding.inflate(inflater, container, false);
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
        viewModel = new ViewModelProvider(this).get(TodayViewModel.class);

        // Setup
        initView();
        initData();
    }

    private void initView() {
        // Recycler view
        recyclerViewAdapter = new TodayRecyclerViewAdapter(viewModel.getRepository());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initData() {
        viewModel.getAllNotifications().observe(getViewLifecycleOwner(), recyclerViewAdapter::updateMedicationList);
    }
}
