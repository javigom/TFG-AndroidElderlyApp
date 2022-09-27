package com.example.simplemedicine.model;

import java.io.Serializable;
import java.util.Objects;

public class HourModel implements Serializable, Comparable<HourModel> {

    private int hours;
    private int minutes;

    public HourModel(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return hours + ":" + ((minutes / 10 == 0)? "0" + minutes: minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HourModel hour = (HourModel) o;
        return hours == hour.hours && minutes == hour.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }
    
    @Override
    public int compareTo(HourModel o) {
        if(this.getHours() != o.getHours())
            return (this.getHours() > o.getHours()? 1: -1);
        else if(this.getMinutes() != o.getMinutes())
            return (this.getMinutes() > o.getMinutes()? 1: -1);
        else return 0;
    }
}
