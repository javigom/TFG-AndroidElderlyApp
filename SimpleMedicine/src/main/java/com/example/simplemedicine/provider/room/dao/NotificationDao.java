package com.example.simplemedicine.provider.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.simplemedicine.model.NotificationModel;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
    void Insert(NotificationModel notification);

    @Query("DELETE FROM notification_table WHERE medicationId = :medicationId")
    void DeleteNotificationsByMedicationId(Long medicationId);

    @Query("SELECT * FROM notification_table")
    LiveData<List<NotificationModel>> getAllNotifications();

}