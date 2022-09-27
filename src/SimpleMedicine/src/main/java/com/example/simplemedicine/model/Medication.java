package com.example.simplemedicine.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.simplemedicine.R;
import com.example.simplemedicine.util.WeekDayEnum;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Entity(tableName = "medication_table")
public class Medication implements Serializable, Comparable<Medication> {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String name;
    private String description;
    private Map<WeekDayEnum, Boolean> weekDays;
    private List<HourModel> hours;
    private int unitsPerDoses;
    private DateModel startDate;
    private DateModel endDate;
    private String photo;
    private boolean notifications;
    private int color;

    public Medication() {
        description = "";
        weekDays = new HashMap<>();
        for(int i = 0; i < WeekDayEnum.values().length; i++)
            weekDays.put(WeekDayEnum.getWeekDay(i), false);
        hours = new ArrayList<>();
        unitsPerDoses = -1;
        notifications = false;
        color = R.color.primary;
    }

    @NonNull
    @Override
    public String toString() {
        return  "name=" + name + '\n' +
                "description=" + description + '\n' +
                "weekDays=" + weekDays + '\n' +
                "hours=" + hours + '\n' +
                "unitsPerDosis=" + unitsPerDoses + '\n' +
                "startDate=" + startDate + '\n' +
                "endDate=" + endDate + '\n' +
                "notifications=" + notifications + '\n' +
                "color=" + color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Map<WeekDayEnum, Boolean> getWeekDays() {
        return weekDays;
    }

    public String getWeekDaysString() {
         ArrayList<String> days = new ArrayList<>();
         for(int i = 0; i < weekDays.size(); i++) {
             if(weekDays.get(WeekDayEnum.getWeekDay(i)))
                 days.add(WeekDayEnum.getWeekDayString(i));
         }
         return days.toString().replace("[", "").replace("]", "");
    }

    public void setWeekDays(Map<WeekDayEnum, Boolean> weekDays) {
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

    public int getUnitsPerDoses() {
        return unitsPerDoses;
    }

    public void setUnitsPerDoses(int unitsPerDoses) {
        this.unitsPerDoses = unitsPerDoses;
    }

    public String getUnitsPerDosisString() {
         return unitsPerDoses + ((unitsPerDoses != 1)? " unidades" : " unidad") + " / "
                 + hours.size() + ((hours.size() != 1)? " veces al día" : " vez al día");
    }

    public DateModel getStartDate() {
        return startDate;
    }

    public void setStartDate(DateModel startDate) {
        this.startDate = startDate;
    }

    public String getStartDateString() {
         return "Desde: " + startDate.toString();
    }

    public DateModel getEndDate() {
        return endDate;
    }

    public void setEndDate(DateModel endDate) {
        this.endDate = endDate;
    }

    public String getEndDateString() {
        return "Hasta: " + endDate.toString();
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int compareTo(Medication o) {
        String name1 = Normalizer.normalize(this.getName(), Normalizer.Form.NFD).replaceFirst("[^\\p{ASCII}]", "");
        String name2 = Normalizer.normalize(o.getName(), Normalizer.Form.NFD).replaceFirst("[^\\p{ASCII}]", "");

        return name1.toLowerCase(Locale.ROOT).compareTo(name2.toLowerCase(Locale.ROOT));
    }
}
