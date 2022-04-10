package com.example.simplemedicine.model;

import com.example.simplemedicine.util.WeekDaysEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Medication implements Serializable {

    private String name;
    private String description;
    private HashMap<WeekDaysEnum, Boolean> weekDays;
    private ArrayList<Hour> hours;
    private double unitsPerDosis;
    private DateModel startDate;
    private DateModel endDate;
    private String photo;

    public Medication(String name, String description, HashMap<WeekDaysEnum, Boolean> weekDays,
                      ArrayList<Hour> hours, double unitsPerDosis, DateModel startDate, DateModel endDate, String photo) {
        this.name = name;
        this.description = description;
        this.weekDays = weekDays;
        this.hours = hours;
        this.unitsPerDosis = unitsPerDosis;
        this.startDate = startDate;
        this.endDate = endDate;
        this.photo = photo;

    }

    public Medication() {
        this.name = name;
        description = "";
        weekDays = new HashMap<>();
        for(int i = 0; i < WeekDaysEnum.values().length; i++)
            weekDays.put(WeekDaysEnum.getWeekDay(i), false);
        hours = new ArrayList<>();
        this.unitsPerDosis = -1;
        this.startDate = null;
        this.endDate = null;
        this.photo = null;
    }

    @Override
    public String toString() {
        return  "name=" + name + '\n' +
                "description=" + description + '\n' +
                "weekDays=" + weekDays + '\n' +
                "hours=" + hours + '\n' +
                "unitsPerDosis=" + unitsPerDosis + '\n' +
                "startDate=" + startDate + '\n' +
                "endDate=" + endDate + '\n';
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

    public ArrayList<Hour> getHours() {
        return hours;
    }

    public void setHours(ArrayList<Hour> hours) {
        this.hours = hours;
    }

    public double getUnitsPerDosis() {
        return unitsPerDosis;
    }

    public void setUnitsPerDosis(double unitsPerDosis) {
        this.unitsPerDosis = unitsPerDosis;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
