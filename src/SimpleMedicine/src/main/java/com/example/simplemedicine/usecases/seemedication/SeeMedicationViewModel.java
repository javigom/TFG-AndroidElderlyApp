package com.example.simplemedicine.usecases.seemedication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.provider.room.repository.Repository;

public class SeeMedicationViewModel extends AndroidViewModel {

    private final Repository repository;

    public SeeMedicationViewModel(Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void delete(Medication medication) {
        repository.delete(medication);
    }

}
