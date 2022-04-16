package com.example.simplemedicine.model.hour;

import androidx.annotation.NonNull;

import com.example.simplemedicine.model.hour.HourModel;

import java.util.Comparator;

public class HourComparator implements Comparator<HourModel> {

    @Override
    public int compare(@NonNull HourModel o1, @NonNull HourModel o2) {
        if(o1.getHours() != o2.getHours())
            return (o1.getHours() > o2.getHours()? 1: -1);
        else
            return (o1.getMinutes() > o2.getMinutes()? 1: -1);
    }
}