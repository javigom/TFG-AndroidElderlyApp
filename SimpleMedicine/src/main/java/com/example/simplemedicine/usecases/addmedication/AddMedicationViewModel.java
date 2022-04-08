package com.example.simplemedicine.usecases.addmedication;

import androidx.lifecycle.ViewModel;

import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage1Router;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage2Router;
import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

import java.util.ArrayList;
import java.util.List;

public class AddMedicationViewModel extends ViewModel {

    private List<BaseFragmentRouter> fragments;

    public AddMedicationViewModel() {
        fragments = new ArrayList<>();
        fragments.add(new AddMedicationPage1Router());
        fragments.add(new AddMedicationPage2Router());
    }

    public int getPages() {
        return fragments.size();
    }

    public List<BaseFragmentRouter> getFragments() {
        return fragments;
    }
}
