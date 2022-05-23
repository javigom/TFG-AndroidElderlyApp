package com.example.simplemedicine.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.simplemedicine.util.WeekDayEnum;

import java.io.Serializable;

@Entity(tableName = "notification_table")
public class NotificationModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private HourModel hour;
    private Long medicationId;
    private WeekDayEnum weekDay;
    private DateModel startDate;
    private DateModel endDate;
    private String medicationName;
    private int medicationColor;
    private boolean completed;

    public NotificationModel(HourModel hour, Long medicationId, WeekDayEnum weekDay, DateModel startDate, DateModel endDate, String medicationName, int medicationColor, boolean completed) {
        this.hour = hour;
        this.medicationId = medicationId;
        this.weekDay = weekDay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.medicationName = medicationName;
        this.medicationColor = medicationColor;
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

    public WeekDayEnum getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDayEnum weekDay) {
        this.weekDay = weekDay;
    }

    public DateModel getStartDate() {
        return startDate;
    }

    public void setStartDate(DateModel startDate) {
        this.startDate = startDate;
    }

    public DateModel getEndDate() {
        return endDate;
    }

    public void setEndDate(DateModel endDate) {
        this.endDate = endDate;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public int getMedicationColor() {
        return medicationColor;
    }

    public void setMedicationColor(int medicationColor) {
        this.medicationColor = medicationColor;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
