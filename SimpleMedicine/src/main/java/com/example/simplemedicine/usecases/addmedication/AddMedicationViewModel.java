package com.example.simplemedicine.usecases.addmedication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.provider.medication.MedicationRepo;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage1Router;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage2Router;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage3Router;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage4Router;
import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

import java.util.ArrayList;
import java.util.List;

public class AddMedicationViewModel extends AndroidViewModel {

    private final List<BaseFragmentRouter> fragments;
    private final Medication medication;
    private final MedicationRepo repository;

    public AddMedicationViewModel(Application application) {
        super(application);
        fragments = new ArrayList<>();
        fragments.add(new AddMedicationPage1Router());
        fragments.add(new AddMedicationPage2Router());
        fragments.add(new AddMedicationPage3Router());
        fragments.add(new AddMedicationPage4Router());
        medication = new Medication();
        repository = new MedicationRepo(application);
    }

    public void insert() {
        repository.insert(medication);
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
