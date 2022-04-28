package com.example.simplemedicine.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notification_table")
public class NotificationModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private HourModel hour;
    private int medicationId;
    private DateModel endDate;
    private boolean completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HourModel getHour() {
        return hour;
    }

    public void setHour(HourModel hour) {
        this.hour = hour;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    public DateModel getEndDate() {
        return endDate;
    }

    public void setEndDate(DateModel endDate) {
        this.endDate = endDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
