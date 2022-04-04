package com.example.simplemedicine.model;

import androidx.recyclerview.widget.SortedList;

import com.example.simplemedicine.util.WeekDaysEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MedicationModel implements Serializable {

    private String name;
    private String description;
    private HashMap<WeekDaysEnum, Boolean> weekDays;
    private SortedList<HourModel> hours;
    private double unitsPerDosis;
    private Date startDate;
    private Date endDate;
    private String photo;

    public MedicationModel(String name, String description, HashMap<WeekDaysEnum, Boolean> weekDays,
                           SortedList<HourModel> hours, double unitsPerDosis, Date startDate, Date endDate, String photo) {
        this.name = name;
        this.description = description;
        this.weekDays = weekDays;
        this.hours = hours;
        this.unitsPerDosis = unitsPerDosis;
        this.startDate = startDate;
        this.endDate = endDate;
        this.photo = photo;

    }

    public MedicationModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<WeekDaysEnum, Boolean> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(HashMap<WeekDaysEnum, Boolean> weekDays) {
        this.weekDays = weekDays;
    }

    public SortedList<HourModel> getHours() {
        return hours;
    }

    public void setHours(SortedList<HourModel> hours) {
        this.hours = hours;
    }

    public double getUnitsPerDosis() {
        return unitsPerDosis;
    }

    public void setUnitsPerDosis(double unitsPerDosis) {
        this.unitsPerDosis = unitsPerDosis;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
