package com.example.simplemedicine.model.medication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.simplemedicine.model.date.DateModel;
import com.example.simplemedicine.model.hour.HourModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "medication_table")
public class Medication implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private Map<WeekDaysEnum, Boolean> weekDays;
    private List<HourModel> hours;
    private int unitsPerDosis;
    private DateModel startDate;
    private DateModel endDate;
    private String photo;
    private boolean notifications;

     public Medication() {
        description = "";
        weekDays = new HashMap<>();
        for(int i = 0; i < WeekDaysEnum.values().length; i++)
            weekDays.put(WeekDaysEnum.getWeekDay(i), false);
        hours = new ArrayList<>();
        this.unitsPerDosis = -1;
        this.notifications = false;
    }

    @Override
    public String toString() {
        return  "name=" + name + '\n' +
                "description=" + description + '\n' +
                "weekDays=" + weekDays + '\n' +
                "hours=" + hours + '\n' +
                "unitsPerDosis=" + unitsPerDosis + '\n' +
                "startDate=" + startDate + '\n' +
                "endDate=" + endDate + '\n' +
                "notifications=" + notifications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Map<WeekDaysEnum, Boolean> getWeekDays() {
        return weekDays;
    }

    public String getWeekDaysString() {
         ArrayList<String> days = new ArrayList<>();
         for(int i = 0; i < weekDays.size(); i++) {
             if(weekDays.get(WeekDaysEnum.getWeekDay(i)))
                 days.add(WeekDaysEnum.getWeekDayString(i));
         }
         return days.toString().replace("[", "").replace("]", "");
    }

    public void setWeekDays(Map<WeekDaysEnum, Boolean> weekDays) {
        this.weekDays = weekDays;
    }

    public List<HourModel> getHours() {
        return hours;
    }

    public String getHoursString() {
         return hours.toString().replace("[", "").replace("]", "");
    }

    public void setHours(List<HourModel> hours) {
        this.hours = hours;
    }

    public int getUnitsPerDosis() {
        return unitsPerDosis;
    }

    public void setUnitsPerDosis(int unitsPerDosis) {
        this.unitsPerDosis = unitsPerDosis;
    }

    public String getUnitsPerDosisString() {
         return unitsPerDosis + ((unitsPerDosis != 1)? " unidades" : " unidad") + " / "
                 + hours.size() + ((hours.size() != 1)? " veces al día" : " vez al día");
    }

    public DateModel getStartDate() {
        return startDate;
    }

    public void setStartDate(DateModel startDate) {
        this.startDate = startDate;
    }

    public String getStartDateString() {
         return startDate.toString();
    }

    public DateModel getEndDate() {
        return endDate;
    }

    public void setEndDate(DateModel endDate) {
        this.endDate = endDate;
    }

    public String getEndDateString() {
        return endDate.toString();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}
