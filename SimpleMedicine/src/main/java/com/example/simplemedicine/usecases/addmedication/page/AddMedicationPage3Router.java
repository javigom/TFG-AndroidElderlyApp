package com.example.simplemedicine.usecases.addmedication.page;

import androidx.fragment.app.Fragment;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

public class AddMedicationPage3Router implements BaseFragmentRouter {

    private AddMedicationPage3Fragment instance;

    public AddMedicationPage3Router() {
        instance = null;
    }

    @Override
    public Fragment fragment() {
        if(instance == null) {
            instance = new AddMedicationPage3Fragment();
        }

        return instance;
    }

    @Override
    public boolean fillData(Medication medication) {
        return true;
    }
}
