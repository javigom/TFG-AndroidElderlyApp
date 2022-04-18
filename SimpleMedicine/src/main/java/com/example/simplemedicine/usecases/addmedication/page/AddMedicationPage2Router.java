package com.example.simplemedicine.usecases.addmedication.page;

import androidx.fragment.app.Fragment;

import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

public class AddMedicationPage2Router implements BaseFragmentRouter {

    private AddMedicationPage2Fragment instance;
    private boolean editMode;
    private Medication medication;

    public AddMedicationPage2Router(boolean editMode, Medication medication) {
        instance = null;
        this.editMode = editMode;
        this.medication = medication;
    }

    @Override
    public Fragment fragment() {
        if(instance == null) {
            instance = new AddMedicationPage2Fragment(editMode, medication);
        }

        return instance;
    }

    @Override
    public boolean fillData(Medication medication) {
        return instance.setData(medication);
    }
}
