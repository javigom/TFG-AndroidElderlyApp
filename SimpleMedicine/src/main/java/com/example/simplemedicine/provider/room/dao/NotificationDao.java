package com.example.simplemedicine.provider.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.model.NotificationModel;

import java.util.List;
import java.util.Map;

@Dao
public interface NotificationDao {

    @Insert
    void Insert(NotificationModel notification);

    @Update
    void Update(NotificationModel notificationModel);

    @Query("DELETE FROM notification_table WHERE medicationId = :medicationId")
    void DeleteNotificationsByMedicationId(Long medicationId);

    @Query("SELECT * FROM notification_table")
    LiveData<List<NotificationModel>> getAllNotifications();

}