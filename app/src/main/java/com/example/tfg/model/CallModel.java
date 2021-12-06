package com.example.tfg.model;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallModel {

    // Information about a call
    private String phone;
    private Date date;
    private String duration;
    private String type;

    //private String name;
    //private String photo;

    public CallModel(String phone, Date date, String duration, String type){
        this.phone = phone;
        this.date = date;
        this.duration = duration;
        this.type = type;

    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate(){ return date; }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDuration() { return duration; }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCallType() { return type; }

    public void setCallType(String callType) {
        this.type = callType;
    }

    public String getFormattedDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd LLLL - HH:mm");
        return simpleDateFormat.format(date);
    }

    @NonNull
    public String toString(){
        return "Call Information:" +
                "\nNumber: " + this.phone +
                "\nDate: " + this.date +
                "\nDuration: " + this.duration +
                "\nType: " + this.type + "\n";
    }
}
