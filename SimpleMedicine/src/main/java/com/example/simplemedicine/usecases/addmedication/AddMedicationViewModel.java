package com.example.simplemedicine.usecases.addmedication;

import androidx.lifecycle.ViewModel;

import com.example.simplemedicine.R;
import com.example.simplemedicine.databinding.ActivityAddMedicationBinding;
import com.example.simplemedicine.databinding.FragmentAddMedicationPage1Binding;
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage1Fragment;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage1Router;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage2Router;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage3Router;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage4Router;
import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

import java.util.ArrayList;
import java.util.List;

public class AddMedicationViewModel extends ViewModel {

    private List<BaseFragmentRouter> fragments;
    private Medication medication;

    public AddMedicationViewModel() {
        fragments = new ArrayList<>();
        fragments.add(new AddMedicationPage1Router());
        fragments.add(new AddMedicationPage2Router());
        fragments.add(new AddMedicationPage3Router());
        fragments.add(new AddMedicationPage4Router());
        medication = new Medication();
    }

    public int getPages() {
        return fragments.size();
    }

    public List<BaseFragmentRouter> getFragments() {
        return fragments;
    }

    public Medication getMedication() {
        return medication;
    }
}
