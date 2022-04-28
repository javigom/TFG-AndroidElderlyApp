package com.example.simplemedicine.provider.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simplemedicine.model.NotificationModel;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
    void Insert(NotificationModel notification);

    @Update
    void Update(NotificationModel notification);

    @Delete
    void Delete(NotificationModel notification);

    @Query("DELETE FROM notification_table")
    void DeleteAllNotifications();

    @Query("SELECT * FROM notification_table")
    LiveData<List<NotificationModel>> getAllNotifications();

}