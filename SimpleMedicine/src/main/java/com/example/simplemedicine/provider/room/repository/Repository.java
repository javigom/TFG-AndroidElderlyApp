package com.example.simplemedicine.provider.room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.simplemedicine.model.HourModel;
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.provider.room.dao.MedicationDao;
import com.example.simplemedicine.provider.room.dao.NotificationDao;
import com.example.simplemedicine.provider.room.database.MedicationDatabase;
import com.example.simplemedicine.provider.room.database.NotificationDatabase;

import java.util.List;

public class Repository {

    // ATTRIBUTES

    private final MedicationDao medicationDao;
    private final LiveData<List<Medication>> allMedication;
    private final NotificationDao notificationDao;
    private final LiveData<List<NotificationModel>> allNotification;


    // CONSTRUCTOR

    public Repository(Application application) {
        // Medication database
        MedicationDatabase database = MedicationDatabase.getInstance(application);
        medicationDao = database.medicationDao();
        allMedication = medicationDao.getAllMedications();

        // Notification database
        NotificationDatabase notificationDatabase = NotificationDatabase.getInstance(application);
        notificationDao = notificationDatabase.notificationDao();
        allNotification = notificationDao.getAllNotifications();
    }


    // METHODS

    // Insertion
    public void insert(Medication medication) {
        new InsertMedicationAsyncTask(medicationDao, notificationDao).execute(medication);
    }
    // Update
    public void update(Medication medication) {
        new UpdateMedicationAsyncTask(medicationDao, notificationDao).execute(medication);
    }
    // Delete
    public void delete(Medication medication) {
        new DeleteMedicationAsyncTask(medicationDao, notificationDao).execute(medication);
    }
    // GetAll
    public LiveData<List<Medication>> getAllMedication() {
        return allMedication;
    }
    public LiveData<List<NotificationModel>> getAllNotification() {
        return allNotification;
    }


    // Nested classes for async

    private static class InsertMedicationAsyncTask extends AsyncTask<Medication, Void, Void> {
        private final MedicationDao medicationDao;
        private final NotificationDao notificationDao;
        private InsertMedicationAsyncTask(MedicationDao medicationDao, NotificationDao notificationDao) {
            this.medicationDao = medicationDao;
            this.notificationDao = notificationDao;
        }
        @Override
        protected Void doInBackground(Medication... medications) {
            Long id = medicationDao.Insert(medications[0]);
            for(HourModel hour: medications[0].getHours()) {
                notificationDao.Insert(new NotificationModel(hour, id, false));
            }
            return null;
        }
    }
    private static class UpdateMedicationAsyncTask extends AsyncTask<Medication, Void, Void> {
        private final MedicationDao medicationDao;
        private final NotificationDao notificationDao;
        private UpdateMedicationAsyncTask(MedicationDao medicationDao, NotificationDao notificationDao) {
            this.medicationDao = medicationDao;
            this.notificationDao = notificationDao;
        }
        @Override
        protected Void doInBackground(Medication... medications) {
            medicationDao.Update(medications[0]);
            notificationDao.DeleteNotificationsByMedicationId(medications[0].getId());
            for(HourModel hour: medications[0].getHours()) {
                notificationDao.Insert(new NotificationModel(hour, medications[0].getId(), false));
            }
            return null;
        }
    }
    private static class DeleteMedicationAsyncTask extends AsyncTask<Medication, Void, Void> {
        private final MedicationDao medicationDao;
        private final NotificationDao notificationDao;
        private DeleteMedicationAsyncTask(MedicationDao medicationDao, NotificationDao notificationDao) {
            this.medicationDao = medicationDao;
            this.notificationDao = notificationDao;
        }

        @Override
        protected Void doInBackground(Medication... medications) {
            medicationDao.Delete(medications[0]);
            notificationDao.DeleteNotificationsByMedicationId(medications[0].getId());
            return null;
        }
    }

}
