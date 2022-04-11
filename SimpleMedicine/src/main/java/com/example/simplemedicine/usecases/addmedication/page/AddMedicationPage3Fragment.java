package com.example.simplemedicine.usecases.addmedication.page;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.simplemedicine.databinding.FragmentAddMedicationPage3Binding;
import com.example.simplemedicine.model.Hour;
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.usecases.common.rows.DaysRecyclerViewAdapter;

public class AddMedicationPage3Fragment extends Fragment {

    private FragmentAddMedicationPage3Binding binding;
    private DaysRecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        binding = FragmentAddMedicationPage3Binding.inflate(inflater, container, false);
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
        initView();
    }

    private void initView() {
        recyclerViewAdapter = new DaysRecyclerViewAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(recyclerViewAdapter);
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(!recyclerViewAdapter.addHour(new Hour(hourOfDay, minute))) {
                            Toast.makeText(getContext(), "No puedes añadir dos horas iguales", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 9, 0, true);
                timePickerDialog.show();
            }
        });
    }

    protected boolean setData(Medication medication) {
        if(binding.numberText.getText().toString().equals("")) {
            return false;
        }
        medication.setUnitsPerDosis(Integer.valueOf(binding.numberText.getText().toString()));
        medication.setHours(recyclerViewAdapter.getHourList());
        return true;
    }
}