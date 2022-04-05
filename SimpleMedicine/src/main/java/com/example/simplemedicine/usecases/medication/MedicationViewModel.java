package com.example.simplemedicine.usecases.medication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simplemedicine.model.Medication;

import java.util.ArrayList;
import java.util.List;

public class MedicationViewModel extends ViewModel {

    // ATTRIBUTES

    private MutableLiveData<List<Medication>> medicationListLiveData;

    // CONSTRUCTOR

    public MedicationViewModel() {
        medicationListLiveData = new MutableLiveData<>();
        ArrayList<Medication> medicationList = new ArrayList<>();

        Medication medication = new Medication();
        medication.setName("Paracetamol");
        medication.setDescription("1000MG");
        medicationList.add(medication);

        Medication medication2 = new Medication();
        medication2.setName("Ibuprofeno");
        medication2.setDescription("600MG");
        medicationList.add(medication2);

        medicationListLiveData.setValue(medicationList);
    }

    // METHODS

    public LiveData<List<Medication>> getMedicationList() {
        return medicationListLiveData;
    }


}
