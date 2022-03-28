package com.example.simplecall.model;

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

    public String getFormattedDate() {
        return getDisplayableTime();
    }

    @NonNull
    public String toString(){
        return "Call Information:" +
                "\nNumber: " + this.phone +
                "\nDate: " + this.date +
                "\nDuration: " + this.duration +
                "\nType: " + this.type + "\n";
    }

    private String getDisplayableTime() {

        String format1 = new SimpleDateFormat("dd LLLL").format(date);
        String format2 = new SimpleDateFormat("HH:mm").format(date);

        Long mDate = java.lang.System.currentTimeMillis();
        long difference = mDate - date.getTime();

        final long seconds = difference/1000;
        final long minutes = seconds/60;
        final long hours = minutes/60;
        final long days = hours/24;

        if (seconds < 60)
            return "Hace " + seconds + " segundos";
        else if (seconds < 3599) // 60 * 60
            return "Hace " + minutes + " minutos";
        else if (seconds < 86400) // 24 * 60 * 60
            return "Hace " + hours + " hora(s)";
        else if (seconds < 172800) // 48 * 60 * 60
            return "Ayer, " + format2;
        else if (seconds < 604800) // 7 * 24 * 60 * 60
            return "Hace " + days + " días, " + format2;
        else
            return format1 + ", " + format2;
    }
}
