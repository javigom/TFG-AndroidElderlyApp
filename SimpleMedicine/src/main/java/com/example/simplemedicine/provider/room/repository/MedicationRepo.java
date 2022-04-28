package com.example.simplemedicine.provider.room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.provider.room.dao.MedicationDao;
import com.example.simplemedicine.provider.room.database.MedicationDatabase;

import java.util.List;

public class MedicationRepo {

    private MedicationDao medicationDao;
    private LiveData<List<Medication>> allMedication;

    public MedicationRepo(Application application) {
        MedicationDatabase database = MedicationDatabase.getInstance(application);
        medicationDao = database.medicationDao();
        allMedication = medicationDao.getAllMedications();
    }

    public void insert(Medication medication) {
        new InsertMedicationAsyncTask(medicationDao).execute(medication);
    }
    public void update(Medication medication) {
        new UpdateMedicationAsyncTask(medicationDao).execute(medication);
    }
    public void delete(Medication medication) {
        new DeleteMedicationAsyncTask(medicationDao).execute(medication);
    }
    public void deleteAllMedications() {
        new DeleteAllMedicationsAsyncTask(medicationDao).execute();
    }
    public LiveData<List<Medication>> getAllMedication() {
        return allMedication;
    }


    private static class InsertMedicationAsyncTask extends AsyncTask<Medication, Void, Void> {
        private MedicationDao medicationDao;
        private InsertMedicationAsyncTask(MedicationDao medicationDao) {
            this.medicationDao = medicationDao;
        }
        @Override
        protected Void doInBackground(Medication... medications) {
            medicationDao.Insert(medications[0]);
            return null;
        }
    }
    private static class UpdateMedicationAsyncTask extends AsyncTask<Medication, Void, Void> {
        private MedicationDao medicationDao;
        private UpdateMedicationAsyncTask(MedicationDao medicationDao) {
            this.medicationDao = medicationDao;
        }
        @Override
        protected Void doInBackground(Medication... medications) {
            medicationDao.Update(medications[0]);
            return null;
        }
    }
    private static class DeleteMedicationAsyncTask extends AsyncTask<Medication, Void, Void> {
        private MedicationDao medicationDao;
        private DeleteMedicationAsyncTask(MedicationDao medicationDao) {
            this.medicationDao = medicationDao;
        }
        @Override
        protected Void doInBackground(Medication... medications) {
            medicationDao.Delete(medications[0]);
            return null;
        }
    }
    private static class DeleteAllMedicationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private MedicationDao medicationDao;
        private DeleteAllMedicationsAsyncTask(MedicationDao medicationDao) {
            this.medicationDao = medicationDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            medicationDao.DeleteAllMedications();
            return null;
        }
    }
}
