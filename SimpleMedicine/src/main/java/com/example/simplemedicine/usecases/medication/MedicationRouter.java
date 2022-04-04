package com.example.simplemedicine.usecases.medication;

import androidx.fragment.app.Fragment;

import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

public class MedicationRouter implements BaseFragmentRouter {

    private MedicationFragment instance;

    public MedicationRouter() {
        instance = null;
    }

    @Override
    public Fragment fragment() {
       if(instance == null) {
           instance = new MedicationFragment();
       }

       return instance;
    }
}
