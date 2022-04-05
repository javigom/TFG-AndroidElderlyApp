package com.example.simplemedicine.model;

public class Hour {

    private int hours;
    private int minutes;

    public Hour(int hours, int minutes) throws Exception {
        if(hours >= 0 && hours < 24 && minutes >= 0 && minutes <= 60) {
            this.hours = hours;
            this.minutes = minutes;
        }
        else {
            throw new Exception("Incorrect format time");
        }
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
        return hours + ":" + minutes;
    }
}
