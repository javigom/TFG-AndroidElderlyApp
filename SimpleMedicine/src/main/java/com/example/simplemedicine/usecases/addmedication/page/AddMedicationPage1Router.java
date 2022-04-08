package com.example.simplemedicine.usecases.addmedication.page;

import androidx.fragment.app.Fragment;

import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

public class AddMedicationPage1Router implements BaseFragmentRouter {

    private AddMedicationPage1Fragment instance;

    public AddMedicationPage1Router() {
        instance = null;
    }

    @Override
    public Fragment fragment() {
        if(instance == null) {
            instance = new AddMedicationPage1Fragment();
        }

        return instance;
    }
}
