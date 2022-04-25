package com.example.simplemedicine.model.date;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DateModel implements Serializable, Comparable<DateModel> {

    private int year, month, day;

    public DateModel(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        if(day == -1 || month == -1 || year == -1)
            return "Sin fecha de fin";
        else
            return day + "/" + (month + 1) + "/" + year;
    }

    @Override
    public int compareTo(@NonNull DateModel o) {
        if(this.getYear() != o.getYear())
            return ((this.getYear() > o.getYear() || this.getYear() == -1)? 1: -1);
        else if(this.getMonth() != o.getMonth())
            return ((this.getMonth() > o.getMonth() || this.getMonth() == -1)? 1: -1);
        else if(this.getDay() != o.getDay())
            return ((this.getDay() > o.getDay() || this.getDay() == -1)? 1: -1);
        else return 0;
    }
}
