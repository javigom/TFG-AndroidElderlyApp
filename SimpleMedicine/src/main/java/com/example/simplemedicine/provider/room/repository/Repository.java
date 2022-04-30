package com.example.simplemedicine.provider.room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.simplemedicine.model.DateModel;
import com.example.simplemedicine.model.HourModel;
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.provider.room.dao.MedicationDao;
import com.example.simplemedicine.provider.room.dao.NotificationDao;
import com.example.simplemedicine.provider.room.database.SimpleMedicineDatabase;
import com.example.simplemedicine.util.WeekDaysEnum;

import java.util.Calendar;
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
        SimpleMedicineDatabase database = SimpleMedicineDatabase.getInstance(application);
        medicationDao = database.medicationDao();
        notificationDao = database.notificationDao();
        allMedication = medicationDao.getAllMedications();
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
    public void update(NotificationModel notification) {
        new UpdateNotificationAsyncTask(notificationDao).execute(notification);
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
            Calendar calendar = Calendar.getInstance();
            DateModel todayDate = new DateModel(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            int weekday = (calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7;
            for(HourModel hour: medications[0].getHours()) {
                Boolean isDayWeek = medications[0].getWeekDays().get(WeekDaysEnum.getWeekDay(weekday));
                if(isDayWeek != null) {
                    if(medications[0].getStartDate().compareTo(todayDate) <= 0 && medications[0].getEndDate().compareTo(todayDate) >= 0 && isDayWeek) {
                        notificationDao.Insert(new NotificationModel(hour, id, medications[0].getName(), medications[0].getColor(), false));
                    }
                }
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
            Calendar calendar = Calendar.getInstance();
            DateModel todayDate = new DateModel(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            int weekday = (calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7;
            for(HourModel hour: medications[0].getHours()) {
                Boolean isDayWeek = medications[0].getWeekDays().get(WeekDaysEnum.getWeekDay(weekday));
                if(medications[0].getStartDate().compareTo(todayDate) <= 0 && medications[0].getEndDate().compareTo(todayDate) >= 0 && isDayWeek) {
                    notificationDao.Insert(new NotificationModel(hour, medications[0].getId(), medications[0].getName(), medications[0].getColor(), false));
                }
            }
            return null;
        }
    }
    private static class UpdateNotificationAsyncTask extends AsyncTask<NotificationModel, Void, Void> {
        private final NotificationDao notificationDao;
        private UpdateNotificationAsyncTask(NotificationDao notificationDao) {
            this.notificationDao = notificationDao;
        }
        @Override
        protected Void doInBackground(NotificationModel... notifications) {
            notificationDao.Update(notifications[0]);
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
