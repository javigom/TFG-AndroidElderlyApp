package com.example.simplemedicine.usecases.addmedication.page;

import androidx.fragment.app.Fragment;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

public class AddMedicationPage4Router implements BaseFragmentRouter {

    private AddMedicationPage4Fragment instance;

    public AddMedicationPage4Router() {
        instance = null;
    }

    @Override
    public Fragment fragment() {
        if(instance == null) {
            instance = new AddMedicationPage4Fragment();
        }

        return instance;
    }

    @Override
    public boolean fillData(Medication medication) {
        return instance.setData(medication);
    }
}
