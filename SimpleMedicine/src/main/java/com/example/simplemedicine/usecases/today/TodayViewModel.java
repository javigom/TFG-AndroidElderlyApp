package com.example.simplemedicine.usecases.today;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.simplemedicine.model.hour.HourModel;
import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.provider.medication.MedicationRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TodayViewModel extends AndroidViewModel {

    // ATTRIBUTES

    private MedicationRepo repository;
    private LiveData<List<Medication>> allMedications;


    // CONSTRUCTOR

    public TodayViewModel(@NonNull Application application) {
        super(application);
        repository = new MedicationRepo(application);
        allMedications = repository.getAllMedication();
    }

    // METHODS

    public LiveData<List<Medication>> getAllMedications() {
        return allMedications;
    }
}
