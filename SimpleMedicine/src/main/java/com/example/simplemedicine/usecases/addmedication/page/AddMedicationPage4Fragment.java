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

public class AddMedicationPage4Fragment extends Fragment {

    private FragmentAddMedicationPage4Binding binding;
    private DateModel start, end;
    private boolean editMode;
    private Medication medication;

    public AddMedicationPage4Fragment(boolean editMode, Medication medication) {
        this.editMode = editMode;
        this.medication = medication;
    }

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
        initView();
    }

    private void initView() {
        Calendar calendar1 = Calendar.getInstance();
        if(editMode) {
            start = medication.getStartDate();
            end = medication.getEndDate();
            binding.startDate.setText(medication.getStartDate().toString());
            if(medication.getEndDate().getDay() != -1) {
                binding.endDate.setText(medication.getEndDate().toString());
                binding.endButton.setEnabled(true);
            }
            else {
                binding.noEndCheckbox.setChecked(true);
                binding.endButton.setEnabled(false);
            }

            calendar1.set(start.getYear(), start.getMonth(), start.getDay());
        }
        else {
            binding.notificationCheckbox.setChecked(true);
        }

        binding.startButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view12, year, month, dayOfMonth) -> {
                start = new DateModel(year, month, dayOfMonth);
                if(!binding.noEndCheckbox.isChecked()) {
                    binding.endButton.setEnabled(true);
                    binding.noEndCheckbox.setEnabled(true);
                    binding.endDate.setText("");
                }
                binding.startDate.setText(start.toString());
            }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.getDatePicker().setMinDate(calendar1.getTime().getTime());
            datePickerDialog.show();
        });

        if(!editMode) {
            binding.endButton.setEnabled(false);
            binding.noEndCheckbox.setEnabled(false);
        }
        binding.endButton.setOnClickListener(v -> {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(start.getYear(), start.getMonth(), start.getDay() + 1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {
                end = new DateModel(year, month, dayOfMonth);
                binding.endDate.setText(end.toString());
                calendar2.set(end.getYear(), end.getMonth(), end.getDay());
            }, calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(calendar2.getTime().getTime());
            datePickerDialog.show();
        });

        binding.noEndCheckbox.setOnClickListener(v -> {
            binding.endButton.setEnabled(!binding.noEndCheckbox.isChecked());
            binding.endDate.setText("");
        });
    }

    protected boolean setData(Medication medication) {
        if(start == null || (!binding.noEndCheckbox.isChecked() && end == null)) {
            return false;
        }

        medication.setStartDate(start);
        medication.setEndDate(binding.noEndCheckbox.isChecked()? new DateModel(-1, -1, -1): end);
        medication.setNotifications(binding.notificationCheckbox.isChecked());
        return true;
    }
}