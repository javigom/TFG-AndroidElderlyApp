package com.example.simplemedicine.provider.room.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.provider.room.converter.DateConverters;
import com.example.simplemedicine.provider.room.converter.HourListConverters;
import com.example.simplemedicine.provider.room.converter.WeekMapConverters;
import com.example.simplemedicine.provider.room.dao.MedicationDao;

@Database(entities = Medication.class, version = 3, exportSchema = false)
@TypeConverters({HourListConverters.class, DateConverters.class, WeekMapConverters.class})
public abstract class MedicationDatabase extends RoomDatabase {

    private static MedicationDatabase instance; //only one interface

    public abstract MedicationDao medicationDao();

    public static synchronized MedicationDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MedicationDatabase.class , "Medication_name")
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
