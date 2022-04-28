package com.example.simplemedicine.provider.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simplemedicine.model.Medication;

import java.util.List;

@Dao
public interface MedicationDao {

    @Insert
    void Insert(Medication medication);

    @Update
    void Update(Medication medication);

    @Delete
    void Delete(Medication medication);

    @Query("DELETE FROM medication_table")
    void DeleteAllMedications();

    @Query("SELECT * FROM medication_table")
    LiveData<List<Medication>> getAllMedications();

}
