package com.example.simplemedicine.usecases.addmedication.page;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

public class AddMedicationPage2Router implements BaseFragmentRouter {

    private AddMedicationPage2Fragment instance;

    public AddMedicationPage2Router() {
        instance = null;
    }

    @Override
    public Fragment fragment() {
        if(instance == null) {
            instance = new AddMedicationPage2Fragment();
        }

        return instance;
    }

    @Override
    public boolean fillData(Medication medication) {
        return instance.setData(medication);
    }
}
