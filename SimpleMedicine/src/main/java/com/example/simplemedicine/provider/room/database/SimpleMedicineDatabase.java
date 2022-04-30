package com.example.simplemedicine.provider.room.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.provider.room.converter.DateConverters;
import com.example.simplemedicine.provider.room.converter.HourConverters;
import com.example.simplemedicine.provider.room.converter.HourListConverters;
import com.example.simplemedicine.provider.room.converter.WeekMapConverters;
import com.example.simplemedicine.provider.room.dao.MedicationDao;
import com.example.simplemedicine.provider.room.dao.NotificationDao;

@Database(entities = {Medication.class, NotificationModel.class}, version = 1, exportSchema = false)
@TypeConverters({HourListConverters.class, DateConverters.class, WeekMapConverters.class, HourConverters.class})
public abstract class SimpleMedicineDatabase extends RoomDatabase {

    private static SimpleMedicineDatabase instance; //only one interface

    public abstract MedicationDao medicationDao();
    public abstract NotificationDao notificationDao();

    public static synchronized SimpleMedicineDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), SimpleMedicineDatabase.class , "SimpleMedicine_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
