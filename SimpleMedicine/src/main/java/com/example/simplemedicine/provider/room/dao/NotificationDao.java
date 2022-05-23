package com.example.simplemedicine.provider.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.util.WeekDayEnum;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
    void Insert(NotificationModel notification);

    @Update
    void Update(NotificationModel notificationModel);

    @Query("DELETE FROM notification_table")
    void DeleteAllNotifications();

    @Query("DELETE FROM notification_table WHERE medicationId = :medicationId")
    void DeleteNotificationsByMedicationId(Long medicationId);

    @Query("SELECT * FROM notification_table")
    LiveData<List<NotificationModel>> getAllNotifications();

    @Query("SELECT * FROM notification_table WHERE weekDay = :todayWeekDay")
    LiveData<List<NotificationModel>> getTodayNotifications(WeekDayEnum todayWeekDay);

}