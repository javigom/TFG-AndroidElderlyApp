package com.example.simplemedicine.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notification_table")
public class NotificationModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private HourModel hour;
    private Long medicationId;
    private boolean completed;

    public NotificationModel(HourModel hour, Long medicationId, boolean completed) {
        this.hour = hour;
        this.medicationId = medicationId;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HourModel getHour() {
        return hour;
    }

    public void setHour(HourModel hour) {
        this.hour = hour;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
