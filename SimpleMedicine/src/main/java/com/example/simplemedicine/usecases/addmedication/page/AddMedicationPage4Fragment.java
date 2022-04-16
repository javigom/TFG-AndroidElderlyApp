package com.example.simplemedicine.usecases.addmedication.page;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simplemedicine.databinding.FragmentAddMedicationPage4Binding;
import com.example.simplemedicine.model.date.DateModel;
import com.example.simplemedicine.model.medication.Medication;

import java.util.Calendar;
import java.util.Date;

public class AddMedicationPage4Fragment extends Fragment {

    private FragmentAddMedicationPage4Binding binding;
    private DateModel start, end;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        binding = FragmentAddMedicationPage4Binding.inflate(inflater, container, false);
        start = null;
        end = null;
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

        binding.startButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view12, year, month, dayOfMonth) -> {
                start = new DateModel(year, month, dayOfMonth);
                binding.endButton.setEnabled(true);
                binding.startDate.setText(start.toString());
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            datePickerDialog.show();
        });

        binding.endButton.setEnabled(false);
        binding.endButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(start.getYear(), start.getMonth(), start.getDay() + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {
                end = new DateModel(year, month, dayOfMonth);
                binding.endDate.setText(end.toString());
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime());
            datePickerDialog.show();
        });

        binding.noEndCheckbox.setOnClickListener(v -> binding.endButton.setEnabled(!binding.noEndCheckbox.isChecked()));
    }

    protected boolean setData(Medication medication) {
        if(start == null || (!binding.noEndCheckbox.isChecked() && end == null)) {
            return false;
        }
        medication.setStartDate(start);
        medication.setEndDate(binding.noEndCheckbox.isChecked()? new DateModel(-1, -1, -1): end);

        return true;
    }
}