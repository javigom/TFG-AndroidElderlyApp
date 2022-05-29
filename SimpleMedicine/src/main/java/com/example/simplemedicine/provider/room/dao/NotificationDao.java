package com.example.simplemedicine.provider.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.util.WeekDayEnum;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
    void Insert(NotificationModel notification);

    @Query("UPDATE notification_table SET completed = 1 WHERE id IN (:ids)")
    void Update(List<Long> ids);

    @Query("UPDATE notification_table SET completed = 0")
    void RestartNotifications();

    @Query("DELETE FROM notification_table WHERE medicationId = :medicationId")
    void DeleteNotificationsByMedicationId(Long medicationId);

    @Query("SELECT * FROM notification_table")
    LiveData<List<NotificationModel>> getAllNotifications();

    @Query("SELECT * FROM notification_table WHERE weekDay = :todayWeekDay")
    LiveData<List<NotificationModel>> getTodayNotifications(WeekDayEnum todayWeekDay);

}