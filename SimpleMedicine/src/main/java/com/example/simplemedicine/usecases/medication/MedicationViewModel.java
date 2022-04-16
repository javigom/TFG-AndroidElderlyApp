package com.example.simplemedicine.usecases.medication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.provider.medication.MedicationRepo;

import java.util.List;

public class MedicationViewModel extends AndroidViewModel {

    // ATTRIBUTES

    private MedicationRepo repository;
    private LiveData<List<Medication>> allMedications;

    // CONSTRUCTOR

    public MedicationViewModel(@NonNull Application application) {
        super(application);
        repository = new MedicationRepo(application);
        allMedications = repository.getAllMedication();

//        medicationListLiveData = new MutableLiveData<>();
//        ArrayList<Medication> medicationList = new ArrayList<>();
//
//        Medication medication = new Medication();
//        medication.setName("Paracetamol");
//        medication.setDescription("1000MG");
//        medicationList.add(medication);
//
//        Medication medication2 = new Medication();
//        medication2.setName("Ibuprofeno");
//        medication2.setDescription("600MG");
//        medicationList.add(medication2);
//
//        medicationListLiveData.setValue(medicationList);
    }

    // METHODS

    public LiveData<List<Medication>> getAllMedications() {
        return allMedications;
    }

}
