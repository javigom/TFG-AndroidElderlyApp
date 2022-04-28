package com.example.simplemedicine.provider.room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.provider.room.database.NotificationDatabase;
import com.example.simplemedicine.provider.room.dao.NotificationDao;

import java.util.List;

public class NotificationRepo {

    private NotificationDao notificationDao;
    private LiveData<List<NotificationModel>> allNotification;

    public NotificationRepo(Application application) {
        NotificationDatabase database = NotificationDatabase.getInstance(application);
        notificationDao = database.notificationDao();
        allNotification = notificationDao.getAllNotifications();
    }

    public void insert(NotificationModel notification) {
        new InsertNotificationAsyncTask(notificationDao).execute(notification);
    }
    public void update(NotificationModel notification) {
        new UpdateNotificationAsyncTask(notificationDao).execute(notification);
    }
    public void delete(NotificationModel notification) {
        new DeleteNotificationAsyncTask(notificationDao).execute(notification);
    }
    public void deleteAllNotifications() {
        new DeleteAllNotificationsAsyncTask(notificationDao).execute();
    }
    public LiveData<List<NotificationModel>> getAllNotification() {
        return allNotification;
    }


    private static class InsertNotificationAsyncTask extends AsyncTask<NotificationModel, Void, Void> {
        private NotificationDao notificationDao;
        private InsertNotificationAsyncTask(NotificationDao notificationDao) {
            this.notificationDao = notificationDao;
        }
        @Override
        protected Void doInBackground(NotificationModel... notifications) {
            notificationDao.Insert(notifications[0]);
            return null;
        }
    }
    private static class UpdateNotificationAsyncTask extends AsyncTask<NotificationModel, Void, Void> {
        private NotificationDao notificationDao;
        private UpdateNotificationAsyncTask(NotificationDao notificationDao) {
            this.notificationDao = notificationDao;
        }
        @Override
        protected Void doInBackground(NotificationModel... notifications) {
            notificationDao.Update(notifications[0]);
            return null;
        }
    }
    private static class DeleteNotificationAsyncTask extends AsyncTask<NotificationModel, Void, Void> {
        private NotificationDao notificationDao;
        private DeleteNotificationAsyncTask(NotificationDao notificationDao) {
            this.notificationDao = notificationDao;
        }
        @Override
        protected Void doInBackground(NotificationModel... notifications) {
            notificationDao.Delete(notifications[0]);
            return null;
        }
    }
    private static class DeleteAllNotificationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDao notificationDao;
        private DeleteAllNotificationsAsyncTask(NotificationDao notificationDao) {
            this.notificationDao = notificationDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            notificationDao.DeleteAllNotifications();
            return null;
        }
    }
}
