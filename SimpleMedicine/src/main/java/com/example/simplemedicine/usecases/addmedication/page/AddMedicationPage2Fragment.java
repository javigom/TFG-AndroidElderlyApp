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
import java.util.List;

public class AddMedicationPage2Fragment extends Fragment {

    FragmentAddMedicationPage2Binding binding;

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
        }

        return res;
    }
}
