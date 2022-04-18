package com.example.simplemedicine.usecases.addmedication.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simplemedicine.databinding.FragmentAddMedicationPage2Binding;
import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.model.medication.WeekDaysEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddMedicationPage2Fragment extends Fragment {

    private FragmentAddMedicationPage2Binding binding;
    private boolean editMode;
    private Medication medication;

    public AddMedicationPage2Fragment(boolean editMode, Medication medication) {
        this.editMode = editMode;
        this.medication = medication;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        binding = FragmentAddMedicationPage2Binding.inflate(inflater, container, false);
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
        if(editMode) {
            binding.checkMonday.setChecked(medication.getWeekDays().get(WeekDaysEnum.MONDAY));
            binding.checkTuesday.setChecked(medication.getWeekDays().get(WeekDaysEnum.TUESDAY));
            binding.checkWednesday.setChecked(medication.getWeekDays().get(WeekDaysEnum.WEDNESDAY));
            binding.checkThursday.setChecked(medication.getWeekDays().get(WeekDaysEnum.THURSDAY));
            binding.checkFriday.setChecked(medication.getWeekDays().get(WeekDaysEnum.FRIDAY));
            binding.checkSaturday.setChecked(medication.getWeekDays().get(WeekDaysEnum.SATURDAY));
            binding.checkSunday.setChecked(medication.getWeekDays().get(WeekDaysEnum.SUNDAY));
        }
    }

    protected boolean setData(Medication medication) {
        boolean res = false;

        List<CheckBox> checkBoxList = new ArrayList<>();
        checkBoxList.add(binding.checkMonday);
        checkBoxList.add(binding.checkTuesday);
        checkBoxList.add(binding.checkWednesday);
        checkBoxList.add(binding.checkThursday);
        checkBoxList.add(binding.checkFriday);
        checkBoxList.add(binding.checkSaturday);
        checkBoxList.add(binding.checkSunday);

        for(int i = 0; i < checkBoxList.size(); i++) {
            if(checkBoxList.get(i).isChecked()) {
                medication.getWeekDays().put(WeekDaysEnum.getWeekDay(i), true);
                res = true;
            }
            else {
                medication.getWeekDays().put(WeekDaysEnum.getWeekDay(i), false);
            }
        }

        return res;
    }
}
