package com.example.simplemedicine.usecases.medication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simplemedicine.model.MedicationModel;

import java.util.ArrayList;
import java.util.List;

public class MedicationViewModel extends ViewModel {

    // ATTRIBUTES

    private MutableLiveData<List<MedicationModel>> medicationListLiveData;

    // CONSTRUCTOR

    public MedicationViewModel() {
        medicationListLiveData = new MutableLiveData<>();
        ArrayList<MedicationModel> medicationList = new ArrayList<>();
        medicationList.add(new MedicationModel("Paracetamol"));
        medicationList.get(0).setDescription("600MG");
        medicationList.add(new MedicationModel("Ibuprofeno"));
        medicationList.get(1).setDescription("400MG");
        medicationListLiveData.setValue(medicationList);
    }

    // METHODS

    public LiveData<List<MedicationModel>> getMedicationList() {
        return medicationListLiveData;
    }


}
