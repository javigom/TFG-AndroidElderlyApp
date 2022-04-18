package com.example.simplemedicine.provider.medication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.util.converter.DateConverters;
import com.example.simplemedicine.util.converter.HourListConverters;
import com.example.simplemedicine.util.converter.WeekMapConverters;

@Database(entities = Medication.class,version = 2,exportSchema = false)
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
