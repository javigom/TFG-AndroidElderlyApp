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
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.simplemedicine.databinding.FragmentAddMedicationPage3Binding;
import com.example.simplemedicine.model.hour.HourModel;
import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.usecases.common.rows.HoursRecyclerViewAdapter;

public class AddMedicationPage3Fragment extends Fragment {

    private FragmentAddMedicationPage3Binding binding;
    private HoursRecyclerViewAdapter recyclerViewAdapter;
    private int units;
    private boolean editMode;
    private Medication medication;

    public AddMedicationPage3Fragment(boolean editMode, Medication medication) {
        this.editMode = editMode;
        this.medication = medication;

        if(this.editMode)
            this.units = medication.getUnitsPerDosis();
        else
            this.units = 1;
    }
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
        recyclerViewAdapter = new HoursRecyclerViewAdapter(editMode, medication.getHours());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.recyclerView.setAdapter(recyclerViewAdapter);
        binding.numberText.setText(String.valueOf(units));

        // Buttons

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewAdapter.getHourList().size() < 9) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            recyclerViewAdapter.addHour(getContext(), new HourModel(hourOfDay, minute));
                        }
                    }, 9, 0, true);
                    timePickerDialog.show();
                }
                
                else {
                    Toast.makeText(getContext(), "No puedes añadir más de 10 horas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.downButton.setOnClickListener(v -> {
            if(units > 1) {
                units--;
                binding.numberText.setText(String.valueOf(units));
            }
        });

        binding.upButton.setOnClickListener(v -> {
            units++;
            binding.numberText.setText(String.valueOf(units));
        });

    }

    protected boolean fillData(Medication medication) {
        medication.setUnitsPerDosis(units);
        medication.setHours(recyclerViewAdapter.getHourList());
        return true;
    }
}