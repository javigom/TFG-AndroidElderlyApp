package com.example.simplemedicine.usecases.seemedication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.provider.room.repository.MedicationRepo;

public class SeeMedicationViewModel extends AndroidViewModel {

    private final MedicationRepo repository;

    public SeeMedicationViewModel(Application application) {
        super(application);
        repository = new MedicationRepo(application);
    }

    public void delete(Medication medication) {
        repository.delete(medication);
    }

}
