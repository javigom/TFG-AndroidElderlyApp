package com.example.simplemedicine.usecases.addmedication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.provider.room.repository.MedicationRepo;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage1Router;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage2Router;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage3Router;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPage4Router;
import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

import java.util.ArrayList;
import java.util.List;

public class AddMedicationViewModel extends AndroidViewModel {

    private final List<BaseFragmentRouter> fragments;
    private Medication medication;
    private final MedicationRepo repository;
    private boolean editMode;

    public AddMedicationViewModel(Application application) {
        super(application);
        fragments = new ArrayList<>();
        repository = new MedicationRepo(application);
        medication = new Medication();
        editMode = false;
    }

    public void insert() {
        repository.insert(medication);
    }

    public void update() {
        repository.update(medication);
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
        editMode = true;
    }

    public void initPages() {
        fragments.add(new AddMedicationPage1Router(editMode, medication));
        fragments.add(new AddMedicationPage2Router(editMode, medication));
        fragments.add(new AddMedicationPage3Router(editMode, medication));
        fragments.add(new AddMedicationPage4Router(editMode, medication));
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
